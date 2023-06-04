package ru.oorzhak.socialnetwork.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private Date createdAt;
    private String body;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinTable(name = "message_from_user",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "from_user_id"))
    private User fromUser;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinTable(name = "message_to_user",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "to_user_id"))
    private User toUser;
}
