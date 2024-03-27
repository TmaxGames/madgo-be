package com.gostop.security.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gostop.security.global.dto.ErrorResponseDto;
import com.gostop.security.global.exception.CustomException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FilterExceptionHandler extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            response.setStatus(e.getErrorCode().getStatus());
        }catch (JwtException e){
            int status = 401;
            String errorJsonResponse = new ObjectMapper().writeValueAsString(new ErrorResponseDto(status, e.getMessage()));
            response.setStatus(status);
            response.getWriter().write(errorJsonResponse);
        } catch (Exception e){
            int status = 500;
            String message = "internal server error";
            String errorJsonResponse = new ObjectMapper().writeValueAsString(new ErrorResponseDto(status, message));
            response.setStatus(status);
            response.getWriter().write(errorJsonResponse);
        }
    }
}