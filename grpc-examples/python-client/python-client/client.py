"""The Python implementation of the GRPC helloworld.Greeter client."""

from __future__ import print_function

import logging

import grpc
import book_pb2
import book_pb2_grpc

# python -m grpc_tools.protoc -I./proto --python_out=. --pyi_out=. --grpc_python_out=. ./proto/book.proto


def run():
    # NOTE(gRPC Python Team): .close() is possible on a channel and should be
    # used in circumstances in which the with statement does not fit the needs
    # of the code.
    print("Will try to greet world ...")
    with grpc.insecure_channel("localhost:15001") as channel:
        booksStub = book_pb2_grpc.BookServiceStub(channel)
        result1 = booksStub.CreateBook(book_pb2.Book(name="pA"))
        result2 = booksStub.CreateBook(book_pb2.Book(name="pB"))
        bookIterator = booksStub.NewBooks(book_pb2.Empty())
        for book in bookIterator:
            print(book)



if __name__ == "__main__":
    logging.basicConfig()
    run()