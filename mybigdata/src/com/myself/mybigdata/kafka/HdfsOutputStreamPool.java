package com.myself.mybigdata.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * HDFS输出流池<br>
 * 包含hive和hdfs输出处理 
 */
public class HdfsOutputStreamPool {
	public static Map<String, FSDataOutputStream> outPoolMap;
	static final String logPrefixForHdfs = "/user/centos/logs/raw";
	static final String logPrefixForHive = "/user/hive/warehouse/logs.db";
	static FileSystem fileSystem = null;
	static {
		try {
			outPoolMap = new HashMap<String, FSDataOutputStream>();
			Configuration configuration = new Configuration();
			configuration.set("fs.defaultFS", "hdfs://master:8020");
			fileSystem = FileSystem.get(configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取HDFS输出流
	 */
	private static FSDataOutputStream getHdfsOutputStream(String path) {
		try {
			FSDataOutputStream dataOutputStream = outPoolMap.get(path);
			if(dataOutputStream == null) {
				Path dfsPath = new Path(path);
				if(!fileSystem.exists(dfsPath)) {
					fileSystem.createNewFile(dfsPath);
				}
				dataOutputStream = fileSystem.append(dfsPath);
				outPoolMap.put(path, dataOutputStream);
			}
			return dataOutputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 关闭输出流
	 */
	public static void closeOutputStream(String path) {
		try {
			FSDataOutputStream dataOutputStream = outPoolMap.get(path);
			if(dataOutputStream != null) {
				dataOutputStream.close();
				outPoolMap.remove(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 写入到HDFS 
	 */
	public static void writeLogToHdfs(String path, String line) {
		try {
			FSDataOutputStream dataOutputStream = getHdfsOutputStream(path);
			dataOutputStream.write((line + "\r\n").getBytes());
			dataOutputStream.hsync();
			closeOutputStream(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
