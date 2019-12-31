# 文章模块

## application-dev配置文件示例：
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/sdcommunity_article?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true
    username: root
    password: root
  redis:
    host: 127.0.0.1
    port: 6479
  data:
    mongodb:
      host: 127.0.0.1
      port: 27017
      database: recruitdb
```
