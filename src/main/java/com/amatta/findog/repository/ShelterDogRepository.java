package com.amatta.findog.repository;

import com.amatta.findog.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShelterDogRepository extends JpaRepository<ShelterDog, Long> {

    List<ShelterDog> findByShelter(Shelter shelterEntity);

    Optional<ShelterDog> findByNoticeNo(String noticeNo);

    List<ShelterDog> findByShelterShelterIdIn(List<Integer> ids);

    @Query("SELECT sd FROM ShelterDog sd JOIN Shelter s on sd.shelter.shelterId = s.shelterId " +
                    "WHERE s.address.address2 LIKE concat('%',:address2, '%') " +
                    "ORDER BY sd.noticeSdt DESC LIMIT 5")
    List<ShelterDog> getTop5NearShelterDog(String address2);
}
