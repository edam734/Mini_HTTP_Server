package com.edam.minihttpserver.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


/**
 * Serializes HTTP responses and writes them to an output stream.
 *
 * <p>Handles the HTTP/1.1 response format, including the status line,
 * headers, Content-Length calculation, and optional body.
 */
public class HttpResponseWriter {

    private OutputStream outputStream;

    public HttpResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }


    /**
     * Writes an HTTP response to the output stream.
     *
     * <p>The response is serialized in the following order:
     * <ol>
     *     <li>Status line (HTTP version, status code, and reason phrase).</li>
     *     <li>Response headers, each terminated by CRLF (\r\n).</li>
     *     <li>An empty line (CRLF) separating headers from the body.</li>
     *     <li>Optional response body, encoded in UTF-8.</li>
     * </ol>
     *
     * <p>Automatically calculates the Content-Length header based on
     * the number of bytes in the encoded body.
     *
     * @param response the HTTP response to serialize and write
     * @throws IOException if an I/O error occurs while writing the response
     */
    public void write(HttpResponse response) throws IOException {
        byte[] body = response.getBody().getBytes(StandardCharsets.UTF_8);

        StringBuilder sb = new StringBuilder();
        sb.append(response.getVersion())
                .append(" ")
                .append(response.getStatus().toString())
                .append("\r\n");

        Map<String, List<String>> headers = response.getHeaders();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            for (String value : entry.getValue()) {
                sb.append(entry.getKey()).append(": ").append(value).append("\r\n");
            }
        }
        sb.append("Content-Length: ").append(body.length).append("\r\n\r\n");

        outputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        outputStream.write(body);
        outputStream.flush();
    }
}