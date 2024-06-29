package com.amatta.findog.repository;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Optional<Shelter> findById(String id);
    boolean existsById(String id);

    Optional<Shelter> findByName(String shelterName);
}
