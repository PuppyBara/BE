package com.amatta.findog.dto.response;

import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.domain.ProtectedDog;
import com.amatta.findog.dto.MyMissingDogs;
import com.amatta.findog.dto.MyProtectedDogs;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyMissingDogResponse {
    private List<MyMissingDogs> myMissingDogs;

    public static MyMissingDogResponse fromEntity(List<MissingDog> list) {
        List<MyMissingDogs> myMissingDogsList = list.stream()
                .map(dog -> MyMissingDogs.builder()
                        .id(dog.getDogId())
                        .sex(String.valueOf(dog.getSex()))
                        .image(dog.getImage())
                        .name(dog.getName())
                        .age(dog.getAge())
                        .dateTime(dog.getDateTime())
                        .location(dog.getFullAddress())
                        .build())
                .collect(Collectors.toList());

        return MyMissingDogResponse.builder()
                .myMissingDogs(myMissingDogsList)
                .build();
    }
}
