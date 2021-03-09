package net.seehope.controller;

import io.swagger.annotations.*;
import net.seehope.IndexService;
import net.seehope.VideoService;
import net.seehope.common.FilePath;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.Video;
import net.seehope.task.MyTask;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/file")
@Api(tags = "静态资源上传",value = "StaticResourceController")
@CrossOrigin(origins = "*",maxAge = 3600)
public class StaticResourceController {
    @Autowired
    VideoService videoService;

    @Autowired
    IndexService indexService;







    //    @GetMapping("/video")
//    public void videoPreview(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        //假如我把视频1.mp4放在了static下的video文件夹里面
//        //sourcePath 是获取resources文件夹的绝对地址
//        //realPath 即是视频所在的磁盘地址
//        //String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1);
//        String realPath = "/Users/everyluck/Downloads/2.mp4";
//
//
//        Path filePath = Paths.get(realPath);
//        if (Files.exists(filePath)) {
//            System.out.println("视频播放");
//            String mimeType = Files.probeContentType(filePath);
//            if (!StringUtils.isEmpty(mimeType)) {
//                response.setContentType(mimeType);
//                System.out.println("快要播放了");
//            }
//            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
//            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
//        } else {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
//        }
//    }
    @GetMapping(value = "video2",produces="application/json;charset=UTF-8")
    @ApiOperation("得到所有的视频信息")
    public RestfulJson videoShow() throws ParseException {
        return RestfulJson.isOk(videoService.getAllVideos());
    }

    @GetMapping(value = "zip",produces="application/json;charset=UTF-8")
    @ApiOperation("得到所有的压缩包")
    public RestfulJson ZipShow() throws ParseException {
        return RestfulJson.isOk(videoService.getAllZips());
    }
    //上传视频
    @PostMapping(value = "video",produces="application/json;charset=UTF-8")
    @ApiOperation(value = "上传视频",notes = "file字段对应的是视频")
    public RestfulJson updateVideo(HttpServletRequest request, @ApiParam(name = "videoName",value = "视频的名字") String videoName,@ApiParam(name = "describe",value = "文件的描述") String describe,@ApiParam(name = "zipName",value = "固件的名字") String zipName) throws IOException {


        if(zipName != null){
            if(zipName == null){
                throw new RuntimeException("请填写固件的名字");
            }
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            File tempFile = new File(FilePath.path);
            String path = FilePath.zip;

            String fileName = null;
            if(StringUtils.equals("地面站",zipName)){
                indexService.deleteFile(zipName,path);
                videoService.deleteZipInformation(zipName);
            }

            if(videoService.isContain(zipName)){
                throw new RuntimeException("这个固件名字已经被使用");
            }


            fileName = indexService.update(files, path);
            String[] suffix = fileName.split("\\.");

            if(fileName == null){
                throw new RuntimeException("文件并没有上传成功");
            }
            String titleName = zipName;
            zipName +=".";
            zipName += suffix[suffix.length-1];
            indexService.renameTo(fileName,zipName,path);

            Video video = new Video();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = simpleDateFormat.format(new Date());
            video.setCreateTime(createTime);
            video.setPath("static/zip/"+zipName);

            video.setDescribestatic(describe);

            video.setVideoName(titleName);
            videoService.addVideo(video);
            return RestfulJson.isOk("上传成功");
        }else{
            if(videoName == null){
                throw new RuntimeException("请填写视频的名字");
            }
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            File tempFile = new File(FilePath.path);
            String path = FilePath.video;
            if(videoService.isContain(videoName)){
                throw new RuntimeException("这个固件名字已经被使用");
            }

            String fileName = indexService.update(files, path);
            String[] suffix = fileName.split("\\.");

            if(fileName == null){
                throw new RuntimeException("文件并没有上传成功");
            }
            String titleName = videoName;
            videoName +=".";
            videoName += suffix[suffix.length-1];
            indexService.renameTo(fileName,videoName,path);

            Video video = new Video();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = simpleDateFormat.format(new Date());
            video.setCreateTime(createTime);
            video.setPath("static/video/"+videoName);

            video.setDescribestatic(describe);

            video.setVideoName(titleName);
            videoService.addVideo(video);
            return RestfulJson.isOk("上传成功");
        }



    }


    //上传固件
//    @PostMapping(value = "zip",produces="application/json;charset=UTF-8")
//    @ApiOperation(value = "上传固件",notes = "file字段对应的是固件")
//    public RestfulJson updateZip(HttpServletRequest request, @ApiParam(name = "zipName",value = "固件的名字") String zipName,@ApiParam(name = "describe",value = "文件的描述") String describe) throws IOException {
//
//        if(zipName == null){
//            throw new RuntimeException("请填写固件的名字");
//        }
//        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
//        File tempFile = new File(FilePath.path);
//        String path = FilePath.zip;
//
//        String fileName = null;
//            if(StringUtils.equals("地面站",zipName)){
//                indexService.deleteFile(zipName,path);
//                videoService.deleteZipInformation(zipName);
//            }
//
//            if(videoService.isContain(zipName)){
//                throw new RuntimeException("这个固件名字已经被使用");
//            }
//
//
//        fileName = indexService.update(files, path);
//        String[] suffix = fileName.split("\\.");
//
//        if(fileName == null){
//            throw new RuntimeException("文件并没有上传成功");
//        }
//        String titleName = zipName;
//        zipName +=".";
//        zipName += suffix[suffix.length-1];
//        indexService.renameTo(fileName,zipName,path);
//
//        Video video = new Video();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String createTime = simpleDateFormat.format(new Date());
//        video.setCreateTime(createTime);
//        video.setPath("static/zip/"+zipName);
//
//        video.setDescribestatic(describe);
//
//        video.setVideoName(titleName);
//        videoService.addVideo(video);
//        return RestfulJson.isOk("上传成功");
//
//    }

    @PostMapping(value = "zip",produces="application/json;charset=UTF-8")
    @ApiOperation(value = "上传固件",notes = "file字段对应的是固件")
    public RestfulJson updateZip(HttpServletRequest request, @ApiParam(name = "zipName",value = "固件的名字") String zipName,@ApiParam(name = "describe",value = "文件的描述") String describe) throws IOException {

        if(zipName == null){
            throw new RuntimeException("请填写固件的名字");
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        File tempFile = new File(FilePath.path);
        String path = FilePath.zip;

        String fileName = null;
//        if(StringUtils.equals("地面站",zipName)){
//            indexService.deleteFile(zipName,path);
//            videoService.deleteZipInformation(zipName);
//        }

        if(videoService.isContain(zipName)){
            throw new RuntimeException("这个固件名字已经被使用");
        }


        fileName = indexService.update(files, path);
        String[] suffix = fileName.split("\\.");

        if(fileName == null){
            throw new RuntimeException("文件并没有上传成功");
        }
        String titleName = zipName;
        zipName +=".";
        zipName += suffix[suffix.length-1];
        indexService.renameTo(fileName,zipName,path);

        Video video = new Video();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        video.setCreateTime(createTime);
        video.setPath("static/zip/"+zipName);

        video.setDescribestatic(describe);

        video.setVideoName(titleName);
        videoService.addVideo(video);
        return RestfulJson.isOk("上传成功");

    }
    @DeleteMapping(value = "video",produces="application/json;charset=UTF-8")
    @ApiOperation("删除视频")

    @ApiImplicitParams({@ApiImplicitParam(name ="videoName",value = "视频的名字",dataType = "String")
    })
    public RestfulJson deleteVideo(@RequestBody Map map){
        String videoName = map.get("videoName").toString();
        videoService.deleteVideo(videoName);
        return RestfulJson.isOk("删除成功");
    }


    @GetMapping("download/{fileName:.+}")
    @ApiOperation("下载文件，需要传递文件的文件名字在访问地址中")
    public ResponseEntity downLoadFile(@PathVariable("fileName") String fileName) throws IOException {
        System.out.println("fileName"+fileName);
        File file = new File(FilePath.path);

        String path = FilePath.zip;

        File zipFile = new File(file.getAbsolutePath()+path+fileName);

        HttpHeaders headers = new HttpHeaders();
        //二进制流数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //通知浏览器以attachment(下载方式)下载文件，文件名称为指定名称
        //headers.setContentDispositionFormData("attachment",fileName);

        headers.setContentDispositionFormData("attachment", fileName=java.net.URLEncoder.encode(fileName, "UTF-8"));
        byte[] bytes = FileUtils.readFileToByteArray(zipFile);
        return new ResponseEntity<byte[]>(bytes,headers, HttpStatus.CREATED);
    }
    
}
