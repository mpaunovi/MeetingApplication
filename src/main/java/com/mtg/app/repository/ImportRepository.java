package com.mtg.app.repository;

import com.mtg.app.models.Import;
import com.mtg.app.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImportRepository extends JpaRepository<Import, UUID> {

    List<Import> findByUserUserId(UUID userId);
}
