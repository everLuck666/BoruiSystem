package net.seehope.mapper;

import net.seehope.pojo.Video;
import org.apache.ibatis.annotations.CacheNamespace;

import java.util.List;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@CacheNamespace
public interface VideoMapper extends tk.mybatis.mapper.common.Mapper<Video> {
    List<Video> getAllVideo();


    List<Video> getAllZip();

    List<Video> getAllPdf();
}




