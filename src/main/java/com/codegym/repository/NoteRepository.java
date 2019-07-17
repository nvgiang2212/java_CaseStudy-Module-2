package com.codegym.repository;

import com.codegym.model.Note;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;

public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {
    Page<Note> findAllByTitleContainingOrContentContaining(
            String title, String content, Pageable pageable);
}
