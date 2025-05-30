/*
 * Copyright 2022 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.loadbalance;

import io.grpc.*;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadBalanceClient {
    private static final Logger logger = Logger.getLogger(LoadBalanceClient.class.getName());

    public static final String exampleScheme = "example";
    public static final String exampleServiceName = "lb.example.grpc.io";

    public LoadBalanceClient(Channel channel) {

    }

    public void greet(String name) {

    }


    public static void main(String[] args) throws Exception {
        NameResolverRegistry.getDefaultRegistry().register(new ExampleNameResolverProvider());

        String target = String.format("%s:///%s", exampleScheme, exampleServiceName);

        logger.info("Use default first_pick load balance policy");
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        try {
            LoadBalanceClient client = new LoadBalanceClient(channel);
            for (int i = 0; i < 5; i++) {
                client.greet("request" + i);
            }
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }

        logger.info("Change to round_robin policy");
        channel = ManagedChannelBuilder.forTarget(target)
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext()
                .build();
        try {
            LoadBalanceClient client = new LoadBalanceClient(channel);
            for (int i = 0; i < 5; i++) {
                client.greet("request" + i);
            }
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
