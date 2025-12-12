package com.lms.backend.library.mapper;

import com.lms.backend.library.dto.LoanDto;
import com.lms.backend.library.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

    LoanDto toDto(Loan entity);
}
