package br.ufjf.nenc.lp.web.rest;

import br.ufjf.nenc.lp.core.bo.AcquiredConquest;
import br.ufjf.nenc.lp.core.entity.Conquest;
import br.ufjf.nenc.lp.core.service.ConquestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingEnumeration;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static br.ufjf.nenc.lp.core.bo.AcquiredConquest.*;
import static br.ufjf.nenc.lp.core.util.ArrayUtil.firstOrDefault;
import static br.ufjf.nenc.lp.core.util.ArrayUtil.firstOrNull;

@RestController
@RequestMapping("/repository")
public class RepositoryRest {

    private static final String HTML_TEMPLATE = "<!DOCTYPE html>\n"+
            "<html>\n"+
            "<head lang=\"en\">\n"+
            "    <meta charset=\"UTF-8\">\n"+
            "    <title>LOPL Repository</title>\n"+
            "</head>\n"+
            "<body>%s</body>\n" +
            "</html>";

    public static final String CONQUEST_PARAM = "conquest";

    public static final String LO_ID_PARAM = "loId";

    public static final String INTERACTION_PARAM = "interaction";

    public static final String PARTICIPANT_ID_PARAM = "id";

    public static final String VALUE_PARAM = "value";

    public static final String NAME_PARAM = "name";

    public static final String DEFAULT_INTERACTION = "0";

    private final ConquestService conquestService;

    @Autowired
    public RepositoryRest(ConquestService conquestService) {
        this.conquestService = conquestService;
    }


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String query(HttpServletRequest request) {

        Map<String, String[]> parameters = request.getParameterMap();
        Conquest conquest = conquestService.save(new Builder()
                .setConquestType(firstOrNull(parameters.get(CONQUEST_PARAM)))
                .setLearningObjectId(firstOrNull(parameters.get(LO_ID_PARAM)))
                .setInteraction(firstOrDefault(parameters.get(INTERACTION_PARAM), DEFAULT_INTERACTION))
                .setParticipantBizId(firstOrNull(parameters.get(PARTICIPANT_ID_PARAM)))
                .setParticipantName(firstOrNull(parameters.get(NAME_PARAM)))
                .setValue(firstOrNull(parameters.get(VALUE_PARAM)))
                .build()
        );

        return String.format(HTML_TEMPLATE, conquest);
    }

}
