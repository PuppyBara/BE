package com.amatta.findog.repository;

import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.domain.ProtectedDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtectedDogRepository extends JpaRepository<ProtectedDog, Long> {

}

