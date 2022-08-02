package com.example.rsocket.service;

import com.example.rsocket.dto.ChartResponseDto;
import com.example.rsocket.dto.RequestDto;
import com.example.rsocket.dto.ResponseDto;
import com.example.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class MathService implements RSocket {
    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        System.out.println("Receiving :"+ ObjectUtil.toObject(payload, RequestDto.class));
        return Mono.empty();
    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        return Mono.fromSupplier(() -> {
            RequestDto requestDto = ObjectUtil.toObject(payload, RequestDto.class);
            int input = requestDto.getInput();
            ResponseDto responseDto = new ResponseDto(input, input * input);
            return ObjectUtil.toPayload(responseDto);
        });
    }

    @Override
    public Flux<Payload> requestStream(Payload payload) {
        RequestDto requestDto = ObjectUtil.toObject(payload, RequestDto.class);
        return Flux.range(1,10)
                .map(i -> i * requestDto.getInput())
                .map(i -> new ResponseDto(requestDto.getInput(), i))
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(System.out::println)
                .doFinally(System.out::println)
                .map(ObjectUtil::toPayload);
    }

    @Override
    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
        return Flux.from(payloads)
                .map(p -> ObjectUtil.toObject(p, RequestDto.class))
                .map(RequestDto::getInput)
                .map(i -> new ChartResponseDto(i, (i * i) + 1))
                .map(ObjectUtil::toPayload);
    }


}
