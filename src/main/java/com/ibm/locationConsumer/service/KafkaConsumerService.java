package com.ibm.locationConsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.locationConsumer.handler.ConsumerHandler;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class KafkaConsumerService {

    private final ConsumerHandler consumerHandler;
    private final ObjectMapper objectMapper = new ObjectMapper();


    private  Boolean validJson;


//@KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.groupId}" ,properties = {"spring.json.value.default.type=com.ibm.locationConsumer.dto.Location"})
@KafkaListener(topics = "kosuru.locationref.topic.internal.any.v1", groupId = "ConsumerGroup1" ,properties = {"spring.json.value.default.type=com.ibm.locationConsumer.dto.Location"})
    public void consume(String message, ConsumerRecord<String,String> record ) throws JsonProcessingException {
        System.out.println(message);
        //String payLoad = message.replaceAll("^\"|\"$","").trim();
        String jsonData = message.replaceAll("^\"|\"$|\\\\","").trim();
        String metadata = "Key: " + record.key() + "," + "Value: " + record.value() + "," + "Topic: " + record.topic() + "," +
                "Partition: " + record.partition() + "," + "Offset: " + record.offset();
         validateJson(jsonData, metadata)
                .doOnSuccess(validJson -> {
                    if(validJson){
                    log.info("Json payload is valid");
                         Mono.fromCallable(() -> consumerHandler.createLoc(jsonData))
                                .doOnError(error -> log.error("Error updating location", error))
                                .subscribe();
                    }
                    //return Mono.empty();
                })
                .doOnError(error -> {
                    log.error("Json payload is not valid");
                }).subscribe();

    }

    public  Mono<Boolean> validateJson(String payload, String metadata) {

        Mono<Boolean> validJsonBoolean =  Mono.fromCallable(() -> objectMapper.readTree(payload))
                .flatMap(data -> {
                    // Check if "geoType" and "name" are not empty
                    if (!data.has("geoType") || !data.has("name") ||
                            data.get("geoType").asText().isEmpty() || data.get("name").asText().isEmpty()) {
                        errorLog("Invalid field data. Any of geoType/name/codeType fields is/are null.",metadata,false);
                        return Mono.error(new IllegalArgumentException("geoType/name/codeType should not be empty"));
                    }
                    else if (!data.has("status") || !"Active".equals(data.get("status").asText())) {   // Check if "status" is "Active"
                        errorLog("Invalid status: " + data.path("status").asText("null"),metadata, false);
                        return Mono.error(new IllegalArgumentException("Invalid status"));
                    }
                   // return Mono.empty().then();
                    return Mono.just(true);
                })
                .onErrorResume(e -> {
                    log.error("Error parsing JSON: " + e.getMessage());
                    return Mono.just(false);
                });
        validJsonBoolean.subscribe();
        return validJsonBoolean;
    }

    private  void errorLog(String errorMessage, String metadata, Boolean validJson) {
        log.error("Error: " + errorMessage);
        log.info("Metadata: " + metadata);
    }

}
