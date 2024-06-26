package com.amatta.findog.dto.response;

import com.amatta.findog.domain.ProtectedDog;
import com.amatta.findog.domain.ShelterDog;
import com.amatta.findog.dto.MyProtectedDogs;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyProtectedDogResponse {
    private List<MyProtectedDogs> myProtectedDogs;

    public static MyProtectedDogResponse fromProtectedDogEntity(List<ProtectedDog> list) {
        List<MyProtectedDogs> myProtectedDogsList = list.stream()
                .map(dog -> MyProtectedDogs.builder()
                        .id(dog.getDogId())
                        .sex(String.valueOf(dog.getSex()))
                        .image(dog.getImage())
                        .breed(dog.getBreed())
                        .dateTime(dog.getDateTime())
                        .location(dog.getFullAddress())
                        .build())
                .collect(Collectors.toList());

        return MyProtectedDogResponse.builder()
                .myProtectedDogs(myProtectedDogsList)
                .build();
    }

    public static MyProtectedDogResponse fromShelterDogEntity(List<ShelterDog> list) {
        List<MyProtectedDogs> myProtectedDogsList = list.stream()
                .map(dog -> MyProtectedDogs.builder()
                        .id(dog.getDogId())
                        .sex(String.valueOf(dog.getSex()))
                        .image(dog.getImage())
                        .breed(dog.getBreed())
                        .dateTime(dog.getDateTime())
                        .location(dog.getFullAddress())
                        .build())
                .collect(Collectors.toList());

        return MyProtectedDogResponse.builder()
                .myProtectedDogs(myProtectedDogsList)
                .build();
    }
}
