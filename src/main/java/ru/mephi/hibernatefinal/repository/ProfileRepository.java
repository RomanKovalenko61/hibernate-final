package ru.mephi.hibernatefinal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.mephi.hibernatefinal.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
