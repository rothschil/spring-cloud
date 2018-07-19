package xyz.wongs.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-boot xyz.wongs.web
 * @Description: TODO
 * @date 2018/6/21 8:48
 **/
@RestController
public class UploadAndDownloadController{

    static final String savePath = "E:\\workspace\\testUploadAndDownload\\";

    @RequestMapping(value = "/upload",method= RequestMethod.POST)
    @ResponseBody
    public String hello(@RequestParam("file") MultipartFile file)  {

        String result = null;
        try {
            result = testUploadMethodOne(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String testUploadMethodOne(MultipartFile file) throws IllegalStateException, IOException{
        File newFile = new File(savePath, file.getOriginalFilename());
        //如果不存在，创建一哥副本
        if (!newFile.exists()) {
            newFile.createNewFile();

        }
        //将io上传到副本中
        file.transferTo(newFile);
        return "上传成功" ;
    }

}
