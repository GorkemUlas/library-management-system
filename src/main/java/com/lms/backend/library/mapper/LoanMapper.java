package com.lms.backend.library.mapper;

import com.lms.backend.library.dto.LoanDto;
import com.lms.backend.library.dto.LoanResponseDto;
import com.lms.backend.library.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * LoanDto → Loan dönüşümü
 * Burada userId ve bookId DTO'da var ama Entity'de User ve Book nesneleri var.
 * Bu yüzden mapping'i service içinde tamamlayacağız.
 */
@Mapper(componentModel = "spring")
public interface LoanMapper {

    /**
     * DTO → Entity dönüşümü
     * User ve Book alanlarını service içinde set edeceğiz.
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    Loan toEntity(LoanDto dto);

    @Mapping(source = "loanId", target = "loanId")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "book.bookId", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "book.author", target = "bookAuthor")
    @Mapping(source = "issueDate", target = "issueDate")
    @Mapping(source = "dueDate", target = "dueDate")
    @Mapping(source = "returnDate", target = "returnDate")
    @Mapping(source = "fineAmount", target = "fineAmount")
    LoanResponseDto toResponseDto(Loan loan);

    List<LoanResponseDto> toResponseDtoList(List<Loan> loans);

    LoanDto toDto(Loan entity);
}
