package com.eventec.eventec.services;

import com.eventec.eventec.models.CertificationItem;
import com.eventec.eventec.models.SubscriptionItem;
import com.eventec.eventec.repositories.CertificationRepository;
import com.eventec.eventec.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<CertificationItem> getCertificatesByUserid(Long userid) {
        return certificationRepository.findAllByUserid(userid);
    }

    public void generateCertificatesForEvent(Long eventId, List<Long> userIds) {
        List<SubscriptionItem> subscriptions = subscriptionRepository.findAllByEvent_Id(eventId);

        for (SubscriptionItem subscription : subscriptions) {
            if (userIds.contains(subscription.getUser().getUserid())) {
                CertificationItem certification = new CertificationItem();
                certification.setSubscription(subscription);
                certificationRepository.save(certification);
            }
        }
    }
}

