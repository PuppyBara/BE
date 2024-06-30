package com.amatta.findog.service;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.Shelter;
import com.amatta.findog.domain.ShelterDog;
import com.amatta.findog.dto.ShelterDogDto;
import com.amatta.findog.dto.request.ShelterDogRequest;
import com.amatta.findog.dto.response.MyShelterDogResponse;
import com.amatta.findog.dto.response.ShelterDogResponse;
import com.amatta.findog.repository.ShelterDogRepository;
import com.amatta.findog.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShelterDogService {

    private final AbandonedDogOpenAPIService abandonedDogOpenAPIService;
    private final ShelterDogRepository shelterDogRepository;
    private final ShelterRepository shelterRepository;

    private Shelter getShelterEntity(UserDetails userDetail){
        for(GrantedAuthority authority : userDetail.getAuthorities()) {
            if(authority.getAuthority().equals("ROLE_MEMBER")) throw new RuntimeException("기업사용자만 사용할 수 있습니다.");
        }
        return shelterRepository.findById(userDetail.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public void reloadAbandonedDogApiData(LocalDateTime beginDate, LocalDateTime endDate) throws UnsupportedEncodingException, ParseException {
        List<ShelterDogDto> shelterDogList = abandonedDogOpenAPIService.getShelterDogList(beginDate, endDate);
        for(ShelterDogDto dto: shelterDogList) {
            Optional<ShelterDog> shelterDog = shelterDogRepository.findByNoticeNo(dto.getNoticeNo());
            if(shelterDog.isEmpty()) {
                Optional<Shelter> shelter = shelterRepository.findByName(dto.getShelterName());
                shelter.ifPresent(value -> shelterDogRepository.save(dto.toEntity(value)));
            }
        }
    }

    public void createShelterDog(UserDetails userDetail, ShelterDogRequest request) {
        shelterDogRepository.save(request.toEntity(getShelterEntity(userDetail)));
    }

    public ShelterDogResponse getShelterDog(Long dogId) {
        ShelterDog shelterDog = shelterDogRepository.findById(dogId)
                .orElseThrow(() -> new RuntimeException("해당 보호소 강아지를 찾을 수 없습니다."));
        return ShelterDogResponse.fromEntity(shelterDog);
    }

    public MyShelterDogResponse getMyShelterDog(UserDetails userDetail) {
        List<ShelterDog> shelterDogs = shelterDogRepository.findByShelter(getShelterEntity(userDetail));
        return MyShelterDogResponse.fromEntity(shelterDogs);
    }

}
