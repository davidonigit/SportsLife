package com.grupo3.sportslife_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo3.sportslife_app.model.Notification;
import com.grupo3.sportslife_app.model.User;

@Repository
public interface NotificationRepository extends JpaRepository <Notification, Long>{
    List<Notification> findByReceiver(User receiver);
}
