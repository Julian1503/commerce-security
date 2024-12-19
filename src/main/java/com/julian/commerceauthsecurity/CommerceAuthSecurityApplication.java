package com.julian.commerceauthsecurity;

import com.julian.commerceauthsecurity.configuration.RsaKeys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeys.class)
public class CommerceAuthSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceAuthSecurityApplication.class, args);
    }

}
