package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.model.Challenge;
import br.ufjf.nenc.thautology.model.Notification;
import br.ufjf.nenc.thautology.model.User;
import br.ufjf.nenc.thautology.repository.NotificationRepository;
import br.ufjf.nenc.thautology.util.EntitySupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    public Notification save(Notification notification) {
        final Date now = new Date();

        notification.setLastUpdated(now);
        if (notification.getId()==null) {
            notification.setCreated(now);
        }
        return notificationRepository.save(notification);
    }

    public Iterable<Notification> getNotificationsTo(Optional<String> to, Optional<Boolean> seen) {
        Iterable<Notification> notifications = Collections.emptyList();

        if (to.isPresent()) {
            Optional<User> recipient = new EntitySupplier<>(to, userService::getUser).supply();
            if (recipient.isPresent()) {
                if (seen.isPresent()) {
                    notifications = listAllBySeen(recipient.get(), seen.get());
                } else {
                    notifications = listAll(recipient.get());
                }
            }
        } else {
            notifications = all();
        }

        return notifications;
    }

    private Iterable<Notification> listAllBySeen(User user, Boolean seen) {
        return notificationRepository.findAllByToAndSeen(user, seen);
    }

    private Iterable<Notification> all() {
        return notificationRepository.findAll();
    }

    public Iterable<Notification> listAll(User to) {
        return notificationRepository.findAllByTo(to);
    }
}
