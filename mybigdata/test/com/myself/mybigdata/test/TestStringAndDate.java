package com.myself.mybigdata.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.myself.mybigdata.kafka.HdfsOutputStreamPool;
import com.myself.mybigdata.util.LogUtil;
import com.myself.mybigdata.util.Util;

public class TestStringAndDate {
	@Test
	public void getDate() {
		String s = "192.168.235.1 - - [03/Aug/2018:23:10:50 +0800] \"GET /mybigdata/css/iphone.css HTTP/1.0\" 304 0 \"http://localhost/mybigdata/html/iphone.html\" \"Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko Core/1.63.5702.400 QQBrowser/10.2.1893.400\" \"-\"\r\n" + "";
		System.out.println(Util.getYear(s));
		System.out.println(Util.getMonth(s));
		System.out.println(Util.getDay(s));
	}
	
	
	@Test
	public void testLog() throws IOException {
		InputStream inputStream = new ClassPathResource("testf").getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line = reader.readLine();
		System.out.println(line);
		System.out.println(LogUtil.cleanLog(line));
	}
	
	@Test
	public void testFormat() {
		System.out.println(Util.format("", "yyyy/MM/dd HH:mm:ss"));
	}
}
