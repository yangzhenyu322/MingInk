# 用于 JenKins 完成项目打包构建和远程文件传输后执行的 Shell 脚本
# 1、停止并删除容器、网络和卷,并且删除该 Docker Compose 文件通过 build 选项构建的本地镜像
# 注意：--rmi 参数只会删除通过 Docker Compose 构建的镜像,它不会删除通过 image 选项指定的镜像(即从 Docker Hub 或其他镜像仓库拉取的镜像)
docker compose -f docker-compose.service.yml down --rmi local

# 2、运行 docker-compose.env.yml 文件
sh env-startup.sh

# 3、运行 docker-compose.service.yml 文件
sh startup.sh

# 4、清除 <none> 的镜像：当 docker build 或者 docker pull 新镜像的时候，如果之前已经存在了，涉及到镜像版本更新的话，那么之前的镜像就变成了临时镜像，也就是 <none>:<none> 镜像
# 删除所有未使用的镜像, -f 表示不需要确认
docker image prune -f
# 自动构建触发测试1