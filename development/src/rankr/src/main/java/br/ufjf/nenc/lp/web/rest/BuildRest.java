package br.ufjf.nenc.lp.web.rest;

import br.ufjf.nenc.lp.core.pl.ir.ProductDescriptor;
import br.ufjf.nenc.lp.core.service.BuildService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Map;

@RestController
@RequestMapping(value = "/pl")
public class BuildRest {

    @Autowired
    BuildService buildService;

    @RequestMapping(value = "/build",
            method= RequestMethod.POST,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Map<String, String> build(@RequestBody ProductDescriptor productDescription) {
        return ImmutableMap.of("id", buildService.build(productDescription).getFile().getName());
    }

    @RequestMapping(value = "/build/{id:.+}", method= RequestMethod.GET)
    public @ResponseBody FileSystemResource build(@PathVariable("id") String id) {

        File build = buildService.getBuildFile(id);

        return new FileSystemResource(build);
    }
}
