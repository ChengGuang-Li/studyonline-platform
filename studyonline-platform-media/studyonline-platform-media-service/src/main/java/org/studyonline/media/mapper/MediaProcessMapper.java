package org.studyonline.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.studyonline.media.model.po.MediaProcess;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Video files pending Mapper
 * </p>
 *
 * @author Chenguang Li
 */
public interface MediaProcessMapper extends BaseMapper<MediaProcess> {

    List<MediaProcess> selectListByShardIndex(Map map);

    /**
     * Start a task
     * @param id task id
     * @return Number of updated records
     */
    int startTask(@Param("id") long id);
}
