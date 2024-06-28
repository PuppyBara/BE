package com.amatta.findog.repository;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.domain.Shelter;
import com.amatta.findog.domain.ShelterDog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShelterDogRepository extends JpaRepository<ShelterDog, Long> {

    List<ShelterDog> findByShelter(Shelter shelterEntity);
}
