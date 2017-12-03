package khasanov.payu.shorturl.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShortUrlService {
    private final Map<String, String> storage = new ConcurrentHashMap<>();

    public String put(String url) {
        String id = generateId(url);
        storage.put(id, url);
        return id;
    }

    private String generateId(String url) {
        return "" + url.hashCode();
    }

    public String get(String id) {
        return storage.get(id);
    }
}
