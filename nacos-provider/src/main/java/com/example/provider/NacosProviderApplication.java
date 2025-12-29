package com.example.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@EnableFeignClients
public class NacosProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication.class, args);
    }

    @RestController
    class EchoController {

        @Autowired
        private ConsumerClient consumerClient;

        @GetMapping("/echo/{string}")
        public String echo(@PathVariable String string) {
            return "Hello Nacos Discovery " + string;
        }

        @GetMapping("/hello")
        public String hello() {
            return "Hello from Nacos Provider!";
        }

        @GetMapping("/call-consumer")
        public String callConsumer() {
            return "Provider called: " + consumerClient.hi();
        }
    }
}
