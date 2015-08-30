package br.ufjf.nenc.thautology.rest;


import br.ufjf.nenc.thautology.model.Achievement;
import br.ufjf.nenc.thautology.model.AchievementSummary;
import br.ufjf.nenc.thautology.service.AchievementService;
import br.ufjf.nenc.thautology.service.AchievementSummaryService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/achievement-summaries")
@Log4j
public class AchievementSummaryResource {

    private final AchievementSummaryService achievementSummaryService;

    @Autowired
    public AchievementSummaryResource(AchievementSummaryService achievementSummaryService) {
        this.achievementSummaryService = achievementSummaryService;
    }

    @RequestMapping(path = "/{userId}", method = GET)
    public AchievementSummary get(@PathVariable("userId") Optional<String> userId){

        return achievementSummaryService.getUseAchievementrSummary(userId.get());
    }


}
