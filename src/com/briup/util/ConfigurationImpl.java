package com.briup.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import com.briup.woss.ConfigurationAWare;
import com.briup.woss.WossModule;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;

public class ConfigurationImpl implements Configuration{
	String filePath="src/com/briup/woss/File/conf.xml";
    //存入woss模块对象
	Map<String,WossModule> wossMap=new HashMap<>();
	//存放配置信息
	Properties pro=new Properties();
	@Override
	public BackUP getBackup() throws Exception {
		// TODO Auto-generated method stub
		return (BackUP) wossMap.get("backup");
	}

	@Override
	public Client getClient() throws Exception {
		// TODO Auto-generated method stub
		return (Client) wossMap.get("client");
	}

	@Override
	public DBStore getDBStore() throws Exception {
		// TODO Auto-generated method stub
		return (DBStore) wossMap.get("dbstore");
	}
	

	public static void main(String[] args) throws Exception {
		new ConfigurationImpl().getDBStore();
	}
	
	@Override
	public Gather getGather() throws Exception {
		// TODO Auto-generated method stub
		return (Gather) wossMap.get("gather");
	}
	

	@Override
	public Logger getLogger() throws Exception {
		// TODO Auto-generated method stub
		return (Logger) wossMap.get("logger");
	}


	@Override
	public Server getServer() throws Exception {
		return (Server) wossMap.get("server");
	}
	

	public ConfigurationImpl() {
		try {
		//1.获取解析器。读取conf.xml
		   //创建SAXReader读取器，专门用于读取xml
		SAXReader saxReader=new SAXReader();
		//2.获取根节
			Document document=saxReader.read(filePath);
			
			Element rootElement=document.getRootElement();

			//3.获取子节点--属性值
			List elements=rootElement.elements();
			for(Object object:elements){
				Element e=(Element)object;
				String name=e.getName();
				//System.out.println("子节点"+name);
				//class
				String attValue=e.attributeValue("class");
//				System.out.println(attValue);
				//通过反射获取对象
					WossModule woss;
					try {
						woss = (WossModule)Class.forName(attValue).newInstance();
//						System.out.println(woss);
						wossMap.put(name, woss);
//						System.out.println(name);
						for(String key:wossMap.keySet()){
//							System.out.println(key+":"+wossMap.get(key));
							
							//4.固定值-->Properties
							List ee=e.elements();
							for(Object obj:ee){
								Element el=(Element)obj;
								String key1=el.getName();
								String value=el.getText();
//								System.out.println(key1);
//								System.out.println(el.getName()+"*:*"+el.getText());
								pro.put(key1, value);
								String po=(String)pro.get("po");
							 //   System.out.println("po:**************");
							//	System.out.println(po);   
								
							}
							//配置信息依赖注入
							for(Object obj:wossMap.values()){
								//调用init（）方法，注入配置信息
								if(obj instanceof WossModule){
									((WossModule) obj).init(pro);
								}
								if(obj instanceof ConfigurationAWare){
									((ConfigurationAWare)obj).setConfiguration(this);
								}
							}
								
						}

					
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//class--->实例化对象放入集合
			   
					
					}
			
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
			
			
		
		
		//固定值
		//class实例化对象放入集合
		
	}

}
