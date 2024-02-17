package org.studyonline.messagesdk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.studyonline.messagesdk.mapper.MqMessageHistoryMapper;
import org.studyonline.messagesdk.model.po.MqMessageHistory;
import org.studyonline.messagesdk.service.MqMessageHistoryService;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 17/02/2024 11:07 pm
 */
@Slf4j
@Service
public class MqMessageHistoryServiceImpl extends ServiceImpl<MqMessageHistoryMapper, MqMessageHistory> implements MqMessageHistoryService {

}

