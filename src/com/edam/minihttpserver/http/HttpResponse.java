package com.edam.minihttpserver.http;

import java.util.*;

public class HttpResponse {

    private final String version;
    private final HttpStatus status;
    private final Map<String, List<String>> headers;

    private String body;

    public HttpResponse(HttpStatus status) {
        this.version = "HTTP/1.1";
        this.status = Objects.requireNonNull(status);
        headers = new HashMap<>();
        body = "";
    }

    public String getVersion() {
        return version;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void addHeader(String header, String value) {
        Objects.requireNonNull(header);
        Objects.requireNonNull(value);

        List<String> values = headers.get(header);
        if (values != null) {
            values.add(value);
        } else {
            values = new ArrayList<>();
            values.add(value);
        }
        headers.put(header, values);
    }

    public List<String> getHeaderValue(String header) {
        return headers.get(header);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = Objects.requireNonNull(body);
    }
}
