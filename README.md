
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


方便二次开发

实现大多数已知的IM功能性，只需按需求裁剪im-business模块功能 即可完成开发


模块划分

- im-common 通用模块存放工具类 实体类
- im-gateway TCP接入层跟业务层分离 提高稳定性与扩展性 可无限扩展
- im-business 业务层实现具体业务 可无限扩展                            


目前功能开发中ing  有兴趣的可以加入一起开发

已实现gateway全部功能

- 通信协议
- 心跳机制
- 消息重发
- 登录指令
- ACK机制
- 集群消息转发


已实现业务功能 （单聊功能已全部实现）

- 正在输入中提示
- 发送消息
- 消息撤回
- 消息已读
- 消息删除
- 会话列表
- 消息记录
- 离线指令
- 清空未读




TODO

群消息
数据分库分表
服务化实现





 







