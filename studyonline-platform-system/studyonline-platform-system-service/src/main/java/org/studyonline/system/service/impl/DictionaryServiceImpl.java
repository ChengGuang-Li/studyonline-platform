package org.studyonline.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.studyonline.system.mapper.DicMapper;
import org.studyonline.system.model.po.Dictionary;
import org.studyonline.system.service.DictionaryService;

import java.util.List;



@Service
@Slf4j
public class DictionaryServiceImpl extends ServiceImpl<DicMapper, Dictionary> implements DictionaryService {

    @Override
    public List<Dictionary> queryAll() {
        List<Dictionary> list = this.list();

        return list;
    }

    @Override
    public Dictionary getByCode(String code) {
        LambdaQueryWrapper<Dictionary> dictionaryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dictionaryLambdaQueryWrapper.eq(Dictionary::getCode,code);
        Dictionary dictionary = this.getOne(dictionaryLambdaQueryWrapper);
        return dictionary;
    }
}
