package com.amatta.findog.dto.response;

import com.amatta.findog.dto.AiDogDto;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiResponse {
    private List<AiDogDto> aiDogResult;
}
