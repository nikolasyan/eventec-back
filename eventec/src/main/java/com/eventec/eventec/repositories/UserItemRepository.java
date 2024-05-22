package com.eventec.eventec.repositories;

import com.eventec.eventec.models.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, Long> {

    Optional<UserItem> findUserByEmailAndPassword(String email, String password);

    Optional<UserItem> findUserByEmailOrEmailInstitucionalAndEmailValidationType(String email, String emailInstitucional, UserItem.EmailValidationType emailValidationType);
}
