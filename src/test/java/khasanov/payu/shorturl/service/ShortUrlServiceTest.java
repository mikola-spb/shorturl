package khasanov.payu.shorturl.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortUrlServiceTest {
    private ShortUrlService service = new ShortUrlService();

    @Test
    public void idIsAssignedForValidUrl() {
        assertThat(service.put("https://corporate.payu.com/"))
                .containsPattern("[0-9a-zA-Z]{3,8}");
    }

    @Test
    public void sameIdForSameUrl() {
        assertThat(service.put("https://corporate.payu.com/"))
                .isEqualTo(service.put("https://corporate.payu.com/"));
    }

    @Test
    public void addedUrlIsResolvableById() {
        assertThat(service.put("https://corporate.payu.com/"))
                .satisfies(id -> assertThat(service.get(id)).isEqualTo("https://corporate.payu.com/"));
    }
}
