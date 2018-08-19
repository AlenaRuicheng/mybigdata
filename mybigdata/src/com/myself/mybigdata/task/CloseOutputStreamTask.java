package com.myself.mybigdata.task;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.hadoop.fs.FSDataOutputStream;

import com.myself.mybigdata.kafka.LogCollectHdfsConsumer;
import com.myself.mybigdata.util.Util;

public class CloseOutputStreamTask {
	/**
	 * 执行定时器任务
	 */
	public void startTask() {
		Timer timer = new Timer();
		TimerTask task = getInstanceOfTimerTask();
		//每隔24小时执行一次
		timer.schedule(task, 2000, 24*60*60*1000);
	}
	
	/**
	 *创建任务 
	 */
	private TimerTask getInstanceOfTimerTask() {
		return new TimerTask() {
			@Override
			public void run() {
				Map<String, FSDataOutputStream> map = LogCollectHdfsConsumer.poolMap;
				for(Entry<String, FSDataOutputStream> entry : map.entrySet()) {
					String key = entry.getKey();
					FSDataOutputStream value = entry.getValue();
					String yesterdayDate = Util.getYesterdaySystemDate();
					if(key.contains(yesterdayDate)) {
						try {
							value.close();
							map.remove(key);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
	}
}
