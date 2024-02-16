package com.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaErrorHandler implements CommonErrorHandler {

    @Override
    public void handleOtherException(
            Exception exception,
            Consumer<?,?> consumer,
            MessageListenerContainer container,
            boolean batchListener
    ){
        log.error("Exception: {}" , exception.getMessage());
        log.warn("Consumer: {}", consumer);
        log.warn("Container: {}" , container);
        if (exception instanceof RecordDeserializationException ex){
            consumer.seek(ex.topicPartition(), ex.offset() + 1L);
            consumer.commitSync();
        }
    }
}
