package com.klemmy.novel.ghostwriter.service;


import com.klemmy.novelideas.api.BookDto;
import com.klemmy.novelideas.api.BookState;
import com.klemmy.novelideas.api.CharacterGenderDto;
import com.klemmy.novelideas.client.NovelIdeasClient;
import com.klemmy.novelideas.client3.NovelIdeasClient3;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@ImportAutoConfiguration
@AllArgsConstructor
public class GhostWriterService {

  @Qualifier("novelIdeaClientConfig")
  private final NovelIdeasClient novelIdeasClient;
  private final NovelIdeasClient3 novelIdeasClient3;

  public Page<BookDto> loadAllBooks(String queryTitle, LocalDateTime startDate, LocalDateTime endDate,
                                    Set<BookState> state, Pageable pageable) {
    return novelIdeasClient.getAllBooks(queryTitle,
        startDate,
        endDate,
        state,
        String.valueOf(pageable.getPageNumber()),
        String.valueOf(pageable.getPageSize()),
        pageable.getSort()).getBody();
  }

  public BookDto getBook(Integer id) {
    return novelIdeasClient.getBook(id).getBody();
  }
//  public BookDto getBook3(Integer id) {
//    return novelIdeasClient3.getBooKById(id);
//  }

  public List<CharacterGenderDto> getAllGenders() {
    return novelIdeasClient.getAllGenders().getBody();
  }
}
