## 项目描述
　　项目从前端给予Spring MVC架构的前端展现，以及Spring与Hibernate在Servlet3.0规范下的纯注解编程体验，迅捷完成复杂业务流程的编程实现处理。通过用户访问或Apache Bench压力测试软件访问编写的网页，产生访问日志记录在Nginx的日志目录下，通过shell脚本和crontab任务定期地将Nginx日志移动到Flume指定的spooldir下，由Flume收集日志并将日志输送到Kafka中。项目中自定义两个Kafka消费者，一个直接将收到的日志写入HDFS中(原生数据)作为备份，另一个消费者对日志记录进行数据清洗，过滤掉不需要的字段，然后把清洗后的数据交由Hive管理。通过编写shell脚本和crontab定时任务来定期创建Hive分区表，避免数据在写入Hive时没有对应的分区表而致使写入失败。Hive通过HBaseStorageHandler与HBase关联起来，这样底层数据将存储到HBase中，数据查找会更快速。<br>
　　项目通篇采用Maven实现项目依赖管理，从项目运维角度上涉及的Nginx服务器的负载均衡实现与动静分离技术中同Tomcat服务器的服务器集群整合。
## 项目支撑概述
### 项目初始化
　　基于Servler3.0 的web层框架准备，Spring MVC + Spring + Hibernate的基础类库实现。
### Nginx服务器反向代理配置
　　nginx + tomcat实现动静资源隔离。
　　nginx实现日志滚动。
　　使用Apache Bench进行压力测试。<br>
### 部署flume和kafka集群<br>
　　配置hive数据仓库，使用linux调度方式周期性生成分区表。
　　利用spooldir source提取nginx滚动生成的日志文件到kafka集群。
　　实现kafka消费者，数据分成两部分处理，一部分作为原生数据直接sink到hdfs，作为备份。
　　另一部分进行数据清洗，并将清洗后的数据写入到hive数据库的分区表中。<br>
### AAA
　　配置linux计划任务，周期性调用hive脚本，对上一天的日志信息进行kpi统计，统计结果进入hbase映射表中。
　　集合web前端部分，对hbase库中数据进行展现和可视化处理。
```Java
public static void main(String[] args){
    System.out.println("hhhhh");
}
```
![image](https://github.com/AlenaRuicheng/mybigdata/blob/master/elements/mybigdata-outline.jpg)
