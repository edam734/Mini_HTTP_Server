package com.edam.minihttpserver.routing;

import com.edam.minihttpserver.http.HttpRequest;
import com.edam.minihttpserver.http.HttpResponse;
import com.edam.minihttpserver.http.HttpStatus;

/**
 * Handles HTTP requests by evaluating their methods and paths and generating the appropriate HTTP
 * responses.
 *
 * <p>Each response contains an HTTP status, optional headers,
 * and an optional body. The serialization of the response is delegated to
 * {@link com.edam.minihttpserver.http.HttpResponseWriter}.
 *
 * @author edam
 */

public class Router {

    /**
     * Processes an HTTP request and generates the corresponding response.
     *
     * <p>The response must contain an appropriate HTTP status code,
     * relevant headers (such as Content-Type), and an optional body.
     *
     * <p>The response is subsequently serialized into HTTP/1.1 format:
     * <pre>
     * HTTP/1.1 200 OK\r\n
     * Content-Type: text/plain; charset=UTF-8\r\n
     * Content-Length: 13\r\n
     * \r\n
     * Hello, World!
     * </pre>
     *
     * @param request the HTTP request to process
     * @return the generated HTTP response
     */
    public HttpResponse handle(HttpRequest request) {
        String method = request.getMethod();
        String path = request.getPath();
        HttpResponse response = null;

        if (method.equals("GET")) {
            if (path.equals("/") || path.equals("/hello")) {
                response = new HttpResponse(HttpStatus.OK);
                response.addHeader("Content-Type", "text/plain; charset=UTF-8");
                response.setBody("Hello, World!");
            } else {
                response = new HttpResponse(HttpStatus.NOT_FOUND);
            }
        }

        return response;
    }
}
