package com.eventec.eventec.services;

import com.eventec.eventec.models.EventItem;
import com.eventec.eventec.models.UserItem;
import com.eventec.eventec.repositories.EventItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventItemService {
    @Autowired
    private EventItemRepository eventItemRepository;

    public List<EventItem> getEventsByUser(UserItem user) {
        return eventItemRepository.findByUser(user);
    }
    public Optional<EventItem> findById(Long id) {
        return eventItemRepository.findById(id);
    }
    public EventItem save(EventItem eventItem) {

        return eventItemRepository.save(eventItem);
    }
    public void delete(EventItem eventItem) {
        eventItemRepository.delete(eventItem);
    }

    public boolean existsById(Long id) {
        return eventItemRepository.existsById(id);
    }

    public List<EventItem> getPendingEventsByAddress(String address) {
        return eventItemRepository.findByApprovedAndAddress(false, address);
    }

    public void deleteById(Long id) {
        eventItemRepository.deleteById(id);
    }

    public List<EventItem> getAllEvents() {
        return eventItemRepository.findAll();
    }

    public List<EventItem> getApprovedEventsForCommonUser() {
        return eventItemRepository.findByApprovedAndAbertoPublico(true, true);
    }

    public List<EventItem> getApprovedEventsForInstitutionalUser() {
        return eventItemRepository.findByApproved(true);
    }
}




