package org.studyonline.base.model;

import lombok.Data;
import lombok.ToString;

/**
 * @description Pagination Query Common Parameters.
 * @author ChengGuang
 * @date 2023/12/29 22:30
 * @version 1.0
 */
@Data
@ToString
public class PageParams {

    //Current Page
    private Long pageNo = 1L;

    //Default Number of Records Per Page.
    private Long pageSize =10L;

    public PageParams(){

    }

    public PageParams(long pageNo,long pageSize){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
