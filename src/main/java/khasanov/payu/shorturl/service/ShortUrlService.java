package khasanov.payu.shorturl.service;

import khasanov.payu.shorturl.util.UrlEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShortUrlService {
    private final Map<String, String> storage = new ConcurrentHashMap<>();

    private final String baseShortUrl;

    public ShortUrlService(
            @Value("${baseShortUrl:http://localhost:8080/}") String baseShortUrl
    ) {
        this.baseShortUrl = baseShortUrl;
    }

    public String put(String targetUrl) {
        String shortUrl = baseShortUrl + generateId(targetUrl);
        storage.put(shortUrl, targetUrl);
        return shortUrl;
    }

    private String generateId(String url) {
        return UrlEncoder.encode(url);
    }

    public String get(String id) {
        return storage.get(id);
    }
}
