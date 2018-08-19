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

import com.myself.mybigdata.util.Util;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

/**
 * Kafka消费者。把原始数据直接存入HDFS
 */
public class LogCollectHdfsConsumer {
	public static Map<String, FSDataOutputStream> poolMap = new HashMap<String, FSDataOutputStream>();
	static final String logPrefix = "/user/centos/logs/raw/";
//	static FileSystem fileSystem;
//	static {
//		try {
//			poolMap = new HashMap<String, FSDataOutputStream>();
//			Configuration configuration = new Configuration();
//			configuration.set("fs.defaultFS", "hdfs://master:8020");
//			fileSystem = FileSystem.get(configuration);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String[] args) throws IOException {
		Properties properties = new Properties();
		properties.load(new ClassPathResource("zookeeper-properties").getInputStream());
		
		ConsumerConfig config = new ConsumerConfig(properties);
		ConsumerConnector connector = Consumer.createJavaConsumerConnector(config);
		Map<String, Integer> topicCount = new HashMap<>();
		topicCount.put("mybigdata", 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> messagesMap = connector.createMessageStreams(topicCount);
		List<KafkaStream<byte[], byte[]>> list = messagesMap.get("mybigdata");
		for(KafkaStream<byte[], byte[]> ks : list) {
			ConsumerIterator<byte[], byte[]> it = ks.iterator();
			while(it.hasNext()) {
				String msg = new String(it.next().message());
				if(!"".equals(msg)) {
					System.out.println(msg);
					String ip = msg.split(",")[0];
					String path = LogCollectHdfsConsumer.logPrefix + Util.getDate(msg) + "/" + ip + ".log";
					HdfsOutputStreamPool.writeLogToHdfs(path, msg);
				}
			}
		}
//		connector.commitOffsets();
		connector.shutdown();
	}


	
	public static void closeOutputStream(String path) {
		try {
			FSDataOutputStream dataOutputStream = poolMap.get(path);
			if(dataOutputStream != null) {
				dataOutputStream.close();
				poolMap.remove(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
