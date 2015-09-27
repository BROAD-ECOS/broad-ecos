package br.ufjf.nenc.broadecos.rankr.web;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.rankr.model.CurrentUser;
import br.ufjf.nenc.broadecos.rankr.model.User;
import br.ufjf.nenc.broadecos.rankr.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/ranking")
public class RankingResource {

    private final RankingService rankingService;

    @Autowired
    public RankingResource(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @RequestMapping(value = "/top", method = GET)
    public Page<User> top(@RequestParam("page") Integer page, @RequestParam("page") Integer pageSize){
        final PageRequest pageRequest = new PageRequest(page, pageSize);
        return rankingService.getTop(pageRequest);
    }

    @RequestMapping(value = "/update", method = GET)
    public String update(Context context){

        // 1 - Obter todas as experiências da plataforma do colaborador
        System.out.println(context);


        return "OK!";
    }

}
