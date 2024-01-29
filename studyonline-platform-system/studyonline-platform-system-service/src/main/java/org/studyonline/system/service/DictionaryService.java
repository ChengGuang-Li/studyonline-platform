package org.studyonline.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.studyonline.system.model.po.Dictionary;

import java.util.List;

/**
 * dictionary query services
 *
 * @author : chengguang li
 * @date: 06/01/2024
 */

public interface DictionaryService extends IService<Dictionary> {

    /**
     * Query all data dictionary contents
     * @return
     */
    List<Dictionary> queryAll();

    /**
     * Query dictionary based on code
     * @param code -- String  --  Dictionary Code
     * @return
     */
    Dictionary getByCode(String code);
}
