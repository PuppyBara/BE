package com.amatta.findog.dto.response;

import com.amatta.findog.domain.Address;
import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.dto.EtcInfo;
import com.amatta.findog.dto.MissingDogInfo;
import com.amatta.findog.enums.Sex;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissingDogResponse {
    private MissingDogInfo missingDogInfo;
    private EtcInfo etcInfo;

    public static MissingDogResponse fromEntity(MissingDog missingDog) {
        return MissingDogResponse.builder()
                .missingDogInfo(MissingDogInfo.builder()
                                .imagePath(missingDog.getImage())
                                .name(missingDog.getName())
                                .age(missingDog.getAge())
                                .breed(missingDog.getBreed())
                                .sex(String.valueOf(missingDog.getSex()))
                                .color(missingDog.getColor())
                                .isNeutering(missingDog.isNeutering())
                                .feature(missingDog.getFeature())
                                .build())
                .etcInfo(EtcInfo.builder()
                        .location(missingDog.getFullAddress())
                        .dateTime(missingDog.getDateTime())
                        .build())
                .build();
    }
}
