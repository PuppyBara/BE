package com.amatta.findog.dto.request;

import com.amatta.findog.domain.Address;
import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.Shelter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SignUpShelterRequest {

    @NotBlank(message = "ID는 필수 입력 항목입니다.")
    @Size(min = 5, max = 20, message = "ID는 5자 이상 20자 이하로 입력해야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 5, max = 20, message = "비밀번호는 5자 이상 20자 이하로 입력해야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    public Shelter toShelterEntity(String encodedPassword){
        return Shelter.createShelter(name, id, encodedPassword);
    }

}
