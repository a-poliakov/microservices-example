package org.example;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.otus.grpc.proto.BookOuterClass;
import ru.otus.grpc.proto.BookServiceGrpc;

import java.util.Iterator;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 15001)
                .usePlaintext()
                .build();
        var booksStub = BookServiceGrpc.newBlockingStub(channel);
        BookServiceGrpc.BookServiceFutureStub bookServiceFutureStub = BookServiceGrpc.newFutureStub(channel);
        ListenableFuture<BookOuterClass.Empty> future = bookServiceFutureStub.createBook(BookOuterClass.Book.newBuilder().setName("C").build());
        System.out.println(future.isDone());
        future.addListener(() -> System.out.println("completed"), Executors.newSingleThreadExecutor());
        BookOuterClass.Empty result1 = booksStub.createBook(BookOuterClass.Book.newBuilder().setName("A").build());
        BookOuterClass.Empty result2 = booksStub.createBook(BookOuterClass.Book.newBuilder().setName("B").build());
        Iterator<BookOuterClass.Book> bookIterator = booksStub.newBooks(BookOuterClass.Empty.newBuilder().build());
        for (Iterator<BookOuterClass.Book> it = bookIterator; it.hasNext(); ) {
            BookOuterClass.Book book = it.next();
            System.out.println(book);
        }
        channel.shutdownNow();
    }
}