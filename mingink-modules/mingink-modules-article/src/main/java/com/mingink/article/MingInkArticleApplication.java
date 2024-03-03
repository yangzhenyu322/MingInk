package com.mingink.article;

import com.mingink.common.core.annotaion.EnableCustomConfig;
import com.mingink.common.core.annotaion.EnableMIFeignClients;
import com.mingink.common.swagger.annotaion.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: ZenSheep
 * @Date: 2024/1/30 21:09
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableMIFeignClients
@SpringBootApplication
@ComponentScan("com.mingink")
public class MingInkArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(MingInkArticleApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  MingInk-Article小说模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "  __  __   _                   ___           _    \n" +
                " |  \\/  | (_)  _ __     __ _  |_ _|  _ __   | | __\n" +
                " | |\\/| | | | | '_ \\   / _` |  | |  | '_ \\  | |/ /\n" +
                " | |  | | | | | | | | | (_| |  | |  | | | | |   < \n" +
                " |_|  |_| |_| |_| |_|  \\__, | |___| |_| |_| |_|\\_\\\n" +
                "                       |___/                      ");
    }
}