package com.example.rsocket.client;


import com.example.rsocket.dto.ResponseDto;
import com.example.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;

public class CallbackService implements RSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        System.out.println("Client received :"+ ObjectUtil.toObject(payload, ResponseDto.class));
        return Mono.empty();
    }
}
