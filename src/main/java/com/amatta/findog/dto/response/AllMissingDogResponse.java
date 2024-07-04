package com.amatta.findog.dto.response;

import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.dto.MyMissingDogs;
import lombok.*;
import java.util.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllMissingDogResponse {
    private List<MyMissingDogs> missingDogs;

    public static AllMissingDogResponse fromEntity(List<MissingDog> missingDogs) {
        List<MyMissingDogs> list = missingDogs.stream()
                .map(dog -> MyMissingDogs.builder()
                        .id(dog.getDogId())
                        .image(dog.getImage())
                        .sex(String.valueOf(dog.getSex()))
                        .name(dog.getName())
                        .age(dog.getAge())
                        .dateTime(dog.getDateTime())
                        .location(dog.getFullAddress())
                        .build())
                .collect(Collectors.toList());

        return new AllMissingDogResponse(list);
    }
}
