package pl.java.scalatech.service;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public interface DemoService {
    @NotNull
    @NotBlank
    String doSomething(@NotBlank @Size(min = 3) String message, @NotNull @Size(min = 2) Collection<String> str);
}