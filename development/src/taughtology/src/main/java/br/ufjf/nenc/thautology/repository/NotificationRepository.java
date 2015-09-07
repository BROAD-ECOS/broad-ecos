package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Notification;
import br.ufjf.nenc.thautology.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, String> {

    Iterable<Notification> findAllByTo(User to);

    Iterable<Notification> findAllByToAndSeen(User user, Boolean seen);
}
