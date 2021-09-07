package com.javafanatics.jibberjabber.notification;
import com.javafanatics.jibberjabber.account.User;

public interface NotificationService {
    void sendNotification(User user, String jibber);
}
