package khasanov.payu.shorturl.util;

import org.springframework.stereotype.Component;

import java.util.zip.CRC32;

@Component
public class UrlShorten {
    private static final char[] ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_".toCharArray();

    public String encode(String url) {
        CRC32 crc32 = new CRC32();
        crc32.update(url.getBytes());
        return encodeToAlphabet(crc32.getValue());
    }

    static String encodeToAlphabet(long hash) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(ALPHABET[(int) (hash & 077)]);
            hash = hash >>> 0x6;
        } while (hash > 0);
        return sb.toString();
    }
}
