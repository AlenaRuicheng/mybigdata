## 项目描述
　　项目前端使用Spring MVC架构来展现，以及使用Spring与Hibernate注解模式进行编程，以此达到快速完成复杂业务流程的编程实现目的。本项目中通过Apache Bench压力测试工具访问或用户访问编写的网页，每次访问网页都将产生一条访问记录，这些访问日志记录被存储在Nginx的日志目录下，然后我们通过编写shell脚本以及crontab任务定期地将产生的日志移动到Flume指定的spooldir下，这样可以避免因日志记录不断增加造成日志文件过大的情况。接下来我们用Flume来收集日志并将日志传送给Kafka的生产者。在本项目中定义了两个Kafka消费者，其中一个消费者直接将访问日志写入HDFS中作为备份，另一个消费者先对日志记录进行数据清洗，过滤掉非法数据，之后把清洗过后的数据交给Hive处理。然后通过编写shell脚本和crontab任务定期地建立Hive分区表，以此避免数据在写入Hive的时候缺少相应的分区表而导致无法写入。最后使用HBaseStorageHandler将Hive与HBase关联起来，这样便可以将底层数据存储到HBase中，加快数据查找速度。<br>

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
