package khasanov.payu.shorturl.service;

import org.springframework.stereotype.Service;

@Service
public class ShortUrlService {
    private static final String DUMMY_TARGET_URI = "https://corporate.payu.com/";

    public String get(String id) {
        return DUMMY_TARGET_URI;
    }
}
