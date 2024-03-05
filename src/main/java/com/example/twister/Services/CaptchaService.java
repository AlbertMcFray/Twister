package com.example.twister.Services;

import com.example.twister.Domain.DTO.CaptchaResponseDto;
import com.example.twister.Services.Interfaces.ICaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CaptchaService implements ICaptchaService {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private final RestTemplate restTemplate;
    @Value("${recaptcha.secret}")
    private String secret;

    @Override
    public boolean verifyCaptcha(String captchaResponse){
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        return response != null && response.isSuccess();
    }
}
