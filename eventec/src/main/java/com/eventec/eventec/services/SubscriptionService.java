package com.eventec.eventec.services;

import com.eventec.eventec.models.SubscriptionItem;
import com.eventec.eventec.models.UserItem;
import com.eventec.eventec.models.EventItem;
import com.eventec.eventec.repositories.SubscriptionRepository;
import com.eventec.eventec.repositories.UserItemRepository;
import com.eventec.eventec.repositories.EventItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserItemRepository userItemRepository;

    @Autowired
    private EventItemRepository eventItemRepository;

    public SubscriptionItem subscribe(Long userid, Long eventId) throws Exception {
        UserItem user = userItemRepository.findById(userid)
                .orElseThrow(() -> new Exception("Usuário não encontrado!"));

        EventItem event = eventItemRepository.findById(eventId)
                .orElseThrow(() -> new Exception("Evento não encontrado!"));


        Optional<SubscriptionItem> existingSubscription = findSubscription(userid, eventId);
        if (existingSubscription.isPresent()) {
            throw new IllegalStateException("Usuário já está inscrito neste evento!");
        }


        SubscriptionItem subscription = new SubscriptionItem();
        subscription.setUser(user);
        subscription.setEvent(event);

        return subscriptionRepository.save(subscription);
    }

    public Optional<SubscriptionItem> findSubscription(Long userid, Long eventId) {
        return subscriptionRepository.findAll()
                .stream()
                .filter(s -> s.getUser().getUserid().equals(userid) && s.getEvent().getId().equals(eventId))
                .findFirst();
    }

    public void unsubscribe(Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }

    public List<SubscriptionItem> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUser_Userid(userId);
    }

    public Map<String, Long> countEventsWithMostSubscriptions() {
        List<Object[]> eventSubscriptionCounts = subscriptionRepository.countEventsWithSubscriptions();

        return eventSubscriptionCounts.stream()
                .collect(Collectors.toMap(
                        objects -> (String) objects[0],
                        objects -> (Long) objects[1]
                ));
    }
}
