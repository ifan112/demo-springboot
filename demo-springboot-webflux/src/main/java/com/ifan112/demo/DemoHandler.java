package com.ifan112.demo;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Component
public class DemoHandler {

    public Mono<ServerResponse> time(ServerRequest request) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just("Time: " + timeFormat.format(new Date())), String.class);
    }

    /**
     * 基于HTTP协议，内容类型是：text/event-stream，所有现代浏览器都支持。
     * 通常称之为SSE，即Server-Sent Event。
     * <p>
     * 与SSE不同，WebSocket是一个全新的应用层协议，只不过它复用了之前创建
     * HTTP连接时的三次TCP握手，然后发送一个升级为WebSocket协议的HTTP请求。
     * 此后，客户端和服务器端便以WebSocket协议进行通讯。
     * <p>
     * <p>
     * <p>
     * 参考：https://www.ruanyifeng.com/blog/2017/05/server-sent_events.html
     *
     * @param request HTTP请求
     * @return 响应
     */
    public Mono<ServerResponse> times(ServerRequest request) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(Flux.interval(Duration.ofSeconds(1)).map(l -> timeFormat.format(new Date())), String.class);
    }

    public Mono<ServerResponse> date(ServerRequest request) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just("Date: " + dateFormat.format(new Date())), String.class);
    }

}
