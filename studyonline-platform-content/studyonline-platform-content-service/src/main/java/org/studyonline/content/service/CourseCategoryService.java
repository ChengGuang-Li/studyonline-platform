package org.studyonline.content.service;

import org.studyonline.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

public interface CourseCategoryService {
    /**
     * Course category query
     * @param id root node id
     * @return All child nodes below the root node
     */
    List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
