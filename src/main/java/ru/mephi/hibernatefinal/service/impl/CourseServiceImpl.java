package ru.mephi.hibernatefinal.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.mephi.hibernatefinal.dto.request.CourseCreateDto;
import ru.mephi.hibernatefinal.dto.request.CourseUpdateDto;
import ru.mephi.hibernatefinal.dto.request.LessonDto;
import ru.mephi.hibernatefinal.dto.request.ModuleDto;
import ru.mephi.hibernatefinal.entity.Role;
import ru.mephi.hibernatefinal.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    private final ru.mephi.hibernatefinal.repository.CourseRepository courseRepository;
    private final ru.mephi.hibernatefinal.repository.CategoryRepository categoryRepository;
    private final ru.mephi.hibernatefinal.repository.UserRepository userRepository;
    private final ru.mephi.hibernatefinal.repository.ModuleRepository moduleRepository;
    private final ru.mephi.hibernatefinal.repository.LessonRepository lessonRepository;
    private final ru.mephi.hibernatefinal.repository.EnrollmentRepository enrollmentRepository;

    public CourseServiceImpl(ru.mephi.hibernatefinal.repository.CourseRepository courseRepository,
                             ru.mephi.hibernatefinal.repository.CategoryRepository categoryRepository,
                             ru.mephi.hibernatefinal.repository.UserRepository userRepository,
                             ru.mephi.hibernatefinal.repository.ModuleRepository moduleRepository,
                             ru.mephi.hibernatefinal.repository.LessonRepository lessonRepository,
                             ru.mephi.hibernatefinal.repository.EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    @Transactional
    public ru.mephi.hibernatefinal.entity.Course createCourse(CourseCreateDto dto) {
        ru.mephi.hibernatefinal.entity.Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        ru.mephi.hibernatefinal.entity.User teacher = userRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        if (teacher.getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("User is not a teacher");
        }

        ru.mephi.hibernatefinal.entity.Course course = new ru.mephi.hibernatefinal.entity.Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setCategory(category);
        course.setTeacher(teacher);

        // сохранение курса без модулей сначала
        course = courseRepository.save(course);

        // если переданы модули — создаём их и уроки
        if (dto.getModules() != null) {
            for (ModuleDto mDto : dto.getModules()) {
                ru.mephi.hibernatefinal.entity.Module module = new ru.mephi.hibernatefinal.entity.Module();
                module.setCourse(course);
                module.setTitle(mDto.getTitle());
                module.setOrderIndex(mDto.getOrderIndex());
                module.setDescription(mDto.getDescription());
                module = moduleRepository.save(module);

                if (mDto.getLessons() != null) {
                    for (LessonDto lDto : mDto.getLessons()) {
                        ru.mephi.hibernatefinal.entity.Lesson lesson = new ru.mephi.hibernatefinal.entity.Lesson();
                        lesson.setModule(module);
                        lesson.setTitle(lDto.getTitle());
                        lesson.setContent(lDto.getContent());
                        lesson.setVideoUrl(lDto.getVideoUrl());
                        lessonRepository.save(lesson);
                    }
                }
            }
        }

        return loadLessonsForModules(courseRepository.findByIdWithModules(course.getId()).orElse(course));
    }

    @Override
    @Transactional
    public ru.mephi.hibernatefinal.entity.Module addModuleToCourse(Integer courseId, ModuleDto moduleDto) {
        ru.mephi.hibernatefinal.entity.Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        ru.mephi.hibernatefinal.entity.Module module = new ru.mephi.hibernatefinal.entity.Module();
        module.setCourse(course);
        module.setTitle(moduleDto.getTitle());
        module.setOrderIndex(moduleDto.getOrderIndex());
        module.setDescription(moduleDto.getDescription());
        module = moduleRepository.save(module);

        if (moduleDto.getLessons() != null) {
            for (LessonDto lDto : moduleDto.getLessons()) {
                ru.mephi.hibernatefinal.entity.Lesson lesson = new ru.mephi.hibernatefinal.entity.Lesson();
                lesson.setModule(module);
                lesson.setTitle(lDto.getTitle());
                lesson.setContent(lDto.getContent());
                lesson.setVideoUrl(lDto.getVideoUrl());
                lessonRepository.save(lesson);
            }
        }

        return module;
    }

    @Override
    @Transactional
    public ru.mephi.hibernatefinal.entity.Lesson addLessonToModule(Integer moduleId, LessonDto lessonDto) {
        ru.mephi.hibernatefinal.entity.Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));

        ru.mephi.hibernatefinal.entity.Lesson lesson = new ru.mephi.hibernatefinal.entity.Lesson();
        lesson.setModule(module);
        lesson.setTitle(lessonDto.getTitle());
        lesson.setContent(lessonDto.getContent());
        lesson.setVideoUrl(lessonDto.getVideoUrl());
        return lessonRepository.save(lesson);
    }

    @Override
    @Transactional
    public ru.mephi.hibernatefinal.entity.Course updateCourse(Integer courseId, CourseUpdateDto dto) {
        ru.mephi.hibernatefinal.entity.Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (dto.getTitle() != null) course.setTitle(dto.getTitle());
        if (dto.getDescription() != null) course.setDescription(dto.getDescription());
        if (dto.getCategoryId() != null) {
            ru.mephi.hibernatefinal.entity.Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            course.setCategory(category);
        }
        if (dto.getTeacherId() != null) {
            ru.mephi.hibernatefinal.entity.User teacher = userRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
            if (teacher.getRole() != Role.TEACHER) {
                throw new IllegalArgumentException("User is not a teacher");
            }
            course.setTeacher(teacher);
        }

        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Integer courseId) {
        // безопасная политика: запрет при существующих записях студентов
        if (!enrollmentRepository.findByCourseId(courseId).isEmpty()) {
            throw new IllegalStateException("Cannot delete course with enrollments");
        }

        // можно удалить каскадно (modules/lessons и т.д. настроены на cascade)
        courseRepository.deleteById(courseId);
    }

    @Override
    @Transactional
    public ru.mephi.hibernatefinal.entity.Course getCourseWithStructure(Integer courseId) {
        return loadLessonsForModules(courseRepository.findByIdWithModules(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found")));
    }

    private ru.mephi.hibernatefinal.entity.Course loadLessonsForModules(ru.mephi.hibernatefinal.entity.Course course) {
        if (course.getModules() != null) {
            for (ru.mephi.hibernatefinal.entity.Module m : course.getModules()) {
                java.util.List<ru.mephi.hibernatefinal.entity.Lesson> lessons = lessonRepository.findByModuleId(m.getId());
                if (m.getLessons() == null) {
                    m.setLessons(lessons);
                } else {
                    m.getLessons().clear();
                    m.getLessons().addAll(lessons);
                }
            }
        }
        return course;
    }
}