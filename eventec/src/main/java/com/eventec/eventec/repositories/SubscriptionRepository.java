package com.eventec.eventec.repositories;

import com.eventec.eventec.models.SubscriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepository extends JpaRepository<SubscriptionItem, Long> {
    List<SubscriptionItem> findByUser_Userid(Long userId);
    List<SubscriptionItem> findAllByEvent_Id(Long eventId);

    @Query("SELECT s.title, COUNT(s) FROM SubscriptionItem s GROUP BY s.title ORDER BY COUNT(s) DESC")
    List<Object[]> countEventsWithSubscriptions();
}
