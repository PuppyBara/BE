package com.amatta.findog.dto.request;

import com.amatta.findog.domain.*;
import com.amatta.findog.dto.EtcInfo;
import com.amatta.findog.dto.ProtectedDogInfo;
import com.amatta.findog.dto.ShelterDogInfo;
import com.amatta.findog.enums.Sex;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ShelterDogRequest {

    private ShelterDogInfo shelterDogInfo;
    private EtcInfo etcInfo;

    public ShelterDog toEntity(Shelter shelter) {
        return ShelterDog.createShelterDog(
                shelterDogInfo.getBreed(),
                Sex.valueOf(shelterDogInfo.getSex()),
                shelterDogInfo.getColor(),
                shelterDogInfo.isNeutering(),
                shelterDogInfo.getFeature(),
                shelterDogInfo.getAge(),
                Address.createAddress(etcInfo.getLocation(), null),
                shelterDogInfo.getImage(),
                etcInfo.getDateTime(),
                shelter,
                shelterDogInfo.getNoticeNo(),
                shelterDogInfo.getNoticeSdt(),
                shelterDogInfo.getNoticeEdt());
    }
}
