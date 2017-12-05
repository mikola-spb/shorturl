package khasanov.payu.shorturl.web;

import khasanov.payu.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Controller
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    @Autowired
    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "registry/index.html";
    }

    @RequestMapping(value = "/registry", method = RequestMethod.POST)
    @ResponseBody
    public ShortUrlResponse save(@RequestParam("targetUrl") String targetUrl) {
        assertTargetUrl(targetUrl);

        return new ShortUrlResponse(
                shortUrlService.put(targetUrl),
                targetUrl
        );

    }

    private void assertTargetUrl(String url) {
        try {
            new URL(url);
            // everything is fine at this point

        } catch (MalformedURLException e) {
            throw new RuntimeException("The following URL is malformed: " + url, e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity resolveUrl(HttpServletRequest httpRequest) {
        String targetUri = shortUrlService.get(httpRequest.getRequestURL().toString());

        if (targetUri != null) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(new URI(targetUri));
                return new ResponseEntity(headers, HttpStatus.SEE_OTHER);

            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
