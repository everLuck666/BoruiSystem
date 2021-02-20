package net.seehope.impl;

import net.seehope.IndexService;
import net.seehope.common.FilePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class IndexServiceImpl implements IndexService {
    Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);
    @Override
    public String update(List<MultipartFile> files, String path) {
        File tempFile = new File(FilePath.path);
        String fileName = null;
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                throw new RuntimeException("上传的文件是空的");
            }
            fileName = file.getOriginalFilename();

            File dest = new File(tempFile.getAbsolutePath() + path + fileName);
            if (dest.exists()) {
                String[] photo = fileName.split("\\.");
                Date d = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String time = dateFormat.format(new Date());
                fileName = file.getOriginalFilename().replace("."+photo[photo.length-1],"")+ time + "." + photo[photo.length-1];
                dest = new File(tempFile.getAbsolutePath() + path + fileName);
            }
            try {
                logger.info(tempFile.getAbsolutePath() + path + fileName);
                file.transferTo(dest);
                logger.info("第" + (i + 1) + "个文件上传成功");//因为是从第0个开始算的，所以显示的时候要从1开始算

            } catch (IOException e) {
                logger.error(e.toString(), e);
                //File dest = new File(tempFile.getAbsolutePath()+"/src/main/resources/static/images/");
                throw new RuntimeException("第" + (i++) + "个文件上传失败");
            }
        }

        return fileName;
    }

    @Override
    public void renameTo(String oldName, String newName,String path) throws IOException {

        File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");


        File oldFile = new File(tempFile.getAbsolutePath()+path+"/"+oldName);

        File newFile = new File(tempFile.getAbsolutePath()+path+"/"+newName);

        if(newFile.exists()){
            oldFile.delete();
            logger.info("开始删除刚刚上传的文件");
            throw new java.io.IOException("文件已经存在");
        }

        if(oldFile.renameTo(newFile)){
            logger.warn("已经重新命名");

        }else{
            throw new RuntimeException("Error");
        }
    }

    @Override
    public Long getStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime().getTime();
    }

    @Override
    public Long getEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        return calendar.getTime().getTime();
    }
}