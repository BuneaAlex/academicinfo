package com.dolcevita.academicinfo.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServerProperties {
    @Value("${server.address}")
    private String serverIp;

    @Value("${server.port}")
    private int serverPort;
}
