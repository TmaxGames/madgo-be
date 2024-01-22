package com.gostop.security.global.filter;

import com.gostop.security.domain.account.Account;
import com.gostop.security.global.exception.token.InvalidTokenException;
import com.gostop.security.domain.account.AccountRepository;
import com.gostop.security.domain.userInfo.UserInfoService;
import com.gostop.security.domain.jwt.JwtUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserInfoService userInfoService;
    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private List<HttpRequestPattern> WHITELIST_ANONYMOUS;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("asd");
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

//        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            Account account = accountRepository.findByName(username).orElseThrow(InvalidTokenException::new);
//            if(jwtUtil.validateToken(token, account)){
//                UserDetails userDetails = userInfoService.loadUserByUsername(username);
//                //현 토큰의 role과 세팅된 role이 같은지 확인
//                UsernamePasswordAuthenticationToken authToken
//                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//            filterChain.doFilter(request, response);
//        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        System.out.println("should not");
        String method = request.getMethod().toUpperCase();
        String uri = request.getRequestURI();

        boolean isValidSecurityApi = WHITELIST_ANONYMOUS.stream().anyMatch(
                pattern -> antPathMatcher.match(pattern.getUriPattern(), uri) && pattern.getHttpMethod().equals(method)
        );
        return isValidSecurityApi;
    }

    @PostConstruct //DI 이후 실행
    private void initWhiteLists(){
        String post = "POST";
        String get = "GET";
        String delete = "DELETE";
        WHITELIST_ANONYMOUS = new ArrayList<>();
        WHITELIST_ANONYMOUS.addAll(List.of(
                HttpRequestPattern.builder().httpMethod(post).uriPattern("/security/v1/account/sign-up").build(),
                HttpRequestPattern.builder().httpMethod(get).uriPattern("/swagger*/**").build(),
                HttpRequestPattern.builder().httpMethod(get).uriPattern("/v3/api-docs/**").build()
        ));
    }
}
