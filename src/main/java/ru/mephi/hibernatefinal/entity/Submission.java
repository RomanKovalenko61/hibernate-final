package ru.mephi.hibernatefinal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User user;
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
    @Column(name = "content")
    private String content;
    @Column(name = "score")
    private Integer score;
    @Column(name = "feedback")
    private String feedback;
}
