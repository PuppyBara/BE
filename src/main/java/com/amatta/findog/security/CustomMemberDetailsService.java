package com.amatta.findog.security;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.Shelter;
import com.amatta.findog.repository.MemberRepository;
import com.amatta.findog.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final ShelterRepository shelterRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(username);
        if (optionalMember.isPresent()) {
            return createUserDetails(optionalMember.get());
        }

        // 일반 회원에 없으면 기업회원에서 찾음
        Optional<Shelter> optionalShelter = shelterRepository.findById(username);
        if (optionalShelter.isPresent()) {
            return createUserDetails(optionalShelter.get());
        }

        throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    // 해당 Member데이터 존재하면 UserDetails로 변환
    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getId())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();
    }
    private UserDetails createUserDetails(Shelter shelter) {
        return User.builder()
                .username(shelter.getId())
                .password(shelter.getPassword())
                .roles(shelter.getRole())
                .build();
    }
}
