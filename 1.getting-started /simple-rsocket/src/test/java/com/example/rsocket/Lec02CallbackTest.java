package com.example.rsocket;

import com.example.rsocket.client.CallbackService;
import com.example.rsocket.dto.RequestDto;
import com.example.rsocket.util.ObjectUtil;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec02CallbackTest {
    private RSocket rSocket;

    @BeforeAll
    public void setup(){
        this.rSocket = RSocketConnector.create()
                .acceptor(SocketAcceptor.with(new CallbackService()))
                .connect(TcpClientTransport.create("localhost", 6565))
                .block();
    }

    @Test
    public void callback() throws InterruptedException {
        RequestDto requestDto = new RequestDto(5);
        Mono<Void> voidMono = this.rSocket.fireAndForget(ObjectUtil.toPayload(requestDto));

        StepVerifier.create(voidMono)
                .verifyComplete();
        System.out.println("Going to wait ..");

        Thread.sleep(12000);
    }
}
