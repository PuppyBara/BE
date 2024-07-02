package com.amatta.findog.repository;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Optional<Shelter> findById(String id);
    boolean existsById(String id);

    Optional<Shelter> findByName(String shelterName);

    @Query("SELECT s FROM Shelter s " +
            "WHERE s.address.address1 LIKE %:keyword%")
    List<Shelter> findAllByAddressContainingKeywords(String keyword);

    @Query("SELECT s FROM Shelter s " +
            "WHERE s.address.address1 LIKE %:keyword1% " +
            "OR s.address.address1 LIKE %:keyword2%")
    List<Shelter> findAllByAddressContainingKeywords(String keyword1, String keyword2);

    @Query("SELECT s FROM Shelter s " +
            "WHERE s.address.address1 LIKE %:keyword1% " +
            "OR s.address.address1 LIKE %:keyword2% " +
            "OR s.address.address1 LIKE %:keyword3%")
    List<Shelter> findAllByAddressContainingKeywords(String keyword1, String keyword2, String keyword3);
}
