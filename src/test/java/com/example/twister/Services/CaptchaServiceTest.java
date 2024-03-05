package com.example.twister.Services;

import com.example.twister.Domain.DTO.CaptchaResponseDto;
import com.example.twister.Services.CaptchaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CaptchaServiceTest {

    @InjectMocks
    private CaptchaService captchaService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void verifyCaptchaTest() {
        String captchaResponse = "testCaptchaResponse";
        CaptchaResponseDto captchaResponseDto = CaptchaResponseDto.builder()
                .success(true)
                .build();

        when(restTemplate.postForObject(anyString(), any(), any(Class.class)))
                .thenReturn(captchaResponseDto);

        boolean result = captchaService.verifyCaptcha(captchaResponse);

        assertTrue(result);
    }
}

