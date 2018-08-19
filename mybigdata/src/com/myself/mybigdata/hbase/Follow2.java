package com.myself.mybigdata.hbase;

import java.io.IOException;  
import java.util.Iterator;  
import java.util.List;  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.hbase.HBaseConfiguration;  
import org.apache.hadoop.hbase.HColumnDescriptor;  
import org.apache.hadoop.hbase.HTableDescriptor;  
import org.apache.hadoop.hbase.KeyValue;  
import org.apache.hadoop.hbase.MasterNotRunningException;  
import org.apache.hadoop.hbase.ZooKeeperConnectionException;  
import org.apache.hadoop.hbase.client.Get;  
import org.apache.hadoop.hbase.client.HBaseAdmin;  
import org.apache.hadoop.hbase.client.HTableInterface;  
import org.apache.hadoop.hbase.client.HTablePool;  
import org.apache.hadoop.hbase.client.Put;  
import org.apache.hadoop.hbase.client.Result;  
import org.apache.hadoop.hbase.client.ResultScanner;  
import org.apache.hadoop.hbase.client.Scan;  
import org.apache.hadoop.hbase.util.Bytes;  

@SuppressWarnings("deprecation")  
public class Follow2 {  
    static Configuration cfg = HBaseConfiguration.create();  
    @SuppressWarnings({ "resource" })  
    public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException{  
        String tb = "followlist2";  
        String cf = "follows";  
        byte[] bcf = Bytes.toBytes(cf);  
        HBaseAdmin admin = new HBaseAdmin(cfg);  
        HTablePool pool = new HTablePool();  
        //check Fake关注Real了没  
        byte[] real = Bytes.toBytes("TheRealMT");  
        HTableInterface table=pool.getTable(tb);  
        Get g = new Get(Bytes.toBytes("TheFakeMT"));  
        g.addColumn(bcf, real);  
        Result r = table.get(g);  
        byte[] value = r.getValue(bcf, real);  
        if(value!=null)  
            System.out.println("Exist!");  
        else{  
            System.out.println("Don't exist!");  
        }  
        Scan s = new Scan();  
                ResultScanner rs =table.getScanner(s);  
                System.out.println("Scan Result: ");  
                for(Result rr:rs){  
                List<KeyValue> list = rr.list();  
                for(KeyValue kv:list){  
                System.out.print("行键: "+Bytes.toString(kv.getRow()));//获取行键  
                System.out.print("\t列族: "+Bytes.toString(kv.getFamily()));//获取列族              
                System.out.print("\t列限定符: "+Bytes.toString(kv.getQualifier()));//获取列限定符  
                System.out.println("\t单元值: "+Bytes.toInt(kv.getValue()));//获取单元值  
                }  
                System.out.println();  
                }  
            table.close();  
    }  
}
