package com.amatta.findog.service;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.dto.request.MissingDogRequest;
import com.amatta.findog.dto.response.AllMissingDogResponse;
import com.amatta.findog.dto.response.MissingDogResponse;
import com.amatta.findog.dto.response.MyMissingDogResponse;
import com.amatta.findog.repository.MemberRepository;
import com.amatta.findog.repository.MissingDogRepository;
import com.amatta.findog.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MissingDogService {
    private final MissingDogRepository missingDogRepository;
    private final MemberRepository memberRepository;
    private final S3Util s3Util;

    private Member getMemberEntity(UserDetails userDetail){
        for(GrantedAuthority authority : userDetail.getAuthorities()) {
            if(authority.getAuthority().equals("ROLE_SHELTER")) throw new RuntimeException("일반사용자만 사용할 수 있습니다.");
        }
        return memberRepository.findById(userDetail.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void createMissingDog(UserDetails userDetail, MultipartFile image, MissingDogRequest missingDog) {
        //== 이미지를 S3에 업로드
        String imagePath = s3Util.saveFile(image);
        MissingDog dog = missingDog.toEntity(getMemberEntity(userDetail),imagePath);
        missingDogRepository.save(dog);
    }

    public MissingDogResponse getMissingDog(Long id) {
        MissingDog dog = missingDogRepository.findById(id).orElseThrow(() -> new RuntimeException("MissingDog not found"));
        return MissingDogResponse.fromEntity(dog);
    }

    public MyMissingDogResponse getMyMissingDog(UserDetails userDetail) {
        //사용자가 보호소면 ShelterDogs를 봐야됨
        Member member = getMemberEntity(userDetail);
        List<MissingDog> list = missingDogRepository.findByMember(member);
        return MyMissingDogResponse.fromEntity(list);
    }

    public AllMissingDogResponse getAllMissingDog(int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo-1, 10, Sort.by(Sort.Order.desc("crudDate.createAt"), Sort.Order.asc("dogId")));
        List<MissingDog> missingDogs = missingDogRepository.findAll(pageable).getContent();
        return AllMissingDogResponse.fromEntity(missingDogs);
    }
}
