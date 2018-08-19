package com.myself.mybigdata.util;

import java.util.StringTokenizer;

public class LogUtil {
	/**
	 * 格式化日志记录
	 */
	public static String formatLog(String line) {
		StringBuilder builder = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(line);
		String delimiter = ",";
		String hostName = tokenizer.nextToken(",");
		String ip = tokenizer.nextToken(" ").substring(1);
		String s1 = tokenizer.nextToken(" ");
		String s2 = tokenizer.nextToken(" ");
		String s3 = tokenizer.nextToken(" ");
		String s4 = tokenizer.nextToken(" ");
		String s5 = tokenizer.nextToken("\"");
		String s6 = tokenizer.nextToken("\"");
		String s7 = tokenizer.nextToken(" ");
		String s8 = tokenizer.nextToken(" ");
		String s9 = tokenizer.nextToken(" ");
		String s10 = tokenizer.nextToken(" ");
		String s11 = tokenizer.nextToken("\"");
		String s12 = tokenizer.nextToken("\"");
		String s13 = tokenizer.nextToken(" ");
		String s14 = tokenizer.nextToken(" ");
		builder.append(ip).append(delimiter)
			.append(s1).append(delimiter)
			.append(s2).append(delimiter)
			.append(s3).append(" ")
			.append(s4).append(delimiter)
			.append(s5).append(delimiter)
			.append(s6).append(delimiter)
			.append(s8).append(delimiter)
			.append(s9).append(delimiter)
			.append(s10).append(delimiter)
			.append(s12).append(delimiter)
			.append(s14);
		return builder.toString();
	}
	
	/**
	 * 清洗日志
	 */
	public static String cleanLog(String line) {
		String[] items = formatLog(line).split(",");
		StringBuilder builder = new StringBuilder();
		builder.append(items[0]).append(",").append(items[3]).append(",").append(items[5]);
		return builder.toString();
	}
}
