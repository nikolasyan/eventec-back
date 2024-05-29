package com.eventec.eventec.repositories;

import com.eventec.eventec.models.ProfessorCertificationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorCertificationRepository extends JpaRepository<ProfessorCertificationItem, Long> {
    List<ProfessorCertificationItem> findAllByUserid(Long userId);

}