package ru.oorzhak.socialnetwork.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    private Long id;
    @CreationTimestamp
    private Date createdAt;
    @NotNull
    private String senderUsername;
    @NotNull
    private String receiverUsername;
    @NotBlank
    private String body;
}
