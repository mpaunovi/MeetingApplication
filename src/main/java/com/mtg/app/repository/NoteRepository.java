package com.mtg.app.repository;

import com.mtg.app.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    List<Note> findByMeetingId(UUID meetingId);

    @Query("SELECT n FROM Note n WHERE n.contributor.userId = :userId")
    List<Note> findAllByContributorUserId(@Param("userId") UUID userId);
}
