package com.learnavro.producer;

import com.learnavro.domain.generated.CoffeeOrder;
import com.learnavro.domain.generated.CoffeeUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class CoffeeOrderUpdateProducer {
    KafkaTemplate<String, CoffeeUpdateEvent> kafkaTemplate;

    public CoffeeOrderUpdateProducer(KafkaTemplate<String, CoffeeUpdateEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUpdateMessage(String orderId, CoffeeUpdateEvent coffeeOrderUpdateAvro) {
        var producerRecord = new ProducerRecord<>("coffee-orders", orderId, coffeeOrderUpdateAvro);


        ListenableFuture<SendResult<String, CoffeeUpdateEvent>> listenableFuture = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, CoffeeUpdateEvent>>() {

            @Override
            public void onFailure(Throwable ex) {
                handleFailure(coffeeOrderUpdateAvro, ex);
            }

            @Override
            public void onSuccess(SendResult<String, CoffeeUpdateEvent> result) {
                handleSuccess(coffeeOrderUpdateAvro, result);
            }
        });

    }

    private void handleFailure(CoffeeUpdateEvent coffeeOrder, Throwable ex) {
        log.error("Error Sending the Message for {} and the exception is {}", coffeeOrder, ex.getMessage(), ex);
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }

    }

    private void handleSuccess(CoffeeUpdateEvent coffeeOrder, SendResult<String, CoffeeUpdateEvent> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", coffeeOrder.getId(), coffeeOrder, result.getRecordMetadata().partition());
    }
}
