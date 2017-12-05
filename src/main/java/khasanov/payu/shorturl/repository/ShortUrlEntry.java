package khasanov.payu.shorturl.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "urls")
public class ShortUrlEntry {
    @Id
    private String shortUrl;

    private String targetUrl;

    public ShortUrlEntry() {
    }

    public ShortUrlEntry(String shortUrl, String targetUrl) {
        this.shortUrl = shortUrl;
        this.targetUrl = targetUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public String toString() {
        return "ShortUrlEntry{"
                + "shortUrl='" + shortUrl + '\''
                + ", targetUrl='" + targetUrl + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShortUrlEntry that = (ShortUrlEntry) o;
        return Objects.equals(shortUrl, that.shortUrl) &&
                Objects.equals(targetUrl, that.targetUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortUrl, targetUrl);
    }
}
