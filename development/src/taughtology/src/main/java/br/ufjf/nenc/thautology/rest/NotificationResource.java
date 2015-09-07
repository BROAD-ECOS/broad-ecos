package br.ufjf.nenc.thautology.rest;

import br.ufjf.nenc.thautology.model.Notification;
import br.ufjf.nenc.thautology.model.Reference;
import br.ufjf.nenc.thautology.service.NotificationService;
import br.ufjf.nenc.thautology.util.IterableList;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/notifications")
@Log4j
public class NotificationResource {

    private final NotificationService notificationService;

    @Autowired
    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @RequestMapping(method = GET)
    public List<Notification> get(@RequestParam("to") Optional<String> to, @RequestParam("seen") Optional<Boolean> seen){
        return new IterableList<>(notificationService.getNotificationsTo(to, seen));
    }

    @RequestMapping(method = POST)
    public Notification save(@RequestBody @Valid Notification notification){
        log.info("Saving " + notification);
        return notificationService.save(notification);
    }

}
