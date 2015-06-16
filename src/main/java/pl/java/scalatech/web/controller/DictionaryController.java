package pl.java.scalatech.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import pl.java.scalatech.domain.Dictionary;
import pl.java.scalatech.service.DictionaryService;

@RestController
@RequestMapping(value = "/api/dictionary", produces = { MediaType.APPLICATION_JSON_VALUE })
public class DictionaryController {

    private DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;

    }

    @Timed
    @RequestMapping(value = "/{word}", method = RequestMethod.GET)
    public ResponseEntity<Dictionary> getDictionary(@PathVariable("word") String word) {
        return new ResponseEntity<>(dictionaryService.findById(word), HttpStatus.OK);

    }

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PagedResources<Resource<Dictionary>>> getAll(@PageableDefault(size = 5) Pageable pageable,
            PagedResourcesAssembler<Dictionary> assembler) {
        Page<Dictionary> items = dictionaryService.getAll(pageable);
        return new ResponseEntity<>(assembler.toResource(items), HttpStatus.OK);

    }

    @Timed
    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<Void> saveDictionary(@Valid @RequestBody Dictionary dictionary) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Dictionary newDictionary = dictionaryService.save(dictionary);
        httpHeaders.setLocation(linkTo(DictionaryController.class).slash(newDictionary.getWord()).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);

    }
}
