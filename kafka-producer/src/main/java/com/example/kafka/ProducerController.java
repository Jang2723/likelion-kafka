package com.example.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {
    private final ProducerService service;

    @PostMapping("/publish")
    public String publish(
        @RequestParam("message")
        String message
    ){
        service.send(message);
        return "published: " + message;
    }

    @PostMapping("/publish-json")
    public String publishJson(
            @RequestBody
            PayloadDto dto
    ) {
        service.sendDto(dto);
        return "published dto: " + dto;
    }
}
