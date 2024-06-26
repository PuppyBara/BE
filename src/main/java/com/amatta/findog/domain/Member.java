package com.amatta.findog.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "member")
@EqualsAndHashCode(of = "memberId")
public class Member extends MemberBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true, nullable = false)
    private Long memberId;

    private String role = "MEMBER";

    public static Member createMember(String name, String id, String password,
                                      Address address){
        Member newMember =  new Member();
        newMember.initializeMemberBaseEntity(name, id, password, address);
        return newMember;
    }



}
