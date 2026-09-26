package com.hms.auth.service;

import com.hms.auth.dto.LoginRequest;
import com.hms.auth.dto.RegisterRequest;
import com.hms.auth.entity.User;
import com.hms.auth.exception.DuplicateResourceException;
import com.hms.auth.exception.UnauthorizedException;
import com.hms.auth.mapper.UserMapper;
import com.hms.auth.repository.UserRepository;
import com.hms.auth.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_duplicateUsername_throws() {
        RegisterRequest request = RegisterRequest.builder()
                .username("mahoraga").password("password123").role("ADMIN").build();
        when(userRepository.existsByUsername("mahoraga")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(DuplicateResourceException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void login_wrongPassword_throwsUnauthorized() {
        User user = User.builder().username("mahoraga").passwordHash("hashed").status("ACTIVE").role("ADMIN").build();
        LoginRequest request = LoginRequest.builder().username("mahoraga").password("wrong").build();

        when(userRepository.findByUsername("mahoraga")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hashed")).thenReturn(false);

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(UnauthorizedException.class);
    }
}
