## 项目描述
　　项目从前端给予Spring MVC架构的前端展现，以及Spring与Hibernate在Servlet3.0规范下的纯注解编程体验，迅捷完成复杂业务流程的编程实现处理。通过用户访问或Apache Bench压力测试软件访问编写的网页，产生访问日志记录在Nginx的日志目录下，通过shell脚本和crontab任务定期地将Nginx日志移动到Flume指定的spooldir下，由Flume收集日志并将日志输送到Kafka中。项目中自定义两个Kafka消费者，一个直接将收到的日志写入HDFS中(原生数据)作为备份，另一个消费者对日志记录进行数据清洗，过滤掉不需要的字段，然后把清洗后的数据交由Hive管理。通过编写shell脚本和crontab任务来定期创建Hive分区表，避免数据在写入Hive时没有对应的分区表而致使写入失败。Hive通过HBaseStorageHandler与HBase关联起来，这样底层数据将存储到HBase中，数据查找会更快速。<br>
　　项目通篇采用Maven实现项目依赖管理，项目实现了Nginx服务器的负载均衡与动静分离技术，同Tomcat的服务器集群整合。
## 项目支撑概述
### 项目初始化
　　基于Servler3.0 的web层框架准备，Spring MVC + Spring + Hibernate的基础类库实现
### Nginx服务器反向代理配置
　　Nginx + Tomcat实现动静资源隔离<br>
　　shell + crontab实现日志滚动<br>
　　使用Apache Bench进行压力测试
### 部署flume和kafka集群
　　利用spooldir source提取nginx滚动生成的日志文件到kafka集群<br>
　　实现kafka消费者，数据分成两部分处理，一部分作为原生数据直接sink到HDFS，作为备份<br>
　　另一部分进行数据清洗，并将清洗后的数据写入到hive数据库的分区表中
### 配置HBase
　　部署crontab计划任务，周期性调用Hive脚本，对上一天的日志信息进行pv统计<br>
　　使用HBase存储处理器将数据映射到HBase中，以方便于快速查询统计结果<br>
　　集合web前端部分，对HBase库中数据进行展现和可视化处理<br><br>
## 项目细节展示
![image](https://github.com/AlenaRuicheng/mybigdata/blob/master/elements/mybigdata-outline.jpg)
　　　　　　　　　　　　　　　　　　　　　　　　　图1  项目导图<br>
![image](https://github.com/AlenaRuicheng/mybigdata/blob/master/elements/HDFS%20info.png)
　　　　　　　　　　　　　　　　　　　　　　图2  HDFS集群配置情况<br>
![image](https://github.com/AlenaRuicheng/mybigdata/blob/master/elements/job%20info.png)
　　　　　　　　　　　　　　　　　　　　　　　图3  作业执行中<br>
![image](https://github.com/AlenaRuicheng/mybigdata/blob/master/elements/AppcheBench%20executing.png)
　　　　　　　　　　　　　　　　　　　　　图4  Apache Bench执行中<br>
![image](https://github.com/AlenaRuicheng/mybigdata/blob/master/elements/view-pv.png)
　　　　　　　　　　　　　　　　　　　　　　图5  查看pv统计情况<br>
## 关键脚本展示
<br>shell脚本createHivePartitions.sh——创建Hive分区表
```Bash
#!/bin/sh
#This shell is designed to create tormorrow's partition

#使用source引入环境变量
source /home/centos/.bashrc
y=`date -d "1 day" +%Y`
m=`date -d "1 day" +%m`
d=`date -d "1 day" +%d`
hive -e "alter table logs.request_info add partition(year=${y},month=${m},day=${d})"
hadoop fs -chown -R auas:auas /user/hive/warehouse/logs.db/request_info/year\=${y}
```
<br>shell脚本calculatePv.sh——统计pv值并写入pv表中
```Bash
#!/bin/sh
#This shell script is designed to caculate the page view of the last day

#使用source引入环境变量
source /home/centos/.bashrc
ts=`date +%s`
y=`date -d "-1 day" +%Y`
m=`date -d "-1 day" +%m`
d=`date -d "-1 day" +%d`
#时间戳作为RowKey,在前面加一个随机数使RowKey均匀分布
ts=$((ts%3))$ts
hive -e "insert into logs.pv(rowid,year,month,day,page,amount) select ${ts},year,month,day,time_local,count(*) as amount from logs.request_info where year=${y} and month=${m} and day=${d} group by ${ts},year,month,day,time_local"
```
<br>shell脚本process_logs.sh——将Nginx日志目录下产生的日志文件滚动到spooldir下
```Bash
#!/bin/sh
#设置日期格式
dateformat=`date +%Y-%m-%d-%H-%M`
path=/usr/soft_r/nginx/logs/access_$dateformat.log
#复制文件到指定目录
cp /usr/soft_r/nginx/logs/access.log $path
#计算日志行数
lines=`wc -l < $path`
if [ $lines -eq 0 ];then
 rm -f $path
else
 #获取主机名并将其插入到每行日志记录的起始位置
 host=`hostname`
 sed -i 's/^/'${host}',&/g' $path
 #更改文件所属
 chown centos:centos $path
 #把日志文件移动到spooldir
 mv $path /usr/soft_r/nginx/logs/flume
 #删除access.log文件中的所有行
 sed -i '1,'$lines'd' /usr/soft_r/nginx/logs/access.log
 #重启Nginx服务器
 kill -USR1 `cat /usr/soft_r/nginx/logs/nginx.pid`
fi
```
## 项目总结与收获
　　在项目编写过程中，麻烦最多的就是大数据各种环境搭建了，我觉得要是需要搭建的进群很大的话，我们可以先下载好需要的安装包，然后编写脚本进行批量执行以节约时间成本。还有就是本集群依托虚拟机来实现，电脑配置有点跟不上，导致程序调试变得比较困难。因此每一次在程序运行之前，都要仔细考虑各个参数设置是否正确。通过本次项目实践，我发现之前学到的东西有些生疏，这次实践又使这些旧的知识得到了巩固，还学到了许多新的东西，与此同时也发现自己还有很多不足与改进之处，这些都是促使我不断进步的源泉。
