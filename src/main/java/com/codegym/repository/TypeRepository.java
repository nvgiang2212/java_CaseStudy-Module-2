package com.codegym.repository;

import com.codegym.model.Note;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TypeRepository extends PagingAndSortingRepository<Note, Long> {
}
