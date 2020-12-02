#  基础镜像
FROM java:openjdk-8-jre-alpine
#Default to UTF-8 file.encoding
ENV LANG C.UTF-8
#添加应用
ADD pre-system-1.4.jar pre-system-1.4.jar
#参数
#ENV PARAMS=""
#执行操作 默认启动线上环境
ENTRYPOINT [ "sh", "-c", "java -Xmx50m -Djava.security.egd=file:/dev/./urandom -jar pre-system-1.4.jar --spring.profiles.active=prod" ]

