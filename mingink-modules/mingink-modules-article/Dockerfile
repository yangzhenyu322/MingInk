# 基础镜像
FROM openjdk:17-jdk-alpine

# 指定工作目录
WORKDIR /app

# 将 jar 包添加到工作目录
ADD target/mingink-modules-article.jar .

# 创建日志目录
RUN chmod -R 777 /app && mkdir /app/log

# 设置时区
ENV TimeZone=Asia/Shanghai
# 使用软连接，并且将时区配置覆盖/etc/timezone (同步docker与宿主机的时区)
RUN ln -snf /usr/share/zoneinfo/$TimeZone /etc/localtime && echo $TimeZone > /etc/timezone

# 暴露端口
EXPOSE 8083

# 启动命令
# CMD允许运行时覆盖，而ENTRYPOINT不可以
CMD ["sh", "-c", "java -jar mingink-modules-article.jar >./log/log.out 2>&1"]