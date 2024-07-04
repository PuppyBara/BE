package com.amatta.findog.dto.request;

import com.amatta.findog.domain.Address;
import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.ProtectedDog;
import com.amatta.findog.dto.EtcInfo;
import com.amatta.findog.dto.ProtectedDogInfo;
import com.amatta.findog.enums.Sex;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProtectedDogRequest {
    private ProtectedDogInfo protectedDogInfo;
    private EtcInfo etcInfo;

    public ProtectedDog toEntity(Member memberEntity, String imagePath) {
        return ProtectedDog.createProtectedDog(protectedDogInfo.getBreed(),
                Sex.valueOf(protectedDogInfo.getSex()), protectedDogInfo.getColor(),
                protectedDogInfo.isNeutering(), protectedDogInfo.getFeature(),
                Address.createAddress(etcInfo.getLocation(), null),
                imagePath, etcInfo.getDateTime(), memberEntity);
    }
}
