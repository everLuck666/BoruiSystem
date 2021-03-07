package net.seehope.impl;

import net.seehope.VideoService;
import net.seehope.common.FilePath;
import net.seehope.mapper.VideoMapper;
import net.seehope.pojo.Video;
import net.seehope.pojo.vo.VideoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Autowired
    VideoMapper videoMapper;
    @Override
public List<VideoVo> getAllVideos() throws ParseException {


        List<Video> videoList =  videoMapper.getAllVideo();
        List<VideoVo> videoList1 = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Video video:videoList){
            VideoVo videoVo = new VideoVo();
            Date date = simpleDateFormat.parse(video.getCreateTime());
            String createTime = simpleDateFormat.format(date).toString();
            videoVo.setCreateTime(createTime);
            videoVo.setPath(video.getPath());
            videoVo.setDescirbe(video.getDescribestatic());
            videoVo.setVideoName(video.getVideoName());
            videoList1.add(videoVo);
        }
        return videoList1;
    }
    @Override
    public List<VideoVo> getAllZips() throws ParseException {


        List<Video> videoList =  videoMapper.getAllZip();
        List<VideoVo> videoList1 = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Video video:videoList){
            VideoVo videoVo = new VideoVo();
            Date date = simpleDateFormat.parse(video.getCreateTime());
            String createTime = simpleDateFormat.format(date).toString();
            videoVo.setCreateTime(createTime);
            videoVo.setPath(video.getPath());
            videoVo.setVideoName(video.getVideoName());
            videoList1.add(videoVo);
        }
        return videoList1;
    }

    @Override
    public void deleteZipInformation(String zipName) {
        Video video = new Video();
        video.setVideoName(zipName);
        videoMapper.delete(video);

    }

    @Override
    public boolean isContain(String zipName) {
        Video video = new Video();
        video.setVideoName(zipName);
        Video value = videoMapper.selectOne(video);
        if(value != null){
            return true;
        }

        return false;
    }


    @Override
    public void addVideo(Video video) {
        videoMapper.insert(video);

    }

    @Override
    public void deleteVideo(String videoName) {
        Video items = new Video();
        items.setVideoName(videoName);
        Video tempItem = videoMapper.selectOne(items);
        if (tempItem != null) {
            videoMapper.delete(items);
            File tempFile = new File(FilePath.path);
            File dest = new File(tempFile.getAbsolutePath() + FilePath.video + videoName);
            if (dest != null) {
                dest.delete();
            } else {
                logger.warn("请注意要删除的门票不存在图片");
            }
        } else {
            logger.info("视频不存在");
            throw new RuntimeException("这个视频不存在");
        }

    }
}
