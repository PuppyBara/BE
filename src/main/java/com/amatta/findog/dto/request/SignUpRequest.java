package com.amatta.findog.dto.request;

import com.amatta.findog.domain.Address;
import com.amatta.findog.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "ID는 필수 입력 항목입니다.")
    @Size(min = 5, max = 20, message = "ID는 5자 이상 20자 이하로 입력해야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 5, max = 20, message = "비밀번호는 5자 이상 20자 이하로 입력해야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "주소1은 필수 입력 항목입니다.")
    private String address1;
    private String address2;

    public Member toEntity(String encodedPassword){
        return Member.createMember(name, id, encodedPassword,
                Address.createAddress(address1, address2));
    }
}
