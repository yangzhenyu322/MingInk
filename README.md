# MingInk
荧墨轩

## 平台简介
MingInk是一个同人小说平台，为创作者提供了分享、创作和阅读同人小说的平台。在这里，作者可以创作属于自己的故事，重新构想和发展已有的角色和情节，与读者互动并分享独特的创意世界。读者则能够在平台上找到各种各样风格的同人小说，探索作者的独特视角和故事发展，形成一个充满创意和热情的文学社区。

## 总体架构图
<img src="/assets/1.png" alt="架构图" title="架构图" style="zoom: 25%;" />

## 技术选型
开发框架：SpringBoot、SpringCloud、OpenFeign  
缓存：Redis分布式缓存 + 本地缓存  
数据库：MySQL、MongoDB  
网关：Gateway + Sentinel  
分布式事务：Seata、事务消息  
持久层：MyBatis、MyBatis-Plus  
注册中心：Nacos  
消息队列：RocketMQ、Kafka  
网络通信：Netty，WebSocket  
文件存储：MinIO、OSS  
日志可视化治理：ELK  
监控：Prometheus，Grafana  
单元测试：Junit  
压力测试：JMeter  



## 系统模块
```
com.mingink
|--mingink-gateway        // 网关模块【8080】
|--mingink-api            // 接口模块
|           └──mingink-api-system               // 系统接口
|--mingink-common         // 通用模块
|           └──mingink-common-core              // 系统接口
|--mingink-modules        // 业务模块
|           └──mingink-system                   // 系统服务模块【8081】
|--pom.xml                                      // 公共依赖

```



## 内置功能
- [ ] 登录注册
- [ ] 首页推送
- [ ] 投稿功能
- [ ] 阅读界面
- [ ] 个人主页
- [ ] 菜单设置
- [ ] 用户经验等级虚拟钱币
- [ ] 会员制度
- [ ] 创作激励
- [ ] 帮助与客服