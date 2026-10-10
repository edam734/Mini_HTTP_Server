package com.edam.minihttpserver.core;

import com.edam.minihttpserver.http.HttpRequest;
import com.edam.minihttpserver.http.HttpRequestParser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public void startServer(int port) throws IOException {
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
        HttpRequest httpRequest = httpRequestParser.getRequest();
        System.out.println(httpRequest.toString());
    }
}
