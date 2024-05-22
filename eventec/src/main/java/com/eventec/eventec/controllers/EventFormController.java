package com.eventec.eventec.controllers;

import com.eventec.eventec.models.EventItem;
import com.eventec.eventec.models.UserItem;
import com.eventec.eventec.models.SubscriptionItem;
import com.eventec.eventec.repositories.SubscriptionRepository;
import com.eventec.eventec.services.EventItemService;
import com.eventec.eventec.services.UserItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class EventFormController {

    @Autowired
    private EventItemService eventItemService;

    @Autowired
    private UserItemService userItemService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;


    @PostMapping("/event")
    public ResponseEntity<?> createEventItem(@RequestBody @Valid EventItem eventItem) {

        String userEmail = eventItem.getUser().getEmail();
        String userPassword = eventItem.getUser().getPassword();
        Optional<UserItem> userOpt = userItemService.getByEmailAndPassword(userEmail, userPassword);

        if(userOpt.isPresent()) {
            UserItem user = userOpt.get();
            eventItem.setUser(user);

            eventItemService.save(eventItem);
            return ResponseEntity.ok("Evento criado com sucesso!");
        } else {
            // Lide com o cenário em que o usuário não foi encontrado, talvez retornando um erro.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");
        }
    }

    @GetMapping("/myEvents")
    public ResponseEntity<?> getMyEvents(@RequestParam String email, @RequestParam String password) {

        Optional<UserItem> user = userItemService.getByEmailAndPassword(email, password);

        if (user.isPresent()) {
            List<EventItem> myEvents = eventItemService.getEventsByUser(user.get());

            return ResponseEntity.ok(myEvents);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
    }
    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        if (eventItemService.existsById(id)) {
            eventItemService.deleteById(id);
            return ResponseEntity.ok("Evento excluído com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento não encontrado.");
        }
    }

    @PutMapping("/eventEdit/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody @Valid EventItem updatedEvent) {
        Optional<EventItem> existingEventOpt = eventItemService.findById(id);

        if (existingEventOpt.isPresent()) {
            EventItem existingEvent = existingEventOpt.get();

            existingEvent.setDateEvent(updatedEvent.getDateEvent());

            eventItemService.save(existingEvent);

            return ResponseEntity.ok("Evento atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento não encontrado.");
        }
    }
    @GetMapping("/allEvents")
    public ResponseEntity<?> getAllEvents() {
        List<EventItem> events = eventItemService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/pendingEvents")
    public ResponseEntity<?> getPendingEvents(@RequestParam String email, @RequestParam String password) {

        Optional<UserItem> user = userItemService.getByEmailAndPassword(email, password);

        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }

        if (user.get().getUserType() != UserItem.UserType.diretor) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não é um diretor.");
        }

        String unidade = user.get().getUnidade();
        if (unidade == null || unidade.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unidade do diretor não especificada.");
        }

        List<EventItem> pendingEvents = eventItemService.getPendingEventsByAddress(unidade);

        return ResponseEntity.ok(pendingEvents);
    }


    @PutMapping("/approveEvent/{id}")
    public ResponseEntity<?> approveEvent(@PathVariable Long id, @RequestParam String email, @RequestParam String password) {
        Optional<UserItem> user = userItemService.getByEmailAndPassword(email, password);

        if (user.isPresent() && user.get().getUserType() == UserItem.UserType.diretor) {
            Optional<EventItem> event = eventItemService.findById(id);
            if (event.isPresent() && event.get().getAddress().equals(user.get().getUnidade())) {
                EventItem eventItem = event.get();
                eventItem.setApproved(true);
                eventItemService.save(eventItem);
                return ResponseEntity.ok("Evento aprovado com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento não encontrado ou não pertence à mesma unidade.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado ou não é um diretor.");
        }
    }

    @RestController
    @RequestMapping("/approvedEvents/{userType}")
    public class EventController {
        @Autowired
        private EventItemService eventItemService;

        @GetMapping
        public ResponseEntity<List<EventItem>> getApprovedEvents(@PathVariable String userType) {
            if ("usuarioComum".equals(userType)) {
                List<EventItem> approvedEvents = eventItemService.getApprovedEventsForCommonUser();
                return ResponseEntity.ok(approvedEvents);
            } else if ("diretor".equals(userType) || "professor".equals(userType) || "aluno".equals(userType)) {
                List<EventItem> approvedEvents = eventItemService.getApprovedEventsForInstitutionalUser();
                return ResponseEntity.ok(approvedEvents);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }



    @GetMapping("/eventUsers/{eventId}")
    public ResponseEntity<List<SubscriptionItem>> getEventUsers(@PathVariable Long eventId) {
        try {
            List<SubscriptionItem> users = subscriptionRepository.findAllByEvent_Id(eventId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}