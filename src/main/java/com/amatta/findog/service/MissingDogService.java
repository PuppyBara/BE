package com.amatta.findog.service;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.dto.request.MissingDogRequest;
import com.amatta.findog.repository.MemberRepository;
import com.amatta.findog.repository.MissingDogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MissingDogService {
    private final MissingDogRepository missingDogRepository;
    private final MemberRepository memberRepository;

    private Member getMemberEntity(String id){
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void createMissingDog(UserDetails userDetail, MissingDogRequest missingDog) {
        MissingDog dog = missingDog.toEntity(getMemberEntity(userDetail.getUsername()));
        missingDogRepository.save(dog);
    }
}
