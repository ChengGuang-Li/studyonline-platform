package org.studyonline.search.service;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 19/02/2024 8:11 pm
 */
public interface IndexService {

    /**
     * @param indexName index name
     * @param id key
     * @param object index object
     * @return Boolean true: Success,false: failed
     * @description add index
     * @Author: Chengguang Li
     * @Date: 19/02/2024 8:11 pm
     */
    public Boolean addCourseIndex(String indexName,String id,Object object);


    /**
     * @description Update Index
     * @param indexName Index name
     * @param id key
     * @param object index object
     * @return Boolean true: Success,false: failed
     * @Author: Chengguang Li
     * @Date: 19/02/2024 8:11 pm
     */
    public Boolean updateCourseIndex(String indexName,String id,Object object);

    /**
     * @description Delete Index
     * @param indexName Index Name
     * @param id  Key
     * @return java.lang.Boolean
     * @Author: Chengguang Li
     * @Date: 19/02/2024 8:11 pm
     */
    public Boolean deleteCourseIndex(String indexName,String id);

}