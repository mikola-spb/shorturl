package khasanov.payu.shorturl.web;

public class TargetUrlNotFound extends RuntimeException {

    public TargetUrlNotFound(String s) {
        super("No target URL for " + s);
    }
}
