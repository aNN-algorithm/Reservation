package com.anngorithm.tabling.domain;

import com.anngorithm.tabling.domain.entity.Member;
import lombok.Data;

import java.util.List;

public class Auth {

    @Data
    public static class SignIn {
        private String username;
        private String password;
    }

    @Data
    public static class SignUp {
        private String username;
        private String password;
        private String roles;
        private String number;

        public Member toEntity() {
            return Member.builder()
                        .username(this.username)
                        .password(this.password)
                        .roles(this.roles)
                        .number(this.number)
                        .build();
        }
    }
}
