package xyz.wongs.common.tool.util;

import java.io.File;
import java.io.IOException;

public class FileTools {


	/**
	 *
	 * @Title: getCurtFilePath
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param bool 是否需要标准路径，默认为true，需要标准路径
	 * @return
	 * @return: String
	 */
	public static String getCurtFilePath(Boolean bool){
		File directory = new File("");//设定为当前文件夹
		String path = null;
		try {
			if(bool){
				path =directory.getCanonicalPath();
			} else {
				path=  directory.getAbsolutePath();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;

	}


	public static String substr(String str){
		String sl = "</table>";
		int i = str.indexOf(sl);
		//System.out.println(i);
		return str.substring(i,sl.length());
	}


}
