package khasanov.payu.shorturl.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlShortenTest {
    private UrlShorten urlShorten = new UrlShorten();

    @Test
    public void itGeneratesHashWithAllowedSymbols() {
        assertThat(urlShorten.encode("https://corporate.payu.com/"))
                .containsPattern("[0-9a-zA-Z_-]{1,6}");
    }

    @Test
    public void testEncodeAlphabet() {
        assertThat(UrlShorten.encodeToAlphabet(0)).isEqualTo("0");
        assertThat(UrlShorten.encodeToAlphabet(9)).isEqualTo("9");
        assertThat(UrlShorten.encodeToAlphabet(10)).isEqualTo("a");
        assertThat(UrlShorten.encodeToAlphabet(35)).isEqualTo("z");
        assertThat(UrlShorten.encodeToAlphabet(36)).isEqualTo("A");
        assertThat(UrlShorten.encodeToAlphabet(61)).isEqualTo("Z");
        assertThat(UrlShorten.encodeToAlphabet(62)).isEqualTo("-");
        assertThat(UrlShorten.encodeToAlphabet(63)).isEqualTo("_");

        assertThat(UrlShorten.encodeToAlphabet(0100)).isEqualTo("01");
        assertThat(UrlShorten.encodeToAlphabet(0144)).isEqualTo("A1");
        assertThat(UrlShorten.encodeToAlphabet(0777)).isEqualTo("_7");
        assertThat(UrlShorten.encodeToAlphabet(07777777777)).isEqualTo("_____");
    }

}