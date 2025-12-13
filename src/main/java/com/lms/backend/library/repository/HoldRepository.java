package com.lms.backend.library.repository;
import com.lms.backend.library.entity.Hold;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HoldRepository extends JpaRepository<Hold, Long> {

    List<Hold> findByBook_BookIdAndStatusOrderByHoldDateAsc(Long bookId, Hold.HoldStatus status);

    boolean existsByUser_UserIdAndBook_BookIdAndStatus(Long userId, Long bookId, Hold.HoldStatus status);
}

