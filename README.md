
### Jbase-IM

基于Netty的千万级高可用分布式IM实现


技术栈

- Spring Cloud Alibaba
- Netty
- Kafka
- Redis
- Mybatis-Plus
- Sharding-JDBC


功能性需求

![image](https://raw.githubusercontent.com/jayqqaa12/jbase-im/master/doc/1.png)

非功能性需求

![image](https://raw.githubusercontent.com/jayqqaa12/jbase-im/master/doc/2.png)



模块划分

- im-common 通用模块存放工具类 实体类
- im-gateway TCP接入层跟业务层分离 提高稳定性与扩展性
- im-business 业务层实现具体业务



目前功能开发中ing 有兴趣的可以加入一起开发

已实现功能

- 基本通信框架搭建
- 通信协议
- 心跳机制
- 消息重发
- 登录指令
- ACK机制
- 集群消息转发

正在开发

- 单聊







