package pl.java.scalatech.domain;

import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Dictionary {
    @Id
    @Size(min = 3, max = 30, message = "min 3 znaki")
    private String word;
    @Size(min = 4, max = 30, message = "min 4 znaki")
    private String translateWord;

}
