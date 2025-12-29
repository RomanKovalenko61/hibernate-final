package ru.mephi.hibernatefinal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.hibernatefinal.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
