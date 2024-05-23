package com.github.nikiene.todo_list.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.github.nikiene.todo_list.repositories.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks")) {
            var authEncoded = request.getHeader("Authorization");
            authEncoded.substring("Basic".length()).trim();

            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
            var authDecodedString = new String(authDecoded);

            var credentials = authDecodedString.split(":");
            var username = credentials[0];
            var password = credentials[1];

            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            Result verifyer = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray());

            if (!verifyer.verified) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            request.setAttribute("ownerUserID", user.getId());
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}