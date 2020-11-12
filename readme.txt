使用docker运行项目：
使用maven的package命令打包，在target目录下生成jar文件
生成镜像文件
docker build -t ethanpetstore:lasted .
启动服务
docker-compose up -d
访问路径：localhost:3000/EthanPetStore/index/homePage