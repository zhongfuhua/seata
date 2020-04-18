FROM registry-intl.ap-southeast-1.aliyuncs.com/gigrt-arch/base_jre_cloud:8u212

ADD /seata-server-springboot-0.8.1-SNAPSHOT.jar /home/admin
ADD /logback.xml /home/admin

# 设置额外参数
ENV EXTRA_JVM_ARGUMENTS="-XX:MaxDirectMemorySize=1024M"

RUN echo '$JAVA_HOME/bin/java -jar $CATALINA_OPTS -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom -Dcatalina.logs=$CATALINA_HOME/logs -Dpandora.location=$CATALINA_HOME/deploy/taobao-hsf.sar  "/home/admin/seata-server-springboot-0.8.1-SNAPSHOT.jar"'   --logging.config=/home/admin/logback.xml > /home/admin/start.sh && chmod +x /home/admin/start.sh
WORKDIR $CATALINA_HOME

CMD ["/bin/bash", "/home/admin/start.sh"]
EXPOSE 8091