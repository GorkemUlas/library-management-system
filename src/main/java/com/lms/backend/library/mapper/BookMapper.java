package com.lms.backend.library.mapper;

import com.lms.backend.library.dto.BookDto;
import com.lms.backend.library.entity.Book;
import org.mapstruct.Mapper;

/**
 * @Mapper → MapStruct'a bu interface'in bir mapper olduğunu söyler.
 * componentModel = "spring" → Spring bu mapper'ı otomatik bean olarak yönetir.
 * Böylece service içinde constructor injection ile kullanabilirsin.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    /**
     * DTO → Entity dönüşümü
     * MapStruct bunu otomatik olarak implement eder.
     */
    Book toEntity(BookDto dto);

    /**
     * Entity → DTO dönüşümü
     * Şimdilik kullanmasan bile ileride çok işine yarar.
     */
    BookDto toDto(Book entity);
}