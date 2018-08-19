package com.myself.mybigdata.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 处理PV值(PV值统计)
 */
public class PvProcessor {
	private Admin admin;
	private Connection conn;

	public PvProcessor() {
		initConnection();
	}

	public void initConnection() {
		try {
			Configuration conf = HBaseConfiguration.create();
			conn = ConnectionFactory.createConnection(conf);
			admin = conn.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 刪除非法
	 */
	public boolean deleteRow(String tableName) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        String rowName;
        if (admin.tableExists(TableName.valueOf(tableName))) {
            try {
            	ResultScanner scan = table.getScanner(new Scan());
        		for (Result r : scan) {
        			rowName = Bytes.toString(r.getRow());
        			if(!rowName.equals("0") && !rowName.equals("1") && !rowName.equals("2")) {
                		Delete delete = new Delete(rowName.getBytes());
                        table.delete(delete);
                		System.out.println(rowName);
                	}
        		}
            	
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

	/**
	 * 查询pv表中记录
	 */
	public List<String[]> queryTableBatch(String tableName){
		List<Get> getList = new ArrayList<Get>();
		List<String[]> resultList = new ArrayList<String[]>();
		
		try {
			Table table = conn.getTable(TableName.valueOf(tableName));// 获取表
			ResultScanner scan = table.getScanner(new Scan());
			for (Result r : scan) {
				getList.add(new Get(r.getRow()));
			}
			Result[] results = table.get(getList);// 直接查getList<Get>
			for (Result result : results) {// 对返回的结果集进行操作
				StringBuilder builder = new StringBuilder();
				for (Cell kv : result.rawCells()) {
					builder.append(Bytes.toString(CellUtil.cloneValue(kv))).append(",");
				}
				resultList.add(builder.toString().split(","));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultList;
	}

	public static void main(String[] args) throws Exception {
		PvProcessor p= new PvProcessor();
		p.queryTableBatch("pv");
	}
}
