package com.amatta.findog.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissingDogInfo {
    @NotBlank(message = "사진은 필수항목 입니다.")
    private String image;

    @NotBlank(message = "이름은 비워둘 수 없습니다.")
    @Size(max = 255, message = "이름은 255자 이하로 작성 가능합니다.")
    private String name;

    @Min(value = 1, message = "나이는 1이상 입니다.")
    @Max(value = 30, message = "나이는 30이하 입니다.")
    private int age;

    @NotBlank(message = "품종은 비워둘 수 없습니다.")
    private String breed;

    private String sex;

    @Size(max = 255, message = "색깔은 255자 이하로 작성 가능합니다.")
    private String color;

    @NotBlank(message = "중성화여부는 비워둘 수 없습니다.")
    private boolean isNeutering;

    @Size(max = 255, message = "특징은 255자 이하로 작성 가능합니다.")
    private String feature;
}
