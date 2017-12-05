package khasanov.payu.shorturl.web;

import khasanov.payu.shorturl.service.ShortUrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShortUrlControllerTest {
    @Mock
    private ShortUrlService shortUrlService;

    @InjectMocks
    private ShortUrlController controller;

    @Test
    public void resolveUrl_existingId() {
        when(shortUrlService.get("http://localhost:8080/payu"))
                .thenReturn("https://corporate.payu.com/");

        ResponseEntity response = controller.resolveUrl(mockRequest("http://localhost:8080/payu"));

        assertThat(response.getStatusCode().is3xxRedirection())
                .isTrue();
        assertThat(response.getHeaders())
                .containsEntry(HttpHeaders.LOCATION, singletonList("https://corporate.payu.com/"));
    }

    @Test
    public void resolveUrl_notExistingId() {
        when(shortUrlService.get("404"))
                .thenReturn(null);

        ResponseEntity response = controller.resolveUrl(mockRequest("http://localhost:8080/404"));

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void saveUrl() {
        when(shortUrlService.put("https://corporate.payu.com/"))
                .thenReturn("http://localhost:8080/payu");

        ShortUrlResponse response = controller.save("https://corporate.payu.com/");

        assertThat(response)
                .hasFieldOrPropertyWithValue("shortUrl", "http://localhost:8080/payu")
                .hasFieldOrPropertyWithValue("targetUrl", "https://corporate.payu.com/");
    }

    private static MockHttpServletRequest mockRequest(String url) {
        return new MockHttpServletRequest("GET", url);
    }

}