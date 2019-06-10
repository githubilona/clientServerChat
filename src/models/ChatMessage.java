package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage implements Serializable {
    private String message;
    private User sender;
    private User receiver;
    private LocalDateTime date;
    private transient DateTimeFormatter formatter;

    public ChatMessage(String message, User sender, User receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.date = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date.format(formatter);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
