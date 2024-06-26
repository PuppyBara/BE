package com.amatta.findog.repository;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.MissingDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissingDogRepository extends JpaRepository<MissingDog, Long> {

}
