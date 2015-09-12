package br.ufjf.nenc.lp.web.rest;

import br.ufjf.nenc.lp.core.entity.LearningObject;
import br.ufjf.nenc.lp.core.exception.NotFoundException;
import br.ufjf.nenc.lp.core.service.LearningObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lo")
public class LearningObjectRest {

    @Autowired
    private LearningObjectService service;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody LearningObject post(@RequestBody LearningObject descriptor) {
        return service.save(descriptor);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public @ResponseBody LearningObject post(@PathVariable("id") String id, @RequestBody LearningObject descriptor) {
        LearningObject lo = null;

        if (id.equals(descriptor.getId())) {
            lo = service.save(descriptor);
        } else {
            throw new IllegalArgumentException();
        }

        return lo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<LearningObject> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
         public @ResponseBody LearningObject get(@PathVariable("id") String id) {
        Optional<LearningObject> lo = service.find(id);
        if (!lo.isPresent()) {
            throw new NotFoundException();
        }
        return lo.get();
    }
}
