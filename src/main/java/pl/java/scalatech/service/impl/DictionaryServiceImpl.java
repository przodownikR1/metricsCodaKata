package pl.java.scalatech.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;

import pl.java.scalatech.domain.Dictionary;
import pl.java.scalatech.repository.DictionaryRespository;
import pl.java.scalatech.service.DictionaryService;

@Service
@Slf4j
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private DictionaryRespository dictionaryRespository;

    @Override
    @Timed
 
    public Dictionary findById(String word) {
        return dictionaryRespository.findOne(word);
    }

    @Override
    @Timed
  
    public Page<Dictionary> getAll(Pageable pageable) {
        return dictionaryRespository.findAll(pageable);
    }

    @Override
    @Timed
    public Dictionary save(Dictionary dictionary) {
        return dictionaryRespository.save(dictionary);
    }

    @Override
    public   Dictionary myValidMethod( String arg1){
        return Dictionary.builder().word("validator").translateWord("walidacja").build();
    
    }

    @Override
    public void show(Dictionary dictionary) {
        log.info("^^^^ {}",dictionary);
        
    }

}
