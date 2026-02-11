package com.dileep;

enum NotificationType {
  EMAIL,
  SMS,
}

interface Notification {
  void sendMessage(String message);
}

class EmailNotification implements Notification {
  public void sendMessage(String message) {
    System.out.println("Send email.");
  }
}

class SmsNotification implements Notification {
  public void sendMessage(String message) {
    System.out.println("Send sms.");
  }
}

class NotificationFactory {
  public static Notification getNotification(NotificationType notificationType) {
    return switch (notificationType) {
      case EMAIL -> new EmailNotification();
      case SMS -> new SmsNotification();
    };
  }
}

public class ExampleFactory {
  public static void main(String[] args) {
    String msg = "data processed";
    Notification notification = NotificationFactory.getNotification(NotificationType.EMAIL);
    notification.sendMessage(msg);
  }
}
