package com.briup.woss.client;

import java.io.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.briup.util.BIDR;
import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.briup.woss.ConfigurationAWare;

public class GatherImpl implements Gather,ConfigurationAWare{
	String pathName;
	String pathName1="src/com/briup/woss/File/map.txt";
	//list存入完整数据
	static List<BIDR> list=new ArrayList<BIDR>();
   //存储不完整数据Map<IP,BIDR>
	static Map<String,BIDR> map=new HashMap();
	Configuration conf=null;
	//加载备份文件
	@Override
	public void init(Properties p) {
		// TODO Auto-generated method stub
		pathName=(String) p.get("src-file");
	}

	@Override
	public Collection<BIDR> gather() throws Exception {
		// TODO Auto-generated method stub
		BackUP bi=conf.getBackup();
		Map<String, BIDR> newMap=(Map<String,BIDR>)bi.load(pathName1, bi.LOAD_REMOVE);
		if(newMap!=null){
			map.putAll(newMap);
		}
		//1.读temp.txt,成功的标志是打印到控制台
		BufferedReader br=new BufferedReader(new FileReader(new File(pathName)));
		BIDR bidr=new BIDR();
		String str="";
		while((str=br.readLine())!=null){
			//System.out.println(str);
			String[] line=str.split("[|]");
				//System.out.println(line.length);
				if(line[2].equals("7")){
					bidr=new BIDR();
					bidr.setAAA_login_name(line[0].substring(1));
					bidr.setNAS_ip(line[1]);
					Long login_date=Long.parseLong(line[3]);
					Timestamp login_time=new Timestamp(login_date*1000);
					bidr.setLogin_date(login_time);
					bidr.setLogin_ip(line[4]);	
					//保存不完整信息
					map.put(line[4],bidr);
					}else if(line[2].equals("8")){
						BIDR bidr1 = map.get(line[4]);
						if(bidr1!=null){
							//设置用户下线时长 
							Long logout_date=Long.parseLong(line[3]);
							Timestamp logout_time=new Timestamp(logout_date*1000);
							bidr1.setLogout_date(logout_time);
							//计算出用户在线时长
							Integer time_deration=(int) (logout_date - (bidr1.getLogin_date().getTime())/1000);
							bidr1.setTime_deration(time_deration);
							//完整数据存入list
							list.add(bidr1);
							map.remove(line[4]);
						}	
						
					}
		}
		bi.store(pathName1, map, bi.STORE_OVERRIDE);
		System.out.println("备份的不完整数据为："+map.size());
		br.close();
		System.out.println("list="+list.size());
		return list;
		
		
	}
	public static void main(String[] args) throws Exception {
		new GatherImpl().gather();
		/*for(BIDR bidr:list){
			System.out.println(bidr.getAAA_login_name()+","+bidr.getLogin_ip()+" "+bidr.getNAS_ip()+" "+bidr.getTime_deration()+","+bidr.getLogin_date()+","+bidr.getLogout_date());
		}*/
		System.out.println("完整的信息为"+list.size());
		System.out.println("不完整信息："+map.size());
	}

	@Override
	public void setConfiguration(Configuration co) {
		// TODO Auto-generated method stub
		this.conf=co;
	}

}
