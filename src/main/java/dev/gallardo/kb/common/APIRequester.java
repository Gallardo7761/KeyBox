package dev.gallardo.kb.common;

public class APIRequester {
    private static APIRequester instance;

    public static APIRequester getInstance() {
        if (instance == null) {
            instance = new APIRequester();
        }
        return instance;
    }

    private APIRequester() {}

    public void request(String uri) {

    }
}
