package khasanov.payu.shorturl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShortUrlService {
    private final Map<String, String> storage = new ConcurrentHashMap<>();

    @Value("${baseShortUrl:http://localhost:8080/}")
    private String baseShortUrl;

    public String put(String targetUrl) {
        String shortUrl = baseShortUrl + generateId(targetUrl);
        storage.put(shortUrl, targetUrl);
        return shortUrl;
    }

    private String generateId(String url) {
        return "" + url.hashCode();
    }

    public String get(String id) {
        return storage.get(id);
    }
}
