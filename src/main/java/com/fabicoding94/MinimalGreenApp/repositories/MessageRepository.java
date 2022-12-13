package com.fabicoding94.MinimalGreenApp.repositories;

import com.fabicoding94.MinimalGreenApp.entities.user.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query( value = "SELECT m FROM Message m WHERE m.sender.id = :id" )
    Page<Message> findBySender(Long id, Pageable p);

    @Query( value = "SELECT m FROM Message m WHERE m.receiver.id = :id" )
    Page<Message> findByReceiver(Long id, Pageable p);

    @Query( value = "SELECT m FROM Message m WHERE"
            + " (m.receiver.id = :user1Id OR m.receiver.id = :user2Id)"
            + " AND (m.sender.id = :user1Id OR m.sender.id = :user2Id)"
            + " ORDER BY m.date" )
    List<Message> findByChat(Long user1Id, Long user2Id);

}
