## 项目概述

　　项目从前端给予Spring MVC架构的前端展现，以及Spring与Hibernate在Servlet3.0规范下的纯注解编程体验，迅捷完成复杂业务流程的编程实现处理。用户访问编写的网页，产生访问日志记录在Nginx的日志目录下，由Flume收集日志并将日志输送到Kafka中。项目中自定义两个Kafka消费者，一个直接将收到的日志写入HDFS中(原生数据)作为备份，另一个消费者对日志记录进行数据清洗，过滤掉不需要的字段，然后把清洗后的数据交由Hive管理。Hive通过HBaseStorageHandler与HBase关联起来，这样底层数据将存储到HBase中，数据查找会更快速。<br>
项目通篇采用maven实现项目依赖管理，更有三方插件在应用过程中的使用便捷扩展与使用。
## 项目支撑
1.项目初始化<br>
  基于servler3.0 的web层框架准备，springmvc+spring+hibernate的基础类库   
  实现。<br>
2.nginx服务器反向代理配置<br>
  nginx + tomcat实现动静资源隔离。
  nginx实现日志滚动。
  使用Apache Bench进行压力测试。<br>
3.部署flume和kafka集群<br>
  配置hive数据仓库，使用linux调度方式周期性生成分区表。
  利用spooldir source提取nginx滚动生成的日志文件到kafka集群。
  实现kafka消费者，数据分成两部分处理，一部分作为原生数据直接sink到hdfs，作为备份。
  另一部分进行数据清洗，并将清洗后的数据写入到hive数据库的分区表中。<br>
4.
  配置linux计划任务，周期性调用hive脚本，对上一天的日志信息进行kpi统计，统计结果进入hbase映射表中。
  集合web前端部分，对hbase库中数据进行展现和可视化处理。
```Java
public static void main(String[] args){
    System.out.println("hhhhh");
}
```
![image](https://github.com/AlenaRuicheng/mybigdata/blob/master/elements/mybigdata-outline.jpg)
