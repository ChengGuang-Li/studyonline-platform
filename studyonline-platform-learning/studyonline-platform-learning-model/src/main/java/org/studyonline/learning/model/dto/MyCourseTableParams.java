package org.studyonline.learning.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 26/02/2024 10:37 pm
 */
@Data
@ToString
public class MyCourseTableParams {

    String userId;

    //Course type [{"code":"700001","desc":"Free Course"},{"code":"700002","desc":"Paid Course"}]
    private String courseType;

    // 1 : Sort by learning time;  2 : Sort by joining time
    private String sortType;

    //1 is about to expire, 2 has expired
    private String expiresType;

    int page=1;
    int startIndex;
    int size=4;

}
