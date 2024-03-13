package org.example;

import io.grpc.stub.StreamObserver;
import ru.otus.grpc.proto.BookOuterClass;
import ru.otus.grpc.proto.BookOuterClass.Book;
import ru.otus.grpc.proto.BookServiceGrpc;

import java.util.concurrent.CopyOnWriteArrayList;

public class BooksService extends BookServiceGrpc.BookServiceImplBase {
    private CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<>();

    @Override
    public void createBook(Book request, StreamObserver<BookOuterClass.Empty> responseObserver) {
        books.add(request);
        responseObserver.onNext(BookOuterClass.Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void newBooks(BookOuterClass.Empty request, StreamObserver<Book> responseObserver) {
        for (Book book : books) {
            responseObserver.onNext(book);
        }
        responseObserver.onCompleted();
        super.newBooks(request, responseObserver);
    }
}
