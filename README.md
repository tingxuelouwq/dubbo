## 使用dubbo改造传统工程
- common-parent: maven父配置
- facade-user: 用户服务接口
- service-user: 用户服务实现
- web-user: 服务消费者

## 做成分布式服务架构的项目特点：
1. 多个服务
2. 多种类型的工程
3. 工程间需要相互调用
4. 如何实现工程间解耦？（高内聚、低耦合）
5. 工程该怎么拆分
6. 如何对大量的工程进行合理有效管理（持续集成） 

## 使用dubbo进行规模服务化
- common: 公共工程
- common-config: 公共配置工程
- common-core: 公共core工程
- common-web: 公共web工程