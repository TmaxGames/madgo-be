package com.gostop.security.domain.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "NICKNAME")
    private String nickname;
//    @Column(name = "ROLE")
//    private String role;
//    @Column(name = "MONEY")
//    private Long money;
//    @Column(name = "SCORE")
//    private Long score;
//    @Column(name = "WIN")
//    private Long win;
//    @Column(name = "LOSE")
//    private Long lose;
//    @Column(name = "PROFILE_URL")
//    private String profile_url;
}
