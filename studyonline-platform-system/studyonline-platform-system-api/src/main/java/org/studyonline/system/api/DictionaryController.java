package org.studyonline.system.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.system.model.po.Dictionary;
import org.studyonline.system.service.DictionaryService;

import java.util.List;

/**
 * @description System information management service
 * @author ChengGuang
 * @date 06/01/2024 17:01
 * @version 1.0
 */
@Api(value = "System information management interface",tags = "System information management interface")
@Slf4j
@RestController
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @ApiOperation("Get all dictionary ")
    @GetMapping("/dictionary/all")
    public List<Dictionary> queryAll(){
        return dictionaryService.queryAll();
    }

    @ApiOperation("Get the corresponding dictionary based on code")
    @GetMapping("/dictionary/code/{code}")
    public Dictionary getByCode(@PathVariable @ApiParam("Dictionary Code") String code){
         return dictionaryService.getByCode(code);
    }
}
