package org.example;

import ru.otus.grpc.proto.GreeterGrpc;
import ru.otus.grpc.proto.Simple;

public class GreeterService extends GreeterGrpc.GreeterImplBase {
    public Simple.HelloReply sayHello(Simple.HelloRequest request) {
        return Simple.HelloReply.newBuilder().setMessage("Hello, " + request.getName()).build();
    }

    public Simple.CalculateResponse calculate(Simple.CalculateRequest request) {
        var result = 0.0;
        switch (request.getOperation()) {
            case PLUS:
                result = request.getA() + request.getB();
                break;
            case MINUS:
                result = request.getA() - request.getB();
                break;
            default: throw new UnsupportedOperationException("Unknown operation: " + request.getOperationValue());
        }
        return Simple.CalculateResponse.newBuilder()
                .setResult(result)
                .build();
    }
}
