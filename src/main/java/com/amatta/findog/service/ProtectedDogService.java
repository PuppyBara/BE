package com.amatta.findog.service;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.ProtectedDog;
import com.amatta.findog.domain.Shelter;
import com.amatta.findog.domain.ShelterDog;
import com.amatta.findog.dto.request.ProtectedDogRequest;
import com.amatta.findog.dto.response.MyProtectedDogResponse;
import com.amatta.findog.dto.response.ProtectedDogResponse;
import com.amatta.findog.repository.*;
import com.amatta.findog.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProtectedDogService {
    private final ProtectedDogRepository protectedDogRepository;
    private final MemberRepository memberRepository;
    private final ShelterDogRepository shelterDogRepository;
    private final ShelterRepository shelterRepository;
    private final S3Util s3Util;

    private Member getMemberEntity(UserDetails userDetail){
        for(GrantedAuthority authority : userDetail.getAuthorities()) {
            if(authority.getAuthority().equals("ROLE_SHELTER")) throw new RuntimeException("일반사용자만 사용할 수 있습니다.");
        }
        return memberRepository.findById(userDetail.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
    }
    private Shelter getShelterEntity(UserDetails userDetail){
        for(GrantedAuthority authority : userDetail.getAuthorities()) {
            if(authority.getAuthority().equals("ROLE_MEMBER")) throw new RuntimeException("기업사용자만 사용할 수 있습니다.");
        }
        return shelterRepository.findById(userDetail.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void createProtectedDog(UserDetails userDetail, ProtectedDogRequest protectedDog, MultipartFile image) {
        //==이미지를 S3에 저장
        String imagePath = s3Util.saveFile(image);
        ProtectedDog dog = protectedDog.toEntity(getMemberEntity(userDetail),imagePath);
        protectedDogRepository.save(dog);
    }

    public ProtectedDogResponse getProtectedDog(Long id) {
        ProtectedDog dog = protectedDogRepository.findById(id).orElseThrow(() -> new RuntimeException("ProtectedDog not found"));
        return ProtectedDogResponse.fromEntity(dog);
    }

    public MyProtectedDogResponse getMyProtectedDog(UserDetails userDetail) {
        for(GrantedAuthority authority : userDetail.getAuthorities()) {
            if(authority.getAuthority().equals("ROLE_SHELTER")) {
                List<ShelterDog> list = shelterDogRepository.findByShelter(getShelterEntity(userDetail));
                return MyProtectedDogResponse.fromShelterDogEntity(list);
            } else if(authority.getAuthority().equals("ROLE_MEMBER")){
                List<ProtectedDog> list = protectedDogRepository.findByMember(getMemberEntity(userDetail));
                return MyProtectedDogResponse.fromProtectedDogEntity(list);
            }
            break;
        }
        return null;
    }
}
