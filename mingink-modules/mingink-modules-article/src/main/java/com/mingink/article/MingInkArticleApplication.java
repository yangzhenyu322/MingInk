package com.mingink.article;

import com.mingink.common.core.annotation.EnableCustomConfig;
import com.mingink.common.core.annotation.EnableMIFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: ZenSheep
 * @Date: 2024/1/30 21:09
 */
@EnableCustomConfig
@EnableMIFeignClients
@SpringBootApplication
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