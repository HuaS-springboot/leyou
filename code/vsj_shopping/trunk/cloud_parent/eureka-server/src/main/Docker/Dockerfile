FROM java:8
VOLUME /tmp
ADD  *.jar eureka-server.jar
RUN bash -c 'touch /eureka-server.jar'
#对应的是你yml中的端口号，最好保持一致
EXPOSE 8761
#最后的/eureka-server.jar一定要对应ADD中最后的名称
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom" , "-jar", "/eureka-server.jar" ]