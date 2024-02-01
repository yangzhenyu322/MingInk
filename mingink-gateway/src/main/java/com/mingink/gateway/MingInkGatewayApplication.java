package com.mingink.gateway;

import com.mingink.common.core.annotaion.EnableMIFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author: ZenSheep
 * @Date: 2024/2/1 0:22
 */
@EnableAsync
@EnableMIFeignClients
@SpringBootApplication
public class MingInkGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MingInkGatewayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Gateway网关模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "  __  __   _                   ___           _    \n" +
                " |  \\/  | (_)  _ __     __ _  |_ _|  _ __   | | __\n" +
                " | |\\/| | | | | '_ \\   / _` |  | |  | '_ \\  | |/ /\n" +
                " | |  | | | | | | | | | (_| |  | |  | | | | |   < \n" +
                " |_|  |_| |_| |_| |_|  \\__, | |___| |_| |_| |_|\\_\\\n" +
                "                       |___/                      ");
    }
}