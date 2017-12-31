package com.briup.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Properties;

public class BackUPImpl implements BackUP {

	String filePath;
	Object object = null;
	private static List<BIDR> list;

	@Override
	public void init(Properties p) {
		// TODO Auto-generated method stub

	}

	// 通过键名获取已经备份的数据 key备份数据的键
	@Override
	public Object load(String key, boolean flag) throws Exception {
		// TODO Auto-generated method stub
		File file = new File(filePath, key);
		// file是否为空
		if (file.exists() && file.length() != 0) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			object = ois.readObject();
		}
		return object;

	}

	// 通过键名存储数据，key-备份数据的键，date-需要备份的数据，flag如果键值已经存在数据，追加还是覆盖之前的数据
	// 文件路径key
	@Override
	public void store(String key, Object date, boolean flag) throws Exception {
		// TODO Auto-generated method stub
		// 通过key找备份文件，键值就是文件名
		// 接收到完整数据
		File file = new File(key);
		if(file.exists()){
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file, flag));
		oos.writeObject(date);
		oos.flush();
		oos.close();
		/*
		 * File file=new File(filePath,fileName); 判断是不是存在 if(!file.exits()){
		 * file.createNewFile(); } ObjectOutputStream oos=new
		 * ObjectOutputStream(new FileOutputStream(file,flag));
		 * oos.writeObject(date);
		 * 
		 */

	}
	}

}
