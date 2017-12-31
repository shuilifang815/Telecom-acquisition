package com.briup.woss.main;

import java.util.Collection;
import com.briup.util.BIDR;
import com.briup.util.ConfigurationImpl;
import com.briup.util.Logger;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;

public class ServerTest {
	public static void main(String[] args) throws Exception {
		ConfigurationImpl conf = new ConfigurationImpl();
		Logger log = conf.getLogger();
		try {
			//1.启动服务器
			log.info("服务器启动");
			Server serverImpl = conf.getServer();
			log.info("服务器完成");
			//2.接收数据
			log.info("等待接收数据");
			Collection<BIDR> bidrs = serverImpl.revicer();
			DBStore dbStore = conf.getDBStore();
			log.info("开始数据入库");
			dbStore.saveToDB(bidrs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("服务器端启动异常······");
			
		}
		//2.接收数据
		
		/*ConfigurationImpl conf=new ConfigurationImpl();
		try {
			//启动服务器
			new ServerTest().Accept();
//		    DBStoreImpl db=new DBStoreImpl();
			DBStore db=conf.getDBStore();
		    System.out.println(db);
			db.saveToDB(bidr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void Accept() throws Exception{
		ConfigurationImpl conf=new ConfigurationImpl();
		//接收数据；
//		ServrImpl si=new ServrImpl();
		Server si = conf.getServer();
		
		System.out.println(si);
		try {
			 bidr=(List<BIDR>) si.revicer();
			 //fos=new FileOutputStream("src/a.txt");
			System.out.println("接收数据");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
}
}
