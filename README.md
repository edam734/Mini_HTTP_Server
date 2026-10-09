
# Mini HTTP Server

<br>

A lightweight HTTP server built from scratch using **Java SE**, without external frameworks.

<br>

## Overview

A lightweight HTTP server built entirely with Java SE, leveraging native networking and I/O APIs to handle client connections, process HTTP requests, and generate HTTP responses.

<br>

## Technologies

- Java SE
- TCP/IP Sockets
- Java I/O
- Multithreading
- HTTP/1.1 (basic implementation)

<br>

## Planned Features

- [ ] Accept incoming TCP connections using `ServerSocket`.
- [ ] Read and parse HTTP requests.
- [ ] Support HTTP GET requests.
- [ ] Implement basic URL routing.
- [ ] Return HTML and JSON responses.
- [ ] Handle HTTP status codes (200, 400, 404, 500).
- [ ] Handle multiple clients concurrently.
- [ ] Implement error handling and logging.
- [ ] Add unit and integration tests.

<br>

## Development Roadmap

### Phase 1 — Networking
Establish TCP connections between clients and the server.

### Phase 2 — HTTP
Parse incoming requests and generate valid HTTP responses.

### Phase 3 — Routing
Handle different URL paths and return the appropriate content.

### Phase 4 — Concurrency
Use multithreading to process multiple client connections.

### Phase 5 — Testing
Test server functionality, error handling, and concurrent requests.

<br>

## Getting Started

Instructions for compiling and running the server will be added once the initial implementation is complete.

<br>

## Project Scope

This project is intended for educational purposes and does not aim to provide a production-ready HTTP server or a complete implementation of the HTTP/1.1 specification.

<br>
