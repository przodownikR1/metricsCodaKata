package pl.java.scalatech.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.java.scalatech.domain.Dictionary;

public interface DictionaryRespository extends MongoRepository<Dictionary, String>{

}
