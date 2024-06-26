package com.amatta.findog.repository;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.domain.ProtectedDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtectedDogRepository extends JpaRepository<ProtectedDog, Long> {
    List<ProtectedDog> findByMember(Member memberEntity);
}

