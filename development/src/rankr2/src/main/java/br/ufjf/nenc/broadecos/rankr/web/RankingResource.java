package br.ufjf.nenc.broadecos.rankr.web;

import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.rankr.component.ConquestLoader;
import br.ufjf.nenc.broadecos.rankr.model.CurrentUser;
import br.ufjf.nenc.broadecos.rankr.model.User;
import br.ufjf.nenc.broadecos.rankr.provider.BroadEcosProvider;
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

    private final ConquestLoader conquestLoader;

    private final BroadEcosApiProvider broadEcosApiProvider;

    @Autowired
    public RankingResource(RankingService rankingService, ConquestLoader conquestLoader, BroadEcosApiProvider broadEcosApiProvider) {
        this.rankingService = rankingService;
        this.conquestLoader = conquestLoader;
        this.broadEcosApiProvider = broadEcosApiProvider;
    }

    @RequestMapping(value = "/top", method = GET)
    public Page<User> top(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        final PageRequest pageRequest = new PageRequest(page, pageSize);
        return rankingService.getTop(pageRequest);
    }

    @RequestMapping(value = "/update", method = GET)
    public String update(Context context){

        conquestLoader.load(broadEcosApiProvider.withContext(context));

        return "OK!";
    }

}
