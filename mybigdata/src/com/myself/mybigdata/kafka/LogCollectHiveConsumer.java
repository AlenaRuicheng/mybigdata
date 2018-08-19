package com.myself.mybigdata.kafka;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.core.io.ClassPathResource;

import com.myself.mybigdata.util.LogUtil;
import com.myself.mybigdata.util.Util;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

/**
 * Kafka消费者。把清洗过后的日志数据存入Hive分区表中
 */
public class LogCollectHiveConsumer {
	public static void main(String[] args) throws IOException {
		Properties properties = new Properties();
		properties.load(new ClassPathResource("zookeeper-properties").getInputStream());
		properties.setProperty("group.id", "g2");

		ConsumerConfig config = new ConsumerConfig(properties);
		ConsumerConnector connector = Consumer.createJavaConsumerConnector(config);
		Map<String, Integer> topicCount = new HashMap<>();
		topicCount.put("mybigdata", 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> map = connector.createMessageStreams(topicCount);
		List<KafkaStream<byte[], byte[]>> list = map.get("mybigdata");
		for (KafkaStream<byte[], byte[]> ks : list) {
			ConsumerIterator<byte[], byte[]> it = ks.iterator();
			while (it.hasNext()) {
				String message = new String(it.next().message());
				if(!"".equals(message)) {
					String ip = message.split(",")[0];
					// Hive中的分区表在HDFS中的路径
					String path = "/user/hive/warehouse/logs.db/request_info/year=" + Util.getYear(message) +"/month=" + Util.getMonth(message) +"/day=" + Util.getDay(message) + "/" + ip + ".log";
					// 格式化日志信息
					String logLine = LogUtil.cleanLog(message);
					String element = logLine.split(",")[2].split(" ")[1];
					if(element.endsWith(".html") || element.endsWith(".htm")) {
						System.out.println(logLine);
						HdfsOutputStreamPool.writeLogToHdfs(path, logLine);
					}
				}
			}
		}
		connector.shutdown();
	}
	
//	public static void writeLog(String path, String line) {
//		try {
//			HdfsOutputStreamPool.writeLogToHdfs(path, line);
//			FSDataOutputStream dataOutputStream = fileSystem.append(new Path(path));
//			dataOutputStream.write((line + "\r\n").getBytes());
//			dataOutputStream.hsync();
//			dataOutputStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	} 
	
}
