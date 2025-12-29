package com.example.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@EnableFeignClients
public class NacosConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }

    @RestController
    public class TestController {

        @Autowired
        private ProviderClient providerClient;

        @GetMapping("/echo-feign")
        public String echo() {
            return providerClient.hello();
        }

        @GetMapping("/echo-feign/{str}")
        public String echoStr(@PathVariable String str) {
            return providerClient.echo(str);
        }

        @GetMapping("/hi")
        public String hi() {
            return "Hi from Consumer!";
        }
    }
}
