package com.johncnstn.data.repository;

import com.johncnstn.data.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("entryRepository")
public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findAllByUserId(long userId);
}
