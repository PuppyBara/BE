package com.amatta.findog.repository;

import com.amatta.findog.domain.Dog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Long> {
    @Query("SELECT d FROM Dog d WHERE TYPE(d) != MissingDog ORDER BY d.dogId DESC")
    Page<Dog> findNonMissingDogs(Pageable pageable);

    @Query("SELECT d FROM Dog d WHERE TYPE(d) != MissingDog AND d.dateTime >= :missingTime ORDER BY d.dogId DESC")
    List<Dog> findNonMissingDogsAfterMissingDate(@Param("missingTime") LocalDateTime missingTime);
}
