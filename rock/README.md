# rock技术架构

## 错误码范围
2000 以下

## 模块介绍

## 基础模块(rock-base)

### 集成功能：

1. 全局异常处理
2. Ribbon跨服务
3. 基础日志打印AOP
4. 常用帮助类
5. 全局异常定义
6. Feign远程调用配
7. Fastjson格式规范
8. 一些基础对象等等

### 使用方法

项目中pom文件添加依赖

		<dependency>
			<groupId>com.lzb.rock.base</groupId>
			<artifactId>rock-base</artifactId>
		</dependency>
启动类继承BaseApplication，添加注解

```
@EnableAutoConfiguration
@EnableEurekaClient
@EnableScheduling
@ComponentScan(basePackages = { "com.lzb.rock" })
@EnableFeignClients(basePackages = { "com.lzb.rock" })
@Slf4j
```

自定义SWAGGER_2文档方法

重写BaseApplication类中的apis()和apiInfo()方法，详情请看注释

配置文件添加，具体含义请看注释

```
#访问的相对路径
server.context-path=/
#为你的应用起个名字，该名字将注册到eureka注册中心
spring.application.name=rock-admin
##注册地址，多个逗号隔开
eureka.client.serviceUrl.defaultZone=http://tx.liuzhibo.top:12011/eureka
#实例名称显示IP配置如下
eureka.instance.preferIpAddress=true
eureka.instance.instance-id= ${spring.cloud.client.ipAddress}:${server.port}
#清理间隔（单位毫秒，默认是60*1000）
eureka.server.eviction-interval-timer-in-ms=5000
#设为false，关闭自我保护主要）
eureka.server.enable-self-preservation=false
#开启健康检查（需要spring-boot-starter-actuator依赖）
eureka.client.healthcheck.enabled=true
#租期更新时间间隔（默认30秒）单位：秒
eureka.instance.lease-renewal-interval-in-seconds=10
#租期到期时间（默认90秒）不怕出错的话，时间可以更短，单位：秒
eureka.instance.lease-expiration-duration-in-seconds=30
#false为启用jdk默认动态代理,true为cglib动态代理
spring.aop.proxy-target-class=true
#指定日期显示格式和时区
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=GMT+8
```

## DB连接模块(rock-mybatis)

### 集成功能

1. 集成mybati plus 分页插件

2. 基于Druid多数据源切换

   

### 使用方法

项目中pom文件添加依赖

		<dependency>
			<groupId>com.lzb.rock.base</groupId>
			<artifactId>rock-base</artifactId>
		</dependency>
		<dependency>
			<groupId>com.lzb.rock.mybatis</groupId>
			<artifactId>rock-mybatis</artifactId>
		</dependency>
配置文件添加，具体含义请看注释

```
##mybatis-plus配置
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.datasource.filters_sys=log4j,wall,mergeStat
#xml路径 com.ouyi.store.ms.xml
mybatis-plus.mapper-locations=classpath:com/lzb/**/*.xml
#实体扫描，多个package用逗号或者分号分隔
mybatis-plus.typeAliasesPackage=com.lzb.**.model
#0:数据库ID自增1:用户输入id2:全局唯一id(IdWorker)3:全局唯一ID(uuid)
mybatis-plus.global-config.id-type=0
mybatis-plus.global-config.db-column-underline=false
mybatis-plus.global-config.refresh-mapper=true
mybatis-plus.global-config.logic-delete-value=0
mybatis-plus.global-config.logic-not-delete-value=1
mybatis-plus.global-config.sql-injector=com.baomidou.mybatisplus.mapper.LogicSqlInjector
#带有下划线的表字段映射为驼峰格式的实体类属性
mybatis-plus.global-config.sql-injector=com.baomidou.mybatisplus.mapper.LogicSqlInjector
#逻辑删除配置
mybatis-plus.global-config.logic-delete-value=1
mybatis-plus.global-config.logic-not-delete-value=0
#驼峰规则
mybatis-plus.configuration.map-underscore-to-camel-case=false
#配置的缓存的全局开关
mybatis-plus.configuration.cache-enabled=true
#延时加载的开关
mybatis-plus.configuration.lazyLoadingEnabled=true
#开启的话，延时加载一个属性时会加载该对象全部属性，否则按需加载属性
mybatis-plus.configuration.multipleResultSetsEnabled=true
#打印sql语句,调试用
#mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

开启多数据源切换

rock.properties 配置文件添加

```
＃开启多数据源
rock.mutiDatasourceOnOff=true
#默认数据源名称
rock.defaultDatasource=sys
```

添加配置文件muti-datasource.properties，内容如下“_”后面接的就是自定义数据源名称，可配置无数个

```
spring.datasource.url_sys=
spring.datasource.username_sys=
spring.datasource.password_sys=
spring.datasource.filters_sys=log4j,wall,mergeStat
```

数据源切换方法调用：DataSourceContextHolder 类方法即可