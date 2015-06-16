package pl.java.scalatech.web.controller;

import java.io.IOException;
import java.security.Principal;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;


@Controller
@RequestMapping("/api/cep/photo")
@Slf4j
public class ImageController {

    @Autowired
    private org.springframework.core.io.Resource photo;

    @Autowired
    private org.springframework.core.io.Resource defaultPhoto;
    

    
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=image/jpeg, image/jpg, image/png, image/gif")
    public @ResponseBody byte[] getPhoto(@PathVariable("id") Integer photoId, Principal principal) throws IOException {
        byte[] content = null;
        content = photoToByte(photo);
        return content;
    }

    private byte[] photoToByte(org.springframework.core.io.Resource photo) throws IOException {
        byte content[] = new byte[(int) photo.contentLength()];
        photo.getInputStream().read(content);
        return content;
    }

}