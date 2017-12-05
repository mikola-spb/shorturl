package khasanov.payu.shorturl.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortUrlServiceTest {
    private ShortUrlService service = new ShortUrlService("http://sh.rt/");

    @Test
    public void shortUrlIsGeneratedForValidTargetUrl() {
        assertThat(service.put("https://corporate.payu.com/"))
                .containsPattern("http://sh.rt/[0-9a-zA-Z_-]{1,6}");
    }

    @Test
    public void sameShortUrlForSameTargetUrl() {
        assertThat(service.put("https://corporate.payu.com/"))
                .isEqualTo(service.put("https://corporate.payu.com/"));
    }

    @Test
    public void addedTargetUrlIsResolvableByShortUrl() {
        assertThat(service.put("https://corporate.payu.com/"))
                .satisfies(id -> assertThat(service.get(id)).isEqualTo("https://corporate.payu.com/"));
    }
}
