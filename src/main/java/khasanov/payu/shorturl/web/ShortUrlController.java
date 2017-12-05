package khasanov.payu.shorturl.web;

import khasanov.payu.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

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
        String normalizedTargetUrl = assertAndNormalizeTargetUrl(targetUrl);

        return new ShortUrlResponse(
                shortUrlService.put(normalizedTargetUrl),
                normalizedTargetUrl
        );

    }

    private String assertAndNormalizeTargetUrl(String rawUrl) {
        try {
            URI uri = new URI(rawUrl);
            if (uri.getScheme() == null) {
                uri = new URI("http://" + rawUrl);
            }
            return uri.toURL().toExternalForm();
            // everything is fine at this point

        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException("The following URL is invalid: " + rawUrl, e);
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
            throw new TargetUrlNotFound(httpRequest.getRequestURL().toString());
        }
    }

    @ExceptionHandler(TargetUrlNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTargetUrlNotFound(TargetUrlNotFound e) {
        return "error/404";
    }
}
