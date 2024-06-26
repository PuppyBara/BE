package com.amatta.findog.service;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.domain.ProtectedDog;
import com.amatta.findog.dto.request.MissingDogRequest;
import com.amatta.findog.dto.response.MissingDogResponse;
import com.amatta.findog.dto.response.MyMissingDogResponse;
import com.amatta.findog.dto.response.MyProtectedDogResponse;
import com.amatta.findog.repository.MemberRepository;
import com.amatta.findog.repository.MissingDogRepository;
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

    public MissingDogResponse getMissingDog(Long id) {
        MissingDog dog = missingDogRepository.findById(id).orElseThrow(() -> new RuntimeException("MissingDog not found"));
        return MissingDogResponse.fromEntity(dog);
    }

    public MyMissingDogResponse getMyMissingDog(UserDetails userDetail) {
        //사용자가 보호소면 ShelterDogs를 봐야됨
        Member member = getMemberEntity(userDetail.getUsername());

        List<MissingDog> list = missingDogRepository.findByMember(member);
        return MyMissingDogResponse.fromEntity(list);
    }

}
