package khasanov.payu.shorturl.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlEncoderTest {

    @Test
    public void itGeneratesHashWithAllowedSymbols() {
        assertThat(UrlEncoder.encode("https://corporate.payu.com/"))
                .containsPattern("[0-9a-zA-Z_-]{1,6}");
    }

    @Test
    public void testEncodeAlphabet() {
        assertThat(UrlEncoder.encodeToAlphabet(0)).isEqualTo("0");
        assertThat(UrlEncoder.encodeToAlphabet(9)).isEqualTo("9");
        assertThat(UrlEncoder.encodeToAlphabet(10)).isEqualTo("a");
        assertThat(UrlEncoder.encodeToAlphabet(35)).isEqualTo("z");
        assertThat(UrlEncoder.encodeToAlphabet(36)).isEqualTo("A");
        assertThat(UrlEncoder.encodeToAlphabet(61)).isEqualTo("Z");
        assertThat(UrlEncoder.encodeToAlphabet(62)).isEqualTo("-");
        assertThat(UrlEncoder.encodeToAlphabet(63)).isEqualTo("_");

        assertThat(UrlEncoder.encodeToAlphabet(0100)).isEqualTo("01");
        assertThat(UrlEncoder.encodeToAlphabet(0144)).isEqualTo("A1");
        assertThat(UrlEncoder.encodeToAlphabet(0777)).isEqualTo("_7");
        assertThat(UrlEncoder.encodeToAlphabet(07777777777)).isEqualTo("_____");
    }

}