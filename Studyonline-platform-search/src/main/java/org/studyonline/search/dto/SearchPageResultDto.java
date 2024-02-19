package org.studyonline.search.dto;

import lombok.Data;
import lombok.ToString;
import org.studyonline.base.model.PageResult;

import java.util.List;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 19/02/2024 8:07 pm
 */
@Data
@ToString
public class SearchPageResultDto<T> extends PageResult {

    //main category
    List<String> mtList;

    //small category
    List<String> stList;

    public SearchPageResultDto(List<T> items, long counts, long page, long pageSize) {
        super(items, counts, page, pageSize);
    }

}

