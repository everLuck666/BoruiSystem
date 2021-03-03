package net.seehope;

import net.seehope.pojo.Video;
import net.seehope.pojo.vo.VideoVo;

import java.text.ParseException;
import java.util.List;

public interface VideoService {

    List<VideoVo> getAllVideos() throws ParseException;



    void addVideo(Video video);

    void deleteVideo(String videoName);

    List<VideoVo> getAllZips() throws ParseException;

    //删除固件记录
    void deleteZipInformation(String zipName);

    //判断是否包含这个固件的名字
    boolean isContain(String zipName);
}
