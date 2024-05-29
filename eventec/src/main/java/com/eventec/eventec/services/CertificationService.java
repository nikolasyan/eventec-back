package com.eventec.eventec.services;

import com.eventec.eventec.models.CertificationItem;
import com.eventec.eventec.models.ProfessorCertificationItem;
import com.eventec.eventec.models.SubscriptionItem;
import com.eventec.eventec.repositories.CertificationRepository;
import com.eventec.eventec.repositories.ProfessorCertificationRepository;
import com.eventec.eventec.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ProfessorCertificationRepository professorCertificationRepository;

    public List<CertificationItem> getCertificatesByUserid(Long userid) {
        return certificationRepository.findAllByUserid(userid);
    }

    public void generateCertificatesForEvent(Long eventId, String professorName, Long userid, List<Long> userIds) {
        List<SubscriptionItem> subscriptions = subscriptionRepository.findAllByEvent_Id(eventId);

        for (SubscriptionItem subscription : subscriptions) {
            if (userIds.contains(subscription.getUser().getUserid())) {
                CertificationItem certification = new CertificationItem();
                certification.setSubscription(subscription);
                certification.setProfessorName(professorName);
                certificationRepository.save(certification);

                // Lógica para salvar o certificado do professor
                ProfessorCertificationItem professorCertification = new ProfessorCertificationItem();
                professorCertification.setProfessorName(professorName);
                professorCertification.setEventTitle(subscription.getEvent().getTitle());
                professorCertification.setEventDate(subscription.getEvent().getDateEvent());
                professorCertification.setUserid(userid);
                professorCertificationRepository.save(professorCertification);
            }
        }
    }

    public List<ProfessorCertificationItem> getProfessorCertificatesByUserid(Long userid) {
        return professorCertificationRepository.findAllByUserid(userid);
    }
}
