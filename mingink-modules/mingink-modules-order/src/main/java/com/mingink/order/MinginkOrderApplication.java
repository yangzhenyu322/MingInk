package com.mingink.order;

import com.mingink.common.core.annotation.EnableCustomConfig;
import com.mingink.common.core.annotation.EnableMIFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/21 11:59
 */
@EnableCustomConfig
@EnableMIFeignClients
@SpringBootApplication
public class MinginkOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinginkOrderApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  MingInk-Order订单模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "  __  __   _                   ___           _    \n" +
                " |  \\/  | (_)  _ __     __ _  |_ _|  _ __   | | __\n" +
                " | |\\/| | | | | '_ \\   / _` |  | |  | '_ \\  | |/ /\n" +
                " | |  | | | | | | | | | (_| |  | |  | | | | |   < \n" +
                " |_|  |_| |_| |_| |_|  \\__, | |___| |_| |_| |_|\\_\\\n" +
                "                       |___/                      ");
    }
}
