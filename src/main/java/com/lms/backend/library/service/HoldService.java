package com.lms.backend.library.service;

import com.lms.backend.library.entity.Book;
import com.lms.backend.library.entity.User;
import com.lms.backend.library.mapper.HoldMapper;
import com.lms.backend.library.repository.BookRepository;
import com.lms.backend.library.repository.UserRepository;
import com.lms.backend.library.dto.HoldDto;
import com.lms.backend.library.entity.Hold;
import com.lms.backend.library.repository.HoldRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoldService {
    private final HoldRepository holdRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final HoldMapper holdMapper;

     public HoldService(HoldRepository holdRepository, UserRepository userRepository, BookRepository bookRepository, HoldMapper holdMapper) {
        this.holdRepository = holdRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.holdMapper = holdMapper;
    }

    public Hold createHold(HoldDto dto) {

        Hold hold = holdMapper.toEntity(dto);

        hold.setUser(
                userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );

        hold.setBook(
                bookRepository.findById(dto.getBookId())
                        .orElseThrow(() -> new RuntimeException("Book not found"))
        );

        return holdRepository.save(hold);
    }

    /*public Hold addHold(Hold hold) {
        return holdRepository.save(hold);
    }*/

    /*public Hold createHold(HoldDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Hold hold = new Hold();
        hold.setUser(user);
        hold.setBook(book);

        return holdRepository.save(hold);
    }*/

    public Hold getHold(Long id) {
        return holdRepository.findById(id).orElse(null);
    }

    public List<Hold> getAllHolds() {
        return holdRepository.findAll();
    }

    public void deleteHold(Long id) {
        holdRepository.deleteById(id);
    }
}