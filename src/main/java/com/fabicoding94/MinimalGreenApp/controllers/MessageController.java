package com.fabicoding94.MinimalGreenApp.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fabicoding94.MinimalGreenApp.services.*;
import com.fabicoding94.MinimalGreenApp.entities.post.*;
import com.fabicoding94.MinimalGreenApp.entities.user.*;
import com.fabicoding94.MinimalGreenApp.DTO.*;



@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:4200/")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

//---------------------------- Get ---------------------------------

    @GetMapping
    public ResponseEntity<Page<Message>> getMessageList(Pageable p) {

        Page<Message> res = messageService.getAllAndPaginate(p);

        if (res.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public Message getMessageById(@PathVariable("id") Long id) {
        return messageService.getById(id);
    }

    @GetMapping("/sender_id/{id}")
    public Page<Message> getBySenderAndPaginate(@PathVariable("id") Long id, Pageable p) {
        return messageService.getBySenderAndPaginate(id, p);
    }

    @GetMapping("/receiver_id/{id}")
    public Page<Message> getByReceiverAndPaginate(@PathVariable("id") Long id, Pageable p) {
        return messageService.getByReceiverAndPaginate(id, p);
    }

    @GetMapping("/chat/{id}/{id2}")
    public List<Message> getMessagesByChat(
            @PathVariable("id") Long user1Id,
            @PathVariable("id2") Long user2Id
    ){
        return messageService.getByChat(user1Id,user2Id);
    }
//---------------------------- Post --------------------------------

    @PostMapping("")
    public Message saveMessage( @RequestBody MessageDTO dto ) {

        Message message = Message.builder()
                .text(dto.getText())
                .sender(userService.getById(dto.getSenderId()))
                .receiver(userService.getById(dto.getReceiverId()))
                .date(LocalDateTime.now())
                .edited(false)
                .build();

        return messageService.save(message);
    }

    //---------------------------- Put ---------------------------------

    @PutMapping("/{id}")
    public Message updateMessage(@PathVariable("id") Long id, @RequestBody String text ) {

        Message message = messageService.getById(id);
        message.setText(text);
        message.setEdited(true);

        return messageService.save(message);
    }

    // -------------------------- Delete -------------------------------

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public String deleteMessageById(@PathVariable("id") Long id) {
        messageService.deleteById(id);
        return "message deleted successfully";
    }

}
