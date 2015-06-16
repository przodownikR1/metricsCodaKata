package pl.java.scalatech.service;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import pl.java.scalatech.domain.Dictionary;
@Validated
public interface DictionaryService {
    Dictionary findById(String word);

    Page<Dictionary> getAll(Pageable pageable);

    Dictionary save(Dictionary dictionary);

    @NotNull Dictionary myValidMethod(@Min(3) String arg1);
    
    void show(@NotNull @Valid Dictionary dictionary);
}
