package com.example.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerService {
    private static final String TOPIC = "topic";

    private final KafkaTemplate<String ,String > stringKafkaTemplate;
    private final KafkaTemplate<String , PayloadDto> payloadDtoKafkaTemplate;

    public void sendDto(PayloadDto dto){
        payloadDtoKafkaTemplate.send(TOPIC, dto);
    }

    public void send(String message){
//        stringKafkaTemplate.send(TOPIC,0,"key",message);
        stringKafkaTemplate.send(TOPIC, message);
    }

    public void sendWithCallback(String message){
        CompletableFuture<SendResult<String, String>> sendResultFuture
                = stringKafkaTemplate.send(TOPIC, message);
        sendResultFuture.whenComplete((sendResult, throwable) -> {
            log.info("send(). whenComplete()");
            log.info(String.valueOf(sendResult));
            log.info(String.valueOf(throwable));
        });
        log.info("end sendWithCallback()"); // 제일 먼저 로그 찍힘
    }

    public void sendResultSync(String message){
        CompletableFuture<SendResult<String ,String >> sendResultFuture
                = stringKafkaTemplate.send(TOPIC, message);
        try {
            SendResult<String ,String > sendResult = sendResultFuture.get();
        } catch (InterruptedException | ExecutionException ignored) {}
    }
}
