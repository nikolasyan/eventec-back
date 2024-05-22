package com.eventec.eventec.controllers;
import com.eventec.eventec.models.CertificationItem;
import org.springframework.web.bind.annotation.*;

import com.eventec.eventec.services.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
public class CertificationController {

    @Autowired
    private CertificationService certificationService;

    @PostMapping("/generate/{eventId}")
    public ResponseEntity<String> generateCertificatesForEvent(@PathVariable Long eventId, @RequestBody List<Long> userIds) {
        try {
            certificationService.generateCertificatesForEvent(eventId, userIds);
            return ResponseEntity.ok("Certificados gerados com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao gerar certificados: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<List<CertificationItem>> getCertificationsByUserId(@PathVariable Long userid) {
        List<CertificationItem> certifications = certificationService.getCertificatesByUserid(userid);

        if (certifications.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(certifications);
        }
    }


}
