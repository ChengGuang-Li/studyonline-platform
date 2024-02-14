package org.studyonline.media.service.jobhandler;


import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.studyonline.base.utils.Mp4VideoUtil;
import org.studyonline.media.model.po.MediaProcess;
import org.studyonline.media.service.MediaFileProcessService;
import org.studyonline.media.service.MediaFileService;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class VideoTask {

    @Autowired
    MediaFileProcessService mediaFileProcessService;

    @Autowired
    MediaFileService mediaFileService;

    @Value("${videoprocess.ffmpegpath}")
    private String ffmpegpath;

    @XxlJob("shardingJobHandler")
    public void videoJobHandler() throws Exception{
          //Sharding parameters
        int shardIndex = XxlJobHelper.getShardIndex(); //The serial number of the executor, starting from 0
        int shardTotal = XxlJobHelper.getShardTotal(); //total number of executors
        int processors = Runtime.getRuntime().availableProcessors(); //Number of CPU cores
        //1. Find the pending tasks,
        List<MediaProcess> mediaProcessList = mediaFileProcessService.getMediaProcessList(shardIndex, shardTotal, processors);
        int size = mediaProcessList.size();
        log.debug("The number of video task processing obtained： {}",size);
        if(size < 1){
            return;
        }
        //2. Start task
        // Create a thread pool -
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        //Use counter
        CountDownLatch countDownLatch = new CountDownLatch(size);
        mediaProcessList.forEach(mediaProcess -> {
            //Put the tasks into the thread pool
            executorService.execute(() ->{
              try{
                  //3. Execute Tasks
                  //Perform video transcoding
                  Long id = mediaProcess.getId();
                  String bucket = mediaProcess.getBucket();
                  String filePath = mediaProcess.getFilePath();
                  boolean b = mediaFileProcessService.startTask(id);
                  String md5 = mediaProcess.getFileId(); //File Md5
                  if(!b){
                      log.debug("Preemption task failed,task is: {}",id);
                      return;
                  }
                  //4.Download Video into local
                  File file = mediaFileService.downloadFileFromMinIO(bucket, filePath);
                  if(file == null){
                      log.debug("Download Video Failed: taskID:{}, bucket:{}, objectName:{}",id,bucket,filePath);
                      mediaFileProcessService.saveProcessFinishStatus(id,"3",md5,null,"Download Video Failed");
                      return;
                  }
                  String video_path = file.getAbsolutePath();

                  String mp4Name = md5 + ".mp4";
                  File mp4File = null;
                  try{
                      mp4File = File.createTempFile("minio", ".mp4");

                  }catch (Exception e){
                      log.error("Create temporary File Exception:,{}",e.getMessage());
                      mediaFileProcessService.saveProcessFinishStatus(id,"3",md5,null,"Create temporary file Failed");
                      return;
                  }
                  String mp4_path = mp4File.getAbsolutePath();
                  //5. Start video conversion
                  Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpegpath,video_path,mp4Name,mp4_path);
                  String result = mp4VideoUtil.generateMp4();
                  if(!"success".equals(result)){
                      mediaFileProcessService.saveProcessFinishStatus(id,"3",md5,null,result);
                      log.debug("Video Conversion Failed:, bucket: {}, objectName:{}, result:{}",bucket,filePath,result);
                      return;
                  }
                  //record tasks - success
                  //6.Upload the Video to MinIo
                  boolean b1 = mediaFileService.addMediaFilesToMinIo(mp4_path, "video/mp4", bucket, filePath);
                  if(!b1){
                      mediaFileProcessService.saveProcessFinishStatus(id,"3",md5,null,"Upload Video to MinIo Failed");
                      log.debug("Upload Video to MinIo Failed:, bucket: {}, objectName:{},tasksId: {}",bucket,filePath,id);
                      return;
                  }
                  //7.Save Info - Success
                  String url = getFilePath(md5, ".mp4");
                  mediaFileProcessService.saveProcessFinishStatus(id,"2",md5,url,"Video Conversion and Upload Success");
              }finally {
                  countDownLatch.countDown(); //Counter minus 1
              }

            });
        });
        countDownLatch.await(30, TimeUnit.MINUTES); //Block, Max wait time is 30 minutes
    }

    private String getFilePath(String fileMd5,String fileExt){
        return   fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/" +fileMd5 +fileExt;
    }

}
