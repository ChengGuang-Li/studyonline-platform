package org.studyonline.media.service;

import org.studyonline.media.model.po.MediaProcess;

import java.util.List;

/**
 *
 * @description Video task processing
 * @author ChengGuang Li
 * @version 1.0
 * @Date 13/02/2024 10:12PM
 */
public interface MediaFileProcessService {


    /**
     * @description Get pending tasks
     * @param shardIndex fragment sequence number
     * @param shardTotal Total number of shards
     * @param count Get the number of records
     * @return List<org.studyonline.media.model.po.MediaProcess/>
     * @author Chengguang Li
     * @date 13/02/2024 10:12PM
     */
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);

    /**
     *  Start a task
     * @param id task id
     * @return true to start the task successfully, false to start the task failed
     */
    public boolean startTask(long id);

    /**
     * @description Save task results
     * @param taskId  task id
     * @param status Task status
     * @param fileId  file id
     * @param url url
     * @param errorMsg error message
     * @return void
     * @author Chengguang Li
     * @date 14/02/2024 12:43
     */
    void saveProcessFinishStatus(Long taskId,String status,String fileId,String url,String errorMsg);


}
