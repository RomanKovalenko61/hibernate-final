package ru.mephi.hibernatefinal.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.entity.Profile;
import ru.mephi.hibernatefinal.entity.Role;
import ru.mephi.hibernatefinal.entity.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserProfileRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    public void testCreateAndFindUsersWithProfiles() {
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
        student.setProfile(studentProfile);

        Profile teacherProfile = new Profile();
        teacherProfile.setUser(teacher);
        teacherProfile.setBio("Преподаватель кафедры информатики, кандидат технических наук");
        teacherProfile.setAvatarUrl("https://avatar.mephi.ru/teacher.jpg");
        teacher.setProfile(teacherProfile);

        Profile adminProfile = new Profile();
        adminProfile.setUser(admin);
        adminProfile.setBio("Повелитель всего");
        adminProfile.setAvatarUrl("https://avatar.mephi.ru/admin.jpg");
        admin.setProfile(adminProfile);

        profileRepository.save(studentProfile);
        profileRepository.save(teacherProfile);
        profileRepository.save(adminProfile);

        System.out.println("\n=== Созданные пользователи ===");

        List<User> allUsers = userRepository.findAll();
        assertThat(allUsers).hasSize(3);

        userRepository.findAll().forEach(user -> {
            System.out.println("ID: " + user.getId() +
                             " | Имя: " + user.getName() +
                             " | Email: " + user.getEmail() +
                             " | Роль: " + user.getRole());
        });

        // Проверка поиска по роли
        List<User> admins = userRepository.findByRole(Role.ADMIN);
        assertThat(admins).hasSize(1);
        assertThat(admins.get(0).getName()).isEqualTo("Анна Бучинская");
        assertThat(admins.get(0).getEmail()).isEqualTo("anna.admin@mephi.ru");

        // === Тестирование профилей ===
        System.out.println("\n=== Созданные профили ===");

        List<Profile> allProfiles = profileRepository.findAll();
        assertThat(allProfiles).hasSize(3);

        profileRepository.findAll().forEach(profile -> {
            System.out.println("ID: " + profile.getId() +
                             " | User: " + profile.getUser().getName() +
                             " | Bio: " + profile.getBio() +
                             " | Avatar: " + profile.getAvatarUrl());
        });

        // Проверка конкретного профиля
        assertThat(studentProfile.getBio()).isEqualTo("Студент 2 курса, изучаю Java и Spring Framework");
        assertThat(studentProfile.getUser().getName()).isEqualTo("Иван Иванов");
        assertThat(studentProfile.getAvatarUrl()).isEqualTo("https://avatar.mephi.ru/student.jpg");

        // === Тестирование связи User-Profile ===
        System.out.println("\n=== Получение профиля через пользователя ===");

        User adminFromDb = userRepository.findByRole(Role.ADMIN).stream().findFirst().orElse(null);
        assertThat(adminFromDb).isNotNull();
        assertThat(adminFromDb.getProfile()).isNotNull();

        Profile adminProfileFromUser = adminFromDb.getProfile();
        System.out.println("Пользователь: " + adminFromDb.getName() + " (" + adminFromDb.getRole() + ")");
        System.out.println("Его профиль:");
        System.out.println("  - Bio: " + adminProfileFromUser.getBio());
        System.out.println("  - Avatar: " + adminProfileFromUser.getAvatarUrl());

        // Проверка, что профиль действительно принадлежит админу
        assertThat(adminProfileFromUser.getBio()).isEqualTo("Повелитель всего");
        assertThat(adminProfileFromUser.getAvatarUrl()).isEqualTo("https://avatar.mephi.ru/admin.jpg");

        System.out.println("==============================\n");
    }
}

