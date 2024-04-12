#java 版本
FROM openjdk:8-jre
#挂载的docker卷
VOLUME /tmp
#前者是要操作的jar包  后者自定义jar包名
ADD *.jar sentinel-dashboard.jar
#定义时区参数
ENV TZ=Asia/Shanghai
#设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone
#配置启动命令,-D表示设置JVM参数,server后写所在服务器地址
ENTRYPOINT ["java","-jar","-Dserver.port=8858", "-Dcsp.sentinel.dashboard.server=localhost:8858", "-Dproject.name=sentinel-dashboard", "-Dnacos.addr=223.82.75.76:8848", "-Dnacos.username=lovelumine", "-Dnacos.password=lovelumine47", "-Dnacos.namespace=4850addc-6018-4aea-b1aa-f5cb3f6e695d", "sentinel-dashboard.jar"]