package com.briup.woss.main;

import java.io.*;

public class IoFileChangTest {
	public static void main(String[] args) {
		BufferedReader br=null;
		try {
			String file="src/com/briup/woss/File/map.txt";
			String file1="src/com/briup/woss/File/io.txt";
		
			ObjectInputStream oi=new ObjectInputStream(new FileInputStream(file));
			Object obj=oi.readObject();
			System.out.println("保存的不完全信息为：");
			System.out.println(obj);
			
			ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(file1));
			os.writeObject(obj);
			os.flush();
			os.close();
			System.out.println("保存成功！");
			}catch(Exception e){
			e.printStackTrace();
		}
		}
}
