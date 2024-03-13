package org.example;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.GeneratedMessage;
import ru.otus.grpc.proto.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static ru.otus.grpc.proto.Test.bar;

public class Main {
    public static void main(String[] args) {
        Test.Foo foo =
                Test.Foo.newBuilder()
                        .setExtension(bar, 1)
                        .build();
        assert foo.hasExtension(bar);
        assert foo.getExtension(bar) == 1;


        Test.Baz baz = Test.Baz.newBuilder().build();
        Test.Foo foo2 =
                Test.Foo.newBuilder()
                        .setExtension(Test.Baz.fooExt, baz)
                        .build();
        assert foo2.hasExtension(Test.Baz.fooExt);
        assert foo2.getExtension(Test.Baz.fooExt) == baz;

        String filename = "foo.protobuf";
        try(FileOutputStream output = new FileOutputStream(filename)) {
            foo.writeTo(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(FileInputStream input = new FileInputStream(filename)) {
            Test.Foo parseFrom = Test.Foo.parseFrom(input);
            System.out.println(parseFrom.getExtension(bar));

            ExtensionRegistry registry = ExtensionRegistry.newInstance();
            registry.add(bar);
            Test.Foo parseFrom2 = Test.Foo.parseFrom(input, registry);
            assert parseFrom2.hasExtension(bar);
            System.out.println(parseFrom2.hasExtension(bar));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}