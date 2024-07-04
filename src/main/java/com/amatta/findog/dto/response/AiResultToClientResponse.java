package com.amatta.findog.dto.response;

import com.amatta.findog.domain.Dog;
import com.amatta.findog.dto.AiDogDto;
import com.amatta.findog.dto.AiResultToClientDto;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiResultToClientResponse {
    private List<AiResultToClientDto> result;
}
