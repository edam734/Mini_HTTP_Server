package com.edam.minihttpserver;

import com.edam.minihttpserver.core.HttpServer;

import java.io.IOException;

public class Main {

    static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer();
        server.startServer(12345);
    }
}