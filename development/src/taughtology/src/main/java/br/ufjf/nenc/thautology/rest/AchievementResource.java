package br.ufjf.nenc.thautology.rest;


import br.ufjf.nenc.thautology.model.Achievement;
import br.ufjf.nenc.thautology.service.AchievementService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/achievements")
@Log4j
public class AchievementResource {

    private final AchievementService achievementService;

    @Autowired
    public AchievementResource(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @RequestMapping(method = GET)
    public Achievement get(@RequestParam("answerId") Optional<String> answerId){
        Optional<Achievement> achievement = achievementService.findByAnswerId(answerId);
        return achievement.orElse(null);
    }


}
