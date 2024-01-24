package org.example.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {
    @Autowired
    private Tracer tracer;

    @GetMapping("")
    public String testGet() {
        // Start a span. If there was a span present in this thread it will become
// the `newSpan`'s parent.
        Span newSpan = this.tracer.nextSpan().name("calculateTax");
        try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
            // ...
            // You can tag a span
            newSpan.tag("taxValue", "test get");
            // ...
            // You can log an event on a span
            newSpan.event("taxCalculated");
            return "OK";
        }
        finally {
            // Once done remember to end the span. This will allow collecting
            // the span to send it to a distributed tracing system e.g. Zipkin
            newSpan.end();
        }
    }
}
