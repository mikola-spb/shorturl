package khasanov.payu.shorturl.service;

import khasanov.payu.shorturl.repository.ShortUrlEntry;
import khasanov.payu.shorturl.repository.ShortUrlRepository;
import khasanov.payu.shorturl.util.UrlShorten;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlService {
    private final String baseShortUrl;

    private final ShortUrlRepository repository;
    private final UrlShorten urlShorten;

    @Autowired
    public ShortUrlService(
            @Value("${baseShortUrl:http://localhost:8080/}") String baseShortUrl,
            ShortUrlRepository repository, UrlShorten urlShorten) {
        this.baseShortUrl = baseShortUrl;
        this.repository = repository;
        this.urlShorten = urlShorten;
    }

    public String put(String targetUrl) {
        String shortUrl = baseShortUrl + generateId(targetUrl);
        repository.save(new ShortUrlEntry(shortUrl, targetUrl));
        return shortUrl;
    }

    private String generateId(String url) {
        return urlShorten.encode(url);
    }

    public String get(String shortUrl) {
        ShortUrlEntry entry = repository.findOne(shortUrl);
        return entry != null ? entry.getTargetUrl() : null;
    }
}
