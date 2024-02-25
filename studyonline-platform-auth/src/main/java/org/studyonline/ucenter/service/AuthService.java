package org.studyonline.ucenter.service;

import org.studyonline.ucenter.model.dto.AuthParamsDto;
import org.studyonline.ucenter.model.dto.XcUserExt;

/**
 * @Description: Unified authentication interface
 * @Author: Chengguang Li
 * @Date: 24/02/2024 11:21 pm
 */
public interface AuthService {
    /**
     * @description authentication method
     * @param authParamsDto Authentication parameters
     * @return org.studyonline.ucenter.model.po.XcUser ; user information
     * @author: Chengguang Li
     * @date: 24/02/2024 11:21 pm
     */
    XcUserExt execute(AuthParamsDto authParamsDto);

}
