package com.edam.minihttpserver.http;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * Parses incoming HTTP requests from an input stream.
 *
 * <p>HTTP/1.1 uses the following delimiters:
 * <ul>
 *     <li>{@code \r\n} - Marks the end of a line.</li>
 *     <li>{@code \r\n\r\n} - Marks the end of the header section.</li>
 * </ul>
 */
public class HttpRequestParser {
    private final InputStream inputStream;

    public HttpRequestParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Reads and parses an HTTP request from the input stream. Processes the request line, headers,
     * and optional body.
     *
     * @return the parsed {@link HttpRequest}, or {@code null} if the request is invalid or the
     * connection is closed
     * @throws IOException if an I/O error occurs while reading the request
     */
    public HttpRequest getRequest() throws IOException {
        String firstLine = realLine();
        if (firstLine == null) {
            return null;
        }
        String[] strings = firstLine.split(" ");
        if (strings.length != 3) {
            return null;
        }
        HttpRequest httpRequest = new HttpRequest(strings[0].trim(), strings[1].trim(),
                strings[2].trim());

        String line;
        while ((line = realLine()) != null && !line.isEmpty()) {
            String[] fullHeader = line.split(":", 2);
            if (fullHeader.length != 2 || fullHeader[0].isBlank()) {
                return null;
            }
            httpRequest.addHeader(fullHeader[0].toLowerCase(Locale.ROOT).trim(),
                    fullHeader[1].trim());
        }
        if (line == null) {
            return null;
        }

        List<String> encodingValue = httpRequest.getHeaderValue(
                "Transfer-Encoding".toLowerCase(Locale.ROOT));
        if (encodingValue != null && !encodingValue.isEmpty()) {
            return null;
        }
        List<String> lengthValues = httpRequest.getHeaderValue(
                "Content-Length".toLowerCase(Locale.ROOT));
        if (lengthValues != null) {
            if (lengthValues.size() == 1) {

                try {
                    int contentLength = Integer.parseInt(lengthValues.getFirst());
                    if (contentLength < 0) {
                        return null;
                    }
                    String body = readBody(contentLength);
                    httpRequest.setBody(body);
                } catch (NumberFormatException e) {
                    return null;
                }
            } else {
                return null;
            }
        }

        return httpRequest;
    }

    private String realLine() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        boolean endOfLine = false;
        int i = 0;
        int r = -1;
        int b;
        while (!endOfLine) {
            b = inputStream.read();
            if ((char) b == '\r') {
                if (r >= 0) {
                    return null; // invalid line
                } else {
                    r = i;
                }
            } else if ((char) b == '\n') {
                if (r == -1 || i != r + 1) {
                    return null; // invalid line
                }
                if (i == (r + 1)) {
                    endOfLine = true;
                } else {
                    return null; // invalid line
                }
            } else {
                byteArrayOutputStream.write(b);
            }
            i++;
        }
        return byteArrayOutputStream.toString(StandardCharsets.ISO_8859_1);
    }

    private String readBody(int contentLength) throws IOException {
        byte[] bytes = inputStream.readNBytes(contentLength);

        if (bytes.length != contentLength) {
            throw new EOFException("Incomplete HTTP body");
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
}