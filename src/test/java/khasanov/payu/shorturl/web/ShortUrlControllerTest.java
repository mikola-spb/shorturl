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
        when(shortUrlService.get("payu"))
                .thenReturn("https://corporate.payu.com/");

        ResponseEntity response = controller.resolveUrl("payu");

        assertThat(response.getStatusCode().is3xxRedirection())
                .isTrue();
        assertThat(response.getHeaders())
                .containsEntry(HttpHeaders.LOCATION, singletonList("https://corporate.payu.com/"));
    }

    @Test
    public void resolveUrl_notExistingId() {
        when(shortUrlService.get("404"))
                .thenReturn(null);

        ResponseEntity response = controller.resolveUrl("payu");

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

}