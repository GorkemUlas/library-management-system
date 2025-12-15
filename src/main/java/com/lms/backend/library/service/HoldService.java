package com.lms.backend.library.service;

import com.lms.backend.library.dto.HoldResponseDto;
import com.lms.backend.library.entity.Book;
import com.lms.backend.library.entity.User;
import com.lms.backend.library.entity.Hold;

import com.lms.backend.library.mapper.HoldMapper;
import com.lms.backend.library.repository.BookRepository;
import com.lms.backend.library.repository.UserRepository;
import com.lms.backend.library.dto.HoldDto;
import com.lms.backend.library.entity.Hold;
import com.lms.backend.library.repository.HoldRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HoldService {


    private final HoldRepository holdRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final HoldMapper holdMapper;

    public HoldService(HoldRepository holdRepository, UserRepository userRepository,
                       BookRepository bookRepository, HoldMapper holdMapper) {
        this.holdRepository = holdRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.holdMapper = holdMapper;
    }

    public HoldResponseDto createHold(HoldDto dto) {

        if (holdRepository.existsByUser_UserIdAndBook_BookIdAndStatus(dto.getUserId(), dto.getBookId(), Hold.HoldStatus.PENDING)) {
            throw new RuntimeException("You already have a pending hold for this book");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Hold hold = new Hold();
        hold.setUser(user);
        hold.setBook(book);
        hold.setHoldDate(LocalDate.now());
        hold.setStatus(Hold.HoldStatus.PENDING);

        Hold saved = holdRepository.save(hold);
        return holdMapper.toResponseDto(saved);
    }

    public List<HoldResponseDto> getAllHolds() {
        List<Hold> holds = holdRepository.findAll();
        return holdMapper.toResponseDtoList(holds);
    }

    public List<HoldResponseDto> getHoldsByUser(Long userId) {
        List<Hold> holds = holdRepository.findByUser_UserId(userId);
        return holdMapper.toResponseDtoList(holds);
    }


}
