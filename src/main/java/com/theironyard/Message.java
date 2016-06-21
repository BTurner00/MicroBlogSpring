package com.theironyard;

import javax.persistence.*;

/**
 * Created by Ben on 6/20/16.
 */

@Entity
@Table(name="messages")
public class Message {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable=false)
    String message;

    public Message(String message) {

        this.message = message;
    }

    public Message() {
    }

    public Message(int id, String message) {
        this.id = id;
        this.message = message;
    }
}
