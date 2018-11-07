package xyz.wongs.tools;

import java.io.File;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName CleanNameTool
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/27 19:43
 * @Version 1.0.0
*/
public class CleanNameTool {


    public static void main(String[] args) {
        CleanNameTool cleanNameTool = new CleanNameTool();
        String path = "F:\\Downloads\\Img";
        File file = new File(path);

        cleanNameTool.getFile(file);
    }

    public void getFile(File file){
        String fileName = null;
        if(!file.exists()){
            System.out.println(file.getPath()+ " 文件不存在");
            return;
        }
        if(!file.isDirectory()){
            fileName= file.getName();
            if(fileName.contains("结果")){
                file.delete();
                System.out.println(fileName);
            }
        }
        File[] files = file.listFiles();
        if(null==files || files.length==0){
            return;
        }
        for(File f:files){
            getFile(f);
        }
    }

}
