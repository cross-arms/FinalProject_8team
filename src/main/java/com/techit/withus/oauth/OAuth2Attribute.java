package com.techit.withus.oauth;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2Attribute
{
    private String provider;
    private String email;

    public static OAuth2Attribute of(String provider,
                                     Map<String, Object> attributes)
    {
        switch (provider.toLowerCase()) {
            case "google" -> {
                return ofGoogle(provider, attributes);
            }
            case "naver" -> {
                return ofNaver(provider, attributes);
            }
            case "kakao" -> {
                return ofKakao(provider, attributes);
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private static OAuth2Attribute ofGoogle(String provider,
                                            Map<String, Object> attributes)
    {
        return OAuth2Attribute
                .builder()
                .provider(provider.toUpperCase())
                .email((String) attributes.get("email"))
                .build();
    }

    private static OAuth2Attribute ofNaver(String provider,
                                           Map<String, Object> attributes)
    {
        Map<String, Object> naverAttribute
                = (Map<String, Object>) attributes.get("response");
        return OAuth2Attribute
                .builder()
                .provider(provider.toUpperCase())
                .email((String) naverAttribute.get("email"))
                .build();
    }

    private static OAuth2Attribute ofKakao(String provider,
                                           Map<String, Object> attributes)
    {
        Map<String, Object> kakaoAttribute
                = (Map<String, Object>) attributes.get("kakao_account");
        return OAuth2Attribute
                .builder()
                .provider(provider.toUpperCase())
                .email((String) kakaoAttribute.get("email"))
                .build();
    }

    public Map<String, Object> toMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("provider", this.provider);
        map.put("email", this.email);
        return map;
    }
}
