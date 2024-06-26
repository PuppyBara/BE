package com.amatta.findog.dto.response;

import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.domain.ProtectedDog;
import com.amatta.findog.dto.EtcInfo;
import com.amatta.findog.dto.MissingDogInfo;
import com.amatta.findog.dto.ProtectedDogInfo;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProtectedDogResponse {
    private ProtectedDogInfo protectedDogInfo;
    private EtcInfo etcInfo;

    public static ProtectedDogResponse fromEntity(ProtectedDog protectedDog) {
        return ProtectedDogResponse.builder()
                .protectedDogInfo(ProtectedDogInfo.builder()
                                .image(protectedDog.getImage())
                                .breed(protectedDog.getBreed())
                                .sex(String.valueOf(protectedDog.getSex()))
                                .color(protectedDog.getColor())
                                .isNeutering(protectedDog.isNeutering())
                                .feature(protectedDog.getFeature())
                                .build())
                .etcInfo(EtcInfo.builder()
                        .location(protectedDog.getFullAddress())
                        .dateTime(protectedDog.getDateTime())
                        .build())
                .build();
    }
}
