package com.edam.minihttpserver.http;

import java.util.*;

public class HttpRequest {

    private final String method;
    private final String path;
    private final String version;
    private final Map<String, List<String>> headers;
    private String body;

    public HttpRequest(String method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.headers = new HashMap<>();
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public void addHeader(String header, String value) {
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
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpRequest{ method='" +
                method + '\'' + ", path='" +
                path + '\'' + ", version='" +
                version + '\'' + ", headers=" +
                headers + ", body='" +
                body + '\'' + '}';
    }
}