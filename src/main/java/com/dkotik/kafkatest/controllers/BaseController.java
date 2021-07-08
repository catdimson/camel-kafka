package com.dkotik.kafkatest.controllers;

import com.dkotik.kafkatest.services.kafka.ProducerService;
import com.dkotik.kafkatest.models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class BaseController {

    private ProducerService producerService;

    @Autowired
    public BaseController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/generate")
    public String generate(@RequestParam String message, @RequestParam Integer age) {
        producerService.sendMessage(new Message(message, age));
        return "OK";
    }

//    @PostMapping("/generate")
//    public String reSend(@RequestParam String message, @RequestParam Integer age) {
//        producerService.sendMessage(new Message(message, age));
//        return "OK";
//    }

    @PostMapping("/generate")
    public ResponseEntity<Void> sendMail(@RequestBody String rawBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(rawBody);
        System.out.println(body);
        producerService.sendMessage(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
