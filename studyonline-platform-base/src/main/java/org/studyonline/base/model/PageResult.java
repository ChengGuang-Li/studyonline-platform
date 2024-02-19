package org.studyonline.base.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @description Pagination Query Result Model Class
 * @author ChengGuang
 * @date 2023/12/29 22:30
 * @version 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    // Data List
    private List<T> items;

    // Total Number of Records.
    private long counts;

    // Current Page.
    private long page;

    // Number of Records Per Page.
    private long pageSize;

    public PageResult(List<T> items, long counts, long page, long pageSize) {
        this.items = items;
        this.counts = counts;
        this.page = page;
        this.pageSize = pageSize;
    }

}
