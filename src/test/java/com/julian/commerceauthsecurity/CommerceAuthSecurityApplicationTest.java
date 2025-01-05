package com.julian.commerceauthsecurity;

import com.julian.commerceauthsecurity.configuration.RsaKeys;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@EnableConfigurationProperties(RsaKeys.class)
class CommerceAuthSecurityApplicationTest {

    @Test
    void main_ShouldRunSpringApplication() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            String[] args = {};
            mockedSpringApplication.when(() -> SpringApplication.run(CommerceAuthSecurityApplication.class, args)).thenReturn(null);

            // Act
            CommerceAuthSecurityApplication.main(args);

            // Assert
            mockedSpringApplication.verify(() -> SpringApplication.run(CommerceAuthSecurityApplication.class, args), times(1));
        }
    }
}
