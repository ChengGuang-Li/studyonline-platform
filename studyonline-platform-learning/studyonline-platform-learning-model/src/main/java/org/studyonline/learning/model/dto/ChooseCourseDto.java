package org.studyonline.learning.model.dto;

import lombok.Data;
import lombok.ToString;
import org.studyonline.learning.model.po.ChooseCourse;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 26/02/2024 10:37 pm
 */
@Data
@ToString
public class ChooseCourseDto extends ChooseCourse {

    //Study qualifications, [{"code":"702001","desc":"Normal study"},{"code":"702002","desc":"No course selection or no payment after course selection"},{ "code":"702003","desc":"Expired and need to apply for renewal or repayment"}]
    public String learnStatus;

}

