package com.eventec.eventec.services;

import com.eventec.eventec.models.UserItem;
import com.eventec.eventec.repositories.UserItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserItemService{
    @Autowired
    private UserItemRepository userItemRepository;

    public Optional<UserItem> getByEmailAndPassword(String email, String password) {
        return userItemRepository.findUserByEmailAndPassword(email, password);
    }
    public Iterable<UserItem> getAll() { return userItemRepository.findAll(); }

    public UserItem save(UserItem userItem) {
        return userItemRepository.save(userItem);
    }

    public UserItem createUserItem(UserItem userItem){
        return userItemRepository.save(userItem);
    }
}
