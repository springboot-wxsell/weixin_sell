package com.sell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
/**
 * @author WangWei
 * @Title: WebSocketConfig
 * @ProjectName weixin_sell
 * @date 2018/11/28 20:56
 * @description
 */
@Component
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
