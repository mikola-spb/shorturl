package khasanov.payu.shorturl.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortUrlRepository extends MongoRepository<ShortUrlEntry, String> {
    ShortUrlEntry getOneByShortUrl(String shortUrl);
}
