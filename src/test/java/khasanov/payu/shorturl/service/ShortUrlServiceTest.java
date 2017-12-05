package khasanov.payu.shorturl.service;

import khasanov.payu.shorturl.repository.ShortUrlEntry;
import khasanov.payu.shorturl.repository.ShortUrlRepository;
import khasanov.payu.shorturl.util.UrlShorten;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShortUrlServiceTest {
    private final static ShortUrlEntry PAYU_ENTRY = new ShortUrlEntry("http://sh.rt/payu", "https://corporate.payu.com/");

    private ShortUrlRepository repository = mock(ShortUrlRepository.class);
    private UrlShorten urlShorten = mock(UrlShorten.class);

    private ShortUrlService service = new ShortUrlService("http://sh.rt/", repository, urlShorten);

    @Before
    public void setupMocks() {
    }

    @Test
    public void shortUrlIsGeneratedForValidTargetUrl() {
        when(urlShorten.encode("https://corporate.payu.com/"))
                .thenReturn("payu");
        when(repository.save(eq(PAYU_ENTRY)))
                .thenReturn(PAYU_ENTRY);

        assertThat(service.put("https://corporate.payu.com/"))
                .isEqualTo("http://sh.rt/payu");

        verify(repository).save(eq(PAYU_ENTRY));
    }

    @Test
    public void registeredTargetUrlIsResolvable() {
        when(repository.getOneByShortUrl("http://sh.rt/payu"))
                .thenReturn(PAYU_ENTRY);

        assertThat(service.get("http://sh.rt/payu"))
                .isEqualTo("https://corporate.payu.com/");
    }

    @Test
    public void unregisteredTargetUrlIsNotResolvable() {
        when(repository.getOneByShortUrl(any()))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThat(service.get("http://sh.rt/payu"))
                .isNull();
    }
}
