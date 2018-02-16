package com.johncnstn.controller;



import com.johncnstn.data.detail.CustomUserDetail;
import com.johncnstn.data.entity.Entry;
import com.johncnstn.data.entity.User;
import com.johncnstn.data.repository.EntryRepository;
import com.johncnstn.data.repository.UserRepository;
import com.johncnstn.data.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EntriesController {

    @Qualifier("entryRepository")
    @Autowired
    private EntryRepository entryRepository;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntryService entryService;

    @GetMapping("/entries")
    public List<Entry> getAllEntries() {
        return entryRepository.findAllByUserId(getPrincipal().getId());
    }

    @PostMapping("/entries")
    public Entry createEntry(@Valid @RequestBody Entry entry) {
        return entryService.save(entry, getPrincipal().getId());
    }

    @GetMapping("/entries/{id}")
    public ResponseEntity<Entry> getEntryById(@PathVariable(value = "id") Long entryId) {

        Entry entry = entryRepository.findOne(entryId);
        if(entry == null || (getPrincipal().getId() != entry.getUser().getId())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(entry);
    }

    @PutMapping("/entries/{id}")
    public ResponseEntity<Entry> updateEntry(@PathVariable(value = "id") Long entryId,
                                           @Valid @RequestBody Entry entryDetails) {
        Entry entry = entryRepository.findOne(entryId);
        if(entry == null || (getPrincipal().getId() != entry.getUser().getId())) {
            return ResponseEntity.notFound().build();
        }

        entry.setDistance(entryDetails.getDistance());
        entry.setRaceTime(entryDetails.getRaceTime());
        entry.setStartRaceDateTime(entryDetails.getStartRaceDateTime());

        Entry updatedEntry = entryRepository.save(entry);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Entry> deleteEntry(@PathVariable(value = "id") Long entryId) {
        Entry entry = entryRepository.findOne(entryId);
        if(entry == null || (getPrincipal().getId() != entry.getUser().getId())) {
            return ResponseEntity.notFound().build();
        }

        entryRepository.delete(entry);
        return ResponseEntity.ok().build();
    }

    private User getPrincipal(){
        return userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
