package br.ufjf.nenc.lp.web.rest;

import br.ufjf.nenc.lp.core.entity.Participant;
import br.ufjf.nenc.lp.core.entity.Profile;
import br.ufjf.nenc.lp.core.service.ProfileService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static br.ufjf.nenc.lp.core.util.ArgumentUtil.or;

@RestController
@RequestMapping("/profiles")
public class ProfileRest {

    public static final Integer DEFAULT_SIZE = 20;
    public static final String DEFAULT_ORDER = "score";
    public static final Sort.Direction DEFAULT_ORDER_ORIENTATION = Sort.Direction.ASC;

    public final ProfileService service;

    @Autowired
    public ProfileRest(ProfileService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Profile> query(
            @RequestParam(value="order", required = false) String order,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="organizationId", required = false) String organizationId) {

        Sort sort = new Sort(Sort.Direction.fromStringOrNull(direction), or(order, DEFAULT_ORDER));
        PageRequest pageRequest = new PageRequest(0, or(size, DEFAULT_SIZE), sort);
        Optional<String> organizationIdOpt = Optional.ofNullable(organizationId);

        return Lists.newArrayList(service.findPage(organizationIdOpt, pageRequest).iterator());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Profile get(@PathVariable(value="id") String id) {
        return service.findOneById(id);
    }



}
