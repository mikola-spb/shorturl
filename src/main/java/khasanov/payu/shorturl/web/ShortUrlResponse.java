package khasanov.payu.shorturl.web;

public class ShortUrlResponse {
    private String shortUrl;
    private String targetUrl;

    public ShortUrlResponse(String shortUrl, String targetUrl) {
        this.shortUrl = shortUrl;
        this.targetUrl = targetUrl;
    }
}
