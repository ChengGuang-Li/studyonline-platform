package org.studyonline.messagesdk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.studyonline.messagesdk.model.po.MqMessage;

import java.util.List;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 17/02/2024 11:01 pm
 */
public interface MqMessageMapper extends BaseMapper<MqMessage> {

    @Select("SELECT t.* FROM mq_message t WHERE t.id % #{shardTotal} = #{shardindex} and t.state='0' and t.message_type=#{messageType} limit #{count}")
    List<MqMessage> selectListByShardIndex(@Param("shardTotal") int shardTotal, @Param("shardindex") int shardindex, @Param("messageType") String messageType, @Param("count") int count);
}
