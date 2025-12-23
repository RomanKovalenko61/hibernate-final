package ru.mephi.hibernatefinal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.mephi.hibernatefinal.entity.Profile;
import ru.mephi.hibernatefinal.entity.Role;
import ru.mephi.hibernatefinal.entity.User;
import ru.mephi.hibernatefinal.repository.ProfileRepository;
import ru.mephi.hibernatefinal.repository.UserRepository;

@SpringBootApplication
public class HibernateFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateFinalApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, ProfileRepository profileRepository) {
        return (args) -> {
            User student = new User();
            student.setName("Иван Иванов");
            student.setEmail("ivan.student@mephi.ru");
            student.setRole(Role.STUDENT);

            User teacher = new User();
            teacher.setName("Петр Петров");
            teacher.setEmail("petr.teacher@mephi.ru");
            teacher.setRole(Role.TEACHER);

            User admin = new User();
            admin.setName("Анна Бучинская");
            admin.setEmail("anna.admin@mephi.ru");
            admin.setRole(Role.ADMIN);

            userRepository.save(student);
            userRepository.save(teacher);
            userRepository.save(admin);

            Profile studentProfile = new Profile();
            studentProfile.setUser(student);
            studentProfile.setBio("Студент 2 курса, изучаю Java и Spring Framework");
            studentProfile.setAvatarUrl("https://avatar.mephi.ru/student.jpg");

            Profile teacherProfile = new Profile();
            teacherProfile.setUser(teacher);
            teacherProfile.setBio("Преподаватель кафедры информатики, кандидат технических наук");
            teacherProfile.setAvatarUrl("https://avatar.mephi.ru/teacher.jpg");

            Profile adminProfile = new Profile();
            adminProfile.setUser(admin);
            adminProfile.setBio("Повелитель всего");
            adminProfile.setAvatarUrl("https://avatar.mephi.ru/admin.jpg");

            profileRepository.save(studentProfile);
            profileRepository.save(teacherProfile);
            profileRepository.save(adminProfile);

            System.out.println("\n=== Созданные пользователи ===");

            userRepository.findAll().forEach(user -> {
                System.out.println("ID: " + user.getId() +
                                 " | Имя: " + user.getName() +
                                 " | Email: " + user.getEmail() +
                                 " | Роль: " + user.getRole());
            });

            System.out.println("\n=== Созданные профили ===");

            profileRepository.findAll().forEach(profile -> {
                System.out.println("ID: " + profile.getId() +
                                 " | User: " + profile.getUser().getName() +
                                 " | Bio: " + profile.getBio() +
                                 " | Avatar: " + profile.getAvatarUrl());
            });

            System.out.println("\n=== Получение профиля через пользователя ===");

            User adminFromDb = userRepository.findByRole(Role.ADMIN).stream().findFirst().orElse(null);
            if (adminFromDb != null && adminFromDb.getProfile() != null) {
                Profile adminProfileFromUser = adminFromDb.getProfile();
                System.out.println("Пользователь: " + adminFromDb.getName() + " (" + adminFromDb.getRole() + ")");
                System.out.println("Его профиль:");
                System.out.println("  - Bio: " + adminProfileFromUser.getBio());
                System.out.println("  - Avatar: " + adminProfileFromUser.getAvatarUrl());
            }

            System.out.println("==============================\n");
        };
    }

}
