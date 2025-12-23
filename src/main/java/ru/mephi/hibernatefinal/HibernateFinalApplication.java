package ru.mephi.hibernatefinal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.mephi.hibernatefinal.entity.Role;
import ru.mephi.hibernatefinal.entity.User;
import ru.mephi.hibernatefinal.repository.UserRepository;

@SpringBootApplication
public class HibernateFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateFinalApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
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

            System.out.println("\n=== Созданные пользователи ===");

            userRepository.findAll().forEach(user -> {
                System.out.println("ID: " + user.getId() +
                                 " | Имя: " + user.getName() +
                                 " | Email: " + user.getEmail() +
                                 " | Роль: " + user.getRole());
            });

            System.out.println("==============================\n");
        };
    }

}
