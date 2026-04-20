docker build
sudo docker buildx build --no-cache --build-arg BASE_IMAGE=docker.1ms.run/library/eclipse-temurin:8u462-b08-jre-noble  --platform linux/arm64 -t wangchi  --load .

sudo docker save -o wangchi.tar wangchi:latest

后端密码 wangchi@by123