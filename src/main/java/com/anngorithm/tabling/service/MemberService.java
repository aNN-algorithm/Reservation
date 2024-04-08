package com.anngorithm.tabling.service;

import com.anngorithm.tabling.Repository.MemberRepository;
import com.anngorithm.tabling.domain.Auth;
import com.anngorithm.tabling.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));
    }

    // 회원 가입
    public Member register(Auth.SignUp member) {
        // 계정 유무 확인
        boolean exists = this.memberRepository.existsByUsername(member.getUsername());
        if (exists) {
            throw new RuntimeException("이미 사용중인 아이디입니다.");
        }

        // 비밀번호 암호화
        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        return this.memberRepository.save(member.toEntity());
    }

    // 로그인
    public Member authenticate(Auth.SignIn member) {
        // 계정 데이터를 받아오고 / 없는 경우 에러 발생
        var user = this.memberRepository.findByUsername(member.getUsername())
                            .orElseThrow(() -> new RuntimeException("존재하지 않는 ID입니다."));

        // 입력한 패스워드의 암호화값 = DB에 저장된 (이미 암호화된) 패스워드 비교
        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 해당 계정 반환
        return user;
    }

}
