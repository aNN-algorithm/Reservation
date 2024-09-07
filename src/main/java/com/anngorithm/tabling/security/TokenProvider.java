package com.anngorithm.tabling.security;

import com.anngorithm.tabling.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 7; // 1시간

    private static final String KEY_ROLES = "role";

    private final MemberService memberService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    // 토큰 생성(발급)
    // input : 사용자 이름 / 권한
    public String generateToken(String username, String roles) {
        System.out.println("generateToken");
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles); // key-value 타입으로 저장

        var now = new Date(); // 토큰이 생성되는 시간
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME); // 토큰 만료 시간
        return Jwts.builder()
                .setClaims(claims) // Payload에 담길 Claims값 저장
                .setIssuedAt(now) // 생성 시간
                .setExpiration(expiredDate) // 만료 시간
                .signWith(SignatureAlgorithm.HS512, this.secretKey) // 서명, 암호화 알고리즘 / 비밀키
                .compact(); // 토큰 생성
    }

    public Authentication getAuthentication(String jwt) {
        System.out.println("getAuthentication");
        // 사용자의 정보 가져오기
        UserDetails userDetails = this.memberService.loadUserByUsername(this.getUsername(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        System.out.println("getUsername");
        return this.parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        System.out.println("validateToken");
        if (!StringUtils.hasText(token))  return false;

        var claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    // 토큰으로부터 클레임 정보 가져오기
    private Claims parseClaims(String token) {
        System.out.println("parseClaims");

        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
