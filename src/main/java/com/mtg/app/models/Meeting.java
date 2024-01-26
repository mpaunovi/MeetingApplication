package com.mtg.app.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "meeting_date_time", nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Note> notes;

    public Meeting(String title, LocalDateTime dateTime, User organizer, String description, List<Note> notes) {
        this.title = title;
        this.dateTime = dateTime;
        this.organizer = organizer;
    }
}

