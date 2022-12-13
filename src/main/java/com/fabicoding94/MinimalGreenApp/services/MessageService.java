package com.fabicoding94.MinimalGreenApp.services;

import com.fabicoding94.MinimalGreenApp.entities.user.Message;
import com.fabicoding94.MinimalGreenApp.exceptions.NotFoundException;
import com.fabicoding94.MinimalGreenApp.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    public Message save(Message x) {
        return repository.save(x);
    }

    public List<Message> getAll() {
        return repository.findAll();
    }

    public Message getById(Long id) {

        Optional<Message> message = repository.findById(id);

        if(message.isEmpty())
            throw new NotFoundException("Message Doesn't exist");

        return message.get();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // ---------------------------- Paging --------------------------------

    public Page<Message> getAllAndPaginate(Pageable p){
        Page<Message> pe = repository.findAll(p);
        return pe;
    }

//	---------------------------- Filtering --------------------------

    public Page<Message> getBySenderAndPaginate(Long senderId, Pageable p){
        Page<Message> pe = repository.findBySender(senderId, p);
        return pe;
    }

    public Page<Message> getByReceiverAndPaginate(Long receiverId, Pageable p){
        Page<Message> pe = repository.findByReceiver(receiverId, p);
        return pe;
    }

    public List<Message> getByChat(Long user1Id, Long user2Id) {

        return repository.findByChat(user1Id,user2Id);
    }

}