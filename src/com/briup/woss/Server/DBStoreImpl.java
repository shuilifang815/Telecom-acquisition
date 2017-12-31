package com.briup.woss.Server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import com.briup.util.BIDR;
import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.server.DBStore;

public class DBStoreImpl implements DBStore,ConfigurationAWare{
	/*
	 * driver=oracle.jdbc.driver.OracleDriver
	 * url=jdbc:oracle:thin:@localhost:1521:XE username=oracle password=orcle
	 */
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private static Connection connection;
	private String aaa_login_name;
	private String login_ip;
	private java.sql.Date login_date;
	private java.sql.Date logout_date;
	private String nas_ip;
	private String time_duration;
	private int i_date;
	private static PreparedStatement[] ps = new PreparedStatement[31];;
	String sql;
	static int i = 0;
	private static String pathName = "src/com/briup/woss/File/list.txt";
	Configuration conf=null;
	// 存放完整数据
	static List<BIDR> list = new ArrayList<>();
	@Override
	public void init(Properties p) {
		driver=p.getProperty("driver");
		url=p.getProperty("url");
		username=p.getProperty("username");
		password=p.getProperty("password");
	}
	@Override
	public void saveToDB(Collection<BIDR> collction) throws Exception {
		BackUP bi=conf.getBackup();
		Logger log=conf.getLogger();
		try {
			// 注册驱动
			Class.forName(driver);
			// 创建连接
			connection = DriverManager.getConnection(url,username,password);
			System.out.println(connection);
			for (int i = 0; i < 31; i++) {

				sql = "insert into t_detail_" + i
						+ "(aaa_login_name,login_ip,login_date,logout_date,nas_ip,time_duration) "
						+ "values(?,?,?,?,?,?)";
				ps[i] = connection.prepareStatement(sql);
			}
			//connection最多创建300个prepareStatement
			//将ps = connection.prepareStatement(sql);放在下面的for (BIDR bidr : collction)里面遍历，由于遍历次数过多，会出现超出游标最大值异常
        	//遍历31次，与下面遍历bidr对象时，存储信息时的ps相对应
			for (BIDR bidr : collction) {
				Timestamp login_d = bidr.getLogin_date();
				String s_date = login_d.toString();
				String[] str1 = s_date.split(" ");
				String[] str2 = str1[0].split("-");
				i_date = Integer.parseInt(str2[2]);
				aaa_login_name = bidr.getAAA_login_name();
				login_ip = bidr.getLogin_ip();
				login_date = new java.sql.Date(bidr.getLogin_date().getTime());
				logout_date = new java.sql.Date(bidr.getLogout_date().getTime());
				// 通过PreparedStatement将信息存储到数据库中
				ps[i_date].setString(1, aaa_login_name);
				ps[i_date].setString(2, login_ip);
				ps[i_date].setDate(3, login_date);
				ps[i_date].setDate(4, logout_date);
				ps[i_date].setString(5, nas_ip);
				ps[i_date].setString(6, time_duration);
				// 执行sql
				ps[i_date].executeUpdate();
				i++;
				if (i == 200) {
					i = 1 / 0;
				}
				list.add(bidr);
				System.out.println("插入数据成功！");
			}

			log.info("入库数据的个数" + list.size());
			collction.removeAll(list);
			log.info("未入库数据的个数" + collction.size());

		} catch (Exception e) {
	 		connection.rollback();
			bi.store(pathName, list, BackUP.STORE_OVERRIDE);//对象.字段会出现黄色波浪线，可以改成类名.字段
			log.debug("备份数据为"+list.size());
			log.debug("未入库数据的个数" + collction.size());
			log.debug("插入个数为："+i);
			
		}

	}

	/*public static void main(String[] args) throws Exception {
		GatherImpl ga = new GatherImpl();
		new DBStoreImpl().saveToDB(ga.gather());
		System.out.println("备份数据的个数为：" + list.size());
		System.out.println(i);
	}*/
	@Override
	public void setConfiguration(Configuration co) {
		// TODO Auto-generated method stub
		this.conf=co;
		
	}

}
