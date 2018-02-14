package com.johncnstn.data.service.impl;

import com.johncnstn.data.entity.Entry;
import com.johncnstn.data.entity.User;
import com.johncnstn.data.repository.EntryRepository;
import com.johncnstn.data.repository.UserRepository;
import com.johncnstn.data.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EntryServiceImpl implements EntryService {

    @Qualifier("entryRepository")
    @Autowired
    private EntryRepository entryRepository;
    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Override
    public Entry save(Entry entry, long userId) {
        User user = userRepository.findOne(userId);
        entry.setUser(user);
        return entryRepository.save(entry);
    }
}
