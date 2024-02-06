package com.mingink.system;

import com.mingink.common.core.annotaion.EnableCustomConfig;
import com.mingink.common.core.annotaion.EnableMIFeignClients;
import com.mingink.common.swagger.annotaion.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: ZenSheep
 * @Date: 2024/1/30 21:09
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableMIFeignClients
@SpringBootApplication
public class MingInkSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(MingInkSystemApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  MingInk-System系统模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "  __  __   _                   ___           _    \n" +
                " |  \\/  | (_)  _ __     __ _  |_ _|  _ __   | | __\n" +
                " | |\\/| | | | | '_ \\   / _` |  | |  | '_ \\  | |/ /\n" +
                " | |  | | | | | | | | | (_| |  | |  | | | | |   < \n" +
                " |_|  |_| |_| |_| |_|  \\__, | |___| |_| |_| |_|\\_\\\n" +
                "                       |___/                      ");
    }
}