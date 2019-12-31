# 用户服务

## application-dev配置文件示例：
```yaml
spring:
  datasource:  
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/sdcommunity_user?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true
    username: root
    password: root
  redis:
    host: 127.0.0.1 # your redis host
    port: 6479
  rabbitmq:
    host: 127.0.0.1 # your mq host
    port: 5772
    # username and password
    username: guest
    password: guest
```
