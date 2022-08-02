package com.example.rsocket.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

// RSocket is a full duplex protocol where a client and server are identical in terms of both having
// the capability to initiate requests to their peer. This interface provides the contract where a
// client or server handles the setup for a new connection and creates a responder RSocket for accepting requests from the remote peer.
public class SocketAcceptorImpl implements SocketAcceptor {
    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
        System.out.println("SocketAcceptorImpl - accept method");

        if(isValidClient(setup.getDataUtf8()))
            return Mono.just(new MathService());
        else
            return Mono.just(new FreeService());

        //return Mono.fromCallable(MathService::new);
        //return Mono.fromCallable(() -> new BatchJobService(sendingSocket));
        //return Mono.fromCallable(FastProducerService::new);
    }

    private boolean isValidClient(String credentials){
        return "user:password".equals(credentials);
    }
}
