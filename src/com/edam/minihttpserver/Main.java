package com.edam.minihttpserver;

import com.edam.minihttpserver.core.HttpServer;
import com.edam.minihttpserver.routing.Router;

import java.io.IOException;

public class Main {

    static void main(String[] args) throws IOException {
        Router router = new Router();
        HttpServer server = new HttpServer(8080, router);
        server.startServer();
    }
}