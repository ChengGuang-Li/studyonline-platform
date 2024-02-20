package org.studyonline.ucenter.model.dto;

import lombok.Data;
import org.studyonline.ucenter.model.po.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 20/02/2024 2:11 pm
 */
@Data
public class UserExt extends User {
    //User rights
    List<String> permissions = new ArrayList<>();
}
