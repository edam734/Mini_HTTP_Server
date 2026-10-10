package com.edam.minihttpserver.core;

import com.edam.minihttpserver.http.HttpRequest;
import com.edam.minihttpserver.http.HttpRequestParser;
import com.edam.minihttpserver.http.HttpResponse;
import com.edam.minihttpserver.http.HttpResponseWriter;
import com.edam.minihttpserver.routing.Router;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final int port;
    private final Router router;

    public HttpServer(int port, Router router) {
        this.port = port;
        this.router = router;
    }

    public void startServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            boolean running = true;
            while (running) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        }
    }

    private void handleClient(Socket clientSocket) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                clientSocket.getInputStream());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                clientSocket.getOutputStream());

        HttpRequestParser httpRequestParser = new HttpRequestParser(bufferedInputStream);
        HttpRequest request = httpRequestParser.parse();

        HttpResponse response = router.handle(request);

        HttpResponseWriter httpResponseWriter = new HttpResponseWriter(bufferedOutputStream);
        httpResponseWriter.write(response);
    }
}
