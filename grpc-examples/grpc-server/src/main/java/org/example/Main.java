package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Main {
    public static void server() throws IOException, InterruptedException {

        Server server = ServerBuilder
                .forPort(15001)
                .addService(new GreeterService())
                .addService(new BooksService())
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));

        server.start();
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello world!");
        server();
    }
}