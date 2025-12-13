package com.lms.backend.library.mapper;

import com.lms.backend.library.dto.HoldDto;
import com.lms.backend.library.dto.HoldResponseDto;
import com.lms.backend.library.entity.Hold;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HoldMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    Hold toEntity(HoldDto dto);

    @Mapping(source = "holdId", target = "holdId")
    @Mapping(source = "user.userId", target = "userId")        // ✅ düzeltildi
    @Mapping(source = "book.bookId", target = "bookId")            // Book id alanına göre ayarla
    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "book.author", target = "bookAuthor")
    @Mapping(source = "holdDate", target = "holdDate")
    @Mapping(source = "status", target = "status")

    HoldResponseDto toResponseDto(Hold hold);
    List<HoldResponseDto> toResponseDtoList(List<Hold> holds);

    //HoldDto toDto(Hold entity);
}