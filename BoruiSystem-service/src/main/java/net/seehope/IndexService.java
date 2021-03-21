package net.seehope;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IndexService {
    //上传服务
    String update(List<MultipartFile> files, String path);

    //文件重命名
    void renameTo(String oldName,String newName,String path) throws IOException;

    //得到今日开始时间
    Long getStartTime() ;


    //得到今日结束时间
    Long getEndTime();

    //删除包含关键字的文件
    void deleteFile(String fileName,String path);

    //删除指定的文件
    void deleteOneFile(String fileName,String path);
}
