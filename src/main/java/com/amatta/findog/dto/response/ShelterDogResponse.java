package com.amatta.findog.dto.response;

import com.amatta.findog.domain.ShelterDog;
import com.amatta.findog.dto.EtcInfo;
import com.amatta.findog.dto.ShelterDogInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelterDogResponse {


    private ShelterDogInfo shelterDogInfo;
    private EtcInfo etcInfo;

    public static ShelterDogResponse fromEntity(ShelterDog shelterDog) {
        return ShelterDogResponse.builder()
                .shelterDogInfo(ShelterDogInfo.builder()
                        .image(shelterDog.getImage())
                        .breed(shelterDog.getBreed())
                        .sex(String.valueOf(shelterDog.getSex()))
                        .color(shelterDog.getColor())
                        .isNeutering(shelterDog.isNeutering())
                        .feature(shelterDog.getFeature())
                        .noticeNo(shelterDog.getNoticeNo())
                        .noticeSdt(shelterDog.getNoticeSdt())
                        .noticeEdt(shelterDog.getNoticeEdt())
                        .build())
                .etcInfo(EtcInfo.builder()
                        .dateTime(shelterDog.getDateTime())
                        .location(shelterDog.getFullAddress())
                        .build())
                .build();
    }
}
