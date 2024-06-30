package com.amatta.findog.dto.response;

import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.domain.ShelterDog;
import com.amatta.findog.dto.MyMissingDogs;
import com.amatta.findog.dto.MyShelterDogs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyShelterDogResponse {

    private List<MyShelterDogs> myShelterDogs;

    public static MyShelterDogResponse fromEntity(List<ShelterDog> list) {
        List<MyShelterDogs> myShelterDogList = list.stream()
                .map(dog -> MyShelterDogs.builder()
                        .id(dog.getDogId())
                        .sex(String.valueOf(dog.getSex()))
                        .breed(dog.getBreed())
                        .image(dog.getImage())
                        .dateTime(dog.getDateTime())
                        .location(dog.getFullAddress())
                        .build())
                .collect(Collectors.toList());

        return MyShelterDogResponse.builder()
                .myShelterDogs(myShelterDogList)
                .build();
    }
}
