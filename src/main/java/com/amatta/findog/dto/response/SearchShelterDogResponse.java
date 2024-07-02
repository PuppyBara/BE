package com.amatta.findog.dto.response;

import com.amatta.findog.domain.ShelterDog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchShelterDogResponse {

    List<ShelterDogResponse> shelterDogs;

    public static SearchShelterDogResponse fromEntity(List<ShelterDog> shelterDogs) {
        return SearchShelterDogResponse.builder()
                .shelterDogs(shelterDogs.stream().map(ShelterDogResponse::fromEntity).toList())
                .build();
    }
}
