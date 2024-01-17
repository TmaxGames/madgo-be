package com.gostop.security.filter;

import com.gostop.security.entity.redis.AccountSession;
import com.gostop.security.exception.session.SessionExpiredException;
import com.gostop.security.repository.redis.SessionRepository;
import com.gostop.security.exception.session.SessionNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomSecurityFilter extends OncePerRequestFilter {
    //user 디파인 화이트 리스트 정의 -> security api 는 화이트 리스트 (세션이 없어도 접근가능)
    //추후 세션이 있어야만 접근 가능한 api 도 정의
    private final SessionRepository sessionRepository;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private List<HttpRequestPattern> WHITELIST_ANONYMOUS;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        //세션이 없는데 유저 api에 접근을 하려한 경우
        AccountSession accountSession = sessionRepository.findById(sessionId).orElseThrow(SessionNotFoundException::new);
        if(accountSession.isExpiredSession()){
            throw new SessionExpiredException();
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
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
                HttpRequestPattern.builder().httpMethod(post).uriPattern("/security/v1/session/login").build(),
                HttpRequestPattern.builder().httpMethod(post).uriPattern("/security/v1/session/logout").build(),
                HttpRequestPattern.builder().httpMethod(post).uriPattern("/security/v1/account/sign-up").build(),
                HttpRequestPattern.builder().httpMethod(get).uriPattern("/swagger*/**").build(),
                HttpRequestPattern.builder().httpMethod(get).uriPattern("/v3/api-docs/**").build()
        ));
    }
}
