package com.briup.woss.Server;
import java.io.*;
import java.net.*;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import com.briup.util.BIDR;
import com.briup.woss.server.Server;

public class ServrImpl implements Server{
	private ServerSocket ss;
	private int port;
	private ObjectInputStream oi;
	

	@Override
	public void init(Properties p) {
		// TODO Auto-generated method stub
		System.out.println(p.get("port"));
	     port=Integer.parseInt((String) p.get("port"));
	     System.out.println(port);
	
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<BIDR> revicer() throws Exception {
		
		// TODO Auto-generated method stub
		//1.port
		//数据流
		//1)创建ServerSocket
		ss=new ServerSocket(port);
		System.out.println("服务器已启动，请连接·····");
		while(true){
		Socket s=ss.accept();
		oi=new ObjectInputStream(s.getInputStream());
		List<BIDR> c=(List<BIDR>)oi.readObject();
		System.out.println("连接成功");
		return c;
		}
		
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	

	

}
