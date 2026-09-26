package com.hms.auth.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    JwtServiceTest() {
        ReflectionTestUtils.setField(jwtService, "secret",
                "ZGV2LW9ubHktc2VjcmV0LWtleS1kby1ub3QtdXNlLWluLXByb2R1Y3Rpb24tMTIzNDU2Nzg=");
        ReflectionTestUtils.setField(jwtService, "expirationMs", 3600000L);
    }

    @Test
    void generateAndParseToken_roundTrips() {
        String token = jwtService.generateToken("mahoraga", "ADMIN");

        assertThat(jwtService.extractUsername(token)).isEqualTo("mahoraga");
        assertThat(jwtService.extractRole(token)).isEqualTo("ADMIN");
        assertThat(jwtService.isTokenValid(token, "mahoraga")).isTrue();
        assertThat(jwtService.isTokenValid(token, "someoneElse")).isFalse();
    }
}
