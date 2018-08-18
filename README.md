# 项目概述
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
![image](https://github.com/AlenaRuicheng/mybigdata/blob/master/elements/mybigdata-outline.jpg)
