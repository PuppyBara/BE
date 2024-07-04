package com.amatta.findog.dto.response;

import com.amatta.findog.domain.Dog;
import com.amatta.findog.domain.ShelterDog;
import com.amatta.findog.dto.AllProtectedDog;
import com.amatta.findog.dto.MyMissingDogs;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AllProtectedDogResponse {
    private List<AllProtectedDog> protectedDogs;

    public static AllProtectedDogResponse fromEntity(List<Dog> dogs) {
        List<AllProtectedDog> list = dogs.stream()
                .map(dog -> AllProtectedDog.builder()
                        .id(dog.getDogId())
                        .image(dog.getImage())
                        .sex(String.valueOf(dog.getSex()))
                        .dateTime(dog.getDateTime())
                        .location(dog.getFullAddress())
                        .whoProtected(dog instanceof ShelterDog ? ((ShelterDog) dog).getShelter().getName() : "개인 임시보호중")
                        .build())
                .collect(Collectors.toList());

        return new AllProtectedDogResponse(list);
    }
}
