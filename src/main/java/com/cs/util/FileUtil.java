package com.cs.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by smi1e
 * Date 2019/5/26 9:35
 * Description
 */
public class FileUtil {
    private static String parentPath="/home/images/";

    /**
     *
     * @param filePath
     * @param type
     * @return 生成图片网络url
     */
    public static String generateFilePath(String filePath,String type){
        if (type.equals("user")){
            return "http://47.93.221.192:81/images/userImg/"+filePath;
        }else {
            return "http://47.93.221.192:81/images/bookImg/"+filePath;
        }
    }

    /**
     * 上传文件
     * @param file
     * @param type
     * @return 文件网络url
     */
    public static String uploadFile(MultipartFile file,String type){
        //文件名
        String filename = UUID.randomUUID().toString()+FormateTime.getData2()+file.getOriginalFilename();
        File dest = null;
        //创建文件
        if (type.equals("user")){
            dest = new File(parentPath+"userImg",filename);
        }else {
            dest = new File(parentPath+"bookImg",filename);
        }
        try {
            file.transferTo(dest);
            return generateFilePath(filename,type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }


}
