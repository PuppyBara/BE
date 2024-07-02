package com.amatta.findog.service;

import com.amatta.findog.domain.Shelter;
import com.amatta.findog.domain.ShelterDog;
import com.amatta.findog.dto.ShelterDogDto;
import com.amatta.findog.dto.ShelterDto;
import com.amatta.findog.dto.response.SearchShelterDogResponse;
import com.amatta.findog.dto.response.ShelterResponse;
import com.amatta.findog.repository.ShelterDogRepository;
import com.amatta.findog.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterInfoOpenAPIService shelterInfoOpenAPIService;
    private final ShelterDogRepository shelterDogRepository;
    private final ShelterRepository shelterRepository;

    private Shelter getShelterEntity(UserDetails userDetail){
        for(GrantedAuthority authority : userDetail.getAuthorities()) {
            if(authority.getAuthority().equals("ROLE_MEMBER")) throw new RuntimeException("기업사용자만 사용할 수 있습니다.");
        }
        return shelterRepository.findById(userDetail.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void reloadShelterInfoApiData() throws UnsupportedEncodingException, ParseException {
        List<ShelterDto> shelterDtos = shelterInfoOpenAPIService.getShelterList();
        for(ShelterDto dto: shelterDtos) {
            Optional<Shelter> shelter = shelterRepository.findByName(dto.getName());
            if(shelter.isEmpty()) shelterRepository.save(dto.toEntity());
        }
    }

    public ShelterResponse getShelterList() {
        List<Shelter> 서울 = shelterRepository.findAllByAddressContainingKeywords("서울");
        List<Shelter> 경기 = shelterRepository.findAllByAddressContainingKeywords("경기");
        List<Shelter> 인천 = shelterRepository.findAllByAddressContainingKeywords("인천");
        List<Shelter> 대전세종충청 = shelterRepository.findAllByAddressContainingKeywords("대전", "세종", "충청");
        List<Shelter> 부산대구경상 = shelterRepository.findAllByAddressContainingKeywords("부산", "대구", "경상");
        List<Shelter> 광주전라 = shelterRepository.findAllByAddressContainingKeywords("광주", "전라");
        List<Shelter> 강원 = shelterRepository.findAllByAddressContainingKeywords("강원");
        List<Shelter> 제주 = shelterRepository.findAllByAddressContainingKeywords("제주");
        return ShelterResponse.fromEntity(서울, 경기, 인천, 대전세종충청, 부산대구경상, 광주전라, 강원, 제주);
    }

    public SearchShelterDogResponse getShelterDogList(List<Integer> searchIds) {
        List<ShelterDog> shelterDogs = shelterDogRepository.findByShelterShelterIdIn(searchIds);
        return SearchShelterDogResponse.fromEntity(shelterDogs);
    }
}
