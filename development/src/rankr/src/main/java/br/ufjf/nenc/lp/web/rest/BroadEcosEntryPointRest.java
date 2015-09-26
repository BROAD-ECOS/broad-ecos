package br.ufjf.nenc.lp.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pl")
public class BroadEcosEntryPointRest {

    @RequestMapping(method=RequestMethod.GET)
    public String main(){
        return "redirect:/entry/";
    }
}
