package com.example.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "nacos-provider")
public interface ProviderClient {

    @GetMapping("/hello")
    String hello();

    @GetMapping("/echo/{string}")
    String echo(@PathVariable("string") String string);
}
