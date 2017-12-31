package com.briup.woss.main;
import java.util.Collection;
import com.briup.util.BIDR;
import com.briup.util.ConfigurationImpl;
import com.briup.util.Logger;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;

public class ClientTest {
	
	public static void main(String[] args) throws Exception {
		ConfigurationImpl confi=new ConfigurationImpl();
		Logger log=confi.getLogger();
		try {
			// 1.采集数据
			log.debug("开始采集数据");
			Gather gi=confi.getGather();
			Collection<BIDR> bidrs=gi.gather();
			//2.启动服务器端
			log.debug("启动服务器端");
			Client ci=confi.getClient();
			//3.发送数据
			log.debug("数据开始传输");
			ci.send(bidrs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("服务器端启动异常");
		}

	}

}
