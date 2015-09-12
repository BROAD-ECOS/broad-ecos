package br.ufjf.nenc.lp.web.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/upload")
public class FileUploadRest {



    @RequestMapping(method= RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(1 + "-uploaded")));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + 1 + " into " + 1 + "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + 1 + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + 1 + " because the file was empty.";
        }
    }

}
