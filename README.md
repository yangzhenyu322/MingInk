# MingInk
荧墨轩

## 平台简介
MingInk是一个同人小说平台，为创作者提供了分享、创作和阅读同人小说的平台。在这里，作者可以创作属于自己的故事，重新构想和发展已有的角色和情节，与读者互动并分享独特的创意世界。读者则能够在平台上找到各种各样风格的同人小说，探索作者的独特视角和故事发展，形成一个充满创意和热情的文学社区。

## 总体架构图
<img src="/assets/1.png" alt="架构图" title="架构图" style="zoom: 25%;" />

## 技术选型
开发框架：SpringBoot、SpringCloud

注册中心：Nacos

远程调用: OpenFeign

缓存：Redis分布式缓存 + 本地缓存  

消息队列：RabbitMQ

数据库：MySQL 、MyBatis-Plus

网关：Gateway

安全认证: Spring Security、JWT、Oauth2 

分布式事务：Seata、事务消息 

文件存储：MinIO

日志可视化治理：ELK 

监控：Prometheus，Grafana  

单元测试：Junit  

压力测试：JMeter


## 系统模块
```
com.mingink
|--env					  // 中间件环境配置
|--logs                   // 日志
|--mingink-api            // 接口模块
|           └──mingink-api-system               // 系统接口
|           └──mingink-api-article              // 小说接口
|--mingink-common         // 通用模块
|           └──mingink-common-core              // 核心模块
|           └──mingink-common-mq	            // 消息队列模块
|           └──mingink-common-oss	            // 文件存储模块
|           └──mingink-common-redis	            // 缓存模块
|           └──mingink-common-seata	            // 分布式事务模块
|           └──mingink-common-swagger	        // 在线开发文档模块
|--mingink-gateway        // 网关模块【8081】
|--mingink-modules        // 业务模块
|           └──mingink-system                   // 系统服务模块【8082】
|           └──mingink-article                  // 小说服务模块【8083】
|           └──mingink-order                    // 订单服务模块【8084】
|--docker-compose.env.yml                       // docker-compose环境部署文件
|--docker-compose.service.yml					// docker-compose服务部署文件
|--pom.xml                                      // 公共依赖
```



## 内置功能
- [x] 登录注册
- [x] 首页推送
- [x] 投稿功能
- [x] 阅读界面
- [x] 个人主页
- [x] 菜单设置
- [ ] 用户经验等级虚拟钱币
- [x] 会员制度
- [ ] 创作激励
- [ ] 帮助与客服