package org.studyonline.search.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.search.service.IndexService;

import java.io.IOException;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 19/02/2024 8:20 pm
 */
@Slf4j
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    RestHighLevelClient client;

    @Override
    public Boolean addCourseIndex(String indexName, String id, Object object) {
        String jsonString = JSON.toJSONString(object);
        IndexRequest indexRequest = new IndexRequest(indexName).id(id);
        //Specify index document content
        indexRequest.source(jsonString, XContentType.JSON);
        //Index response object
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Error adding index:{}",e.getMessage());
            StudyOnlineException.cast("Error adding index");
        }
        String name = indexResponse.getResult().name();
        System.out.println(name);
        return name.equalsIgnoreCase("created") || name.equalsIgnoreCase("updated");
    }

    @Override
    public Boolean updateCourseIndex(String indexName, String id, Object object) {
        String jsonString = JSON.toJSONString(object);
        UpdateRequest updateRequest = new UpdateRequest(indexName, id);
        updateRequest.doc(jsonString, XContentType.JSON);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Error update index:{}",e.getMessage());
            StudyOnlineException.cast("Error update index");
        }
        DocWriteResponse.Result result = updateResponse.getResult();
        return result.name().equalsIgnoreCase("updated");
    }

    @Override
    public Boolean deleteCourseIndex(String indexName, String id) {
        //Delete index request object
        DeleteRequest deleteRequest = new DeleteRequest(indexName,id);
        //response object
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Error delete index:{}",e.getMessage());
            StudyOnlineException.cast("Error delete index");
        }
        //Get response results
        DocWriteResponse.Result result = deleteResponse.getResult();
        return result.name().equalsIgnoreCase("deleted");
    }
}
