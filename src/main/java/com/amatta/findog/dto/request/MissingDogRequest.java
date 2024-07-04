package com.amatta.findog.dto.request;

import com.amatta.findog.domain.Address;
import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.dto.EtcInfo;
import com.amatta.findog.dto.MissingDogInfo;
import com.amatta.findog.enums.Sex;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MissingDogRequest {
    private MissingDogInfo missingDogInfo;
    private EtcInfo etcInfo;

    public MissingDog toEntity(Member member, String imagePath) {
        return MissingDog.createMissingDog(
                missingDogInfo.getBreed(), Sex.valueOf(missingDogInfo.getSex()),
                missingDogInfo.isNeutering(), missingDogInfo.getFeature(),
                missingDogInfo.getAge(), Address.createAddress(etcInfo.getLocation(), null),
                etcInfo.getDateTime(), imagePath,
                missingDogInfo.getName(), missingDogInfo.getColor(), member);
    }
}
