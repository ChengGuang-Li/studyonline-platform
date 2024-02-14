package org.studyonline.media.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyonline.media.mapper.MediaFilesMapper;
import org.studyonline.media.mapper.MediaProcessHistoryMapper;
import org.studyonline.media.mapper.MediaProcessMapper;
import org.studyonline.media.model.po.MediaFiles;
import org.studyonline.media.model.po.MediaProcess;
import org.studyonline.media.model.po.MediaProcessHistory;
import org.studyonline.media.service.MediaFileProcessService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class MediaFileProcessServiceImpl implements MediaFileProcessService {

    @Autowired
    MediaProcessMapper mediaProcessMapper;

    @Autowired
    MediaProcessHistoryMapper mediaProcessHistoryMapper;

    @Autowired
    MediaFilesMapper mediaFilesMapper;

    @Override
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("shardTotal",shardTotal);
        map.put("shardIndex",shardIndex);
        map.put("count",count);
        List<MediaProcess> mediaProcesses = mediaProcessMapper.selectListByShardIndex(map);

        return mediaProcesses;
    }

    @Override
    public boolean startTask(long id) {
        int result = mediaProcessMapper.startTask(id);
        return result<=0?false:true;
    }

    @Override
    public void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg) {
         //1. Find the task and return directly if it does not exist
        MediaProcess mediaProcess = mediaProcessMapper.selectById(taskId);
        if(mediaProcess == null){
            return;
        }
        //2. Update Media_Process Table
        //If Failed - update and return / IF Success - Continue
        if("3".equals(status)){
            mediaProcess.setStatus("3");
            mediaProcess.setFailCount(mediaProcess.getFailCount()+1);
            mediaProcess.setErrormsg(errorMsg);
            mediaProcessMapper.updateById(mediaProcess);
            return;
        }
         //3. Update the MediaFile Table
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileId);
        mediaFiles.setUrl(url);
        mediaFilesMapper.updateById(mediaFiles);
        //4. Update Media_Process Table
        mediaProcess.setStatus("2");
        mediaProcess.setFinishDate(LocalDateTime.now());
        mediaProcess.setUrl(url);
        mediaProcessMapper.updateById(mediaProcess);

        //5. Insert data into Media_Process_History Table
        MediaProcessHistory mediaProcessHistory = new MediaProcessHistory();
        BeanUtils.copyProperties(mediaProcess,mediaProcessHistory);
        mediaProcessHistoryMapper.insert(mediaProcessHistory);
        //6.Delete this task from Media_Process Table
        mediaProcessMapper.deleteById(taskId);
    }
}
