package com.amatta.findog.service;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.domain.ProtectedDog;
import com.amatta.findog.dto.request.MissingDogRequest;
import com.amatta.findog.dto.request.ProtectedDogRequest;
import com.amatta.findog.dto.response.MissingDogResponse;
import com.amatta.findog.dto.response.MyProtectedDogResponse;
import com.amatta.findog.dto.response.ProtectedDogResponse;
import com.amatta.findog.repository.MemberRepository;
import com.amatta.findog.repository.MissingDogRepository;
import com.amatta.findog.repository.ProtectedDogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProtectedDogService {
    private final ProtectedDogRepository protectedDogRepository;
    private final MemberRepository memberRepository;

    private Member getMemberEntity(String id){
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void createProtectedDog(UserDetails userDetail, ProtectedDogRequest protectedDog) {
        ProtectedDog dog = protectedDog.toEntity(getMemberEntity(userDetail.getUsername()));
        protectedDogRepository.save(dog);
    }

    public ProtectedDogResponse getProtectedDog(Long id) {
        ProtectedDog dog = protectedDogRepository.findById(id).orElseThrow(() -> new RuntimeException("ProtectedDog not found"));
        return ProtectedDogResponse.fromEntity(dog);
    }

    public MyProtectedDogResponse getMyProtectedDog(UserDetails userDetail) {
        List<ProtectedDog> list = protectedDogRepository.findByMember(getMemberEntity(userDetail.getUsername()));
        return MyProtectedDogResponse.fromEntity(list);
    }
}
