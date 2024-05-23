package com.github.nikiene.todo_list.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // get auth
        var authEncoded = request.getHeader("Authorization");
        authEncoded.substring("Basic".length()).trim();

        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
        var authDecodedString = new String(authDecoded);

        var credentials = authDecodedString.split(":");
        var username = credentials[0];
        var password = credentials[1];

        filterChain.doFilter(request, response);
    }

}
