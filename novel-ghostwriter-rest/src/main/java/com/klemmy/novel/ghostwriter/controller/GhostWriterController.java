package com.klemmy.novel.ghostwriter.controller;

import com.klemmy.novel.ghostwriter.service.GhostWriterService;
import com.klemmy.novelideas.api.BookDto;
import com.klemmy.novelideas.api.BookState;
import com.klemmy.novelideas.api.CharacterGenderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "GhostWriter", description = "A book represents a novel or script")
public class GhostWriterController {

  private final GhostWriterService ghostWriterService;

  @GetMapping("/")
  @Operation(summary = "Get all Books", description = "List all the Books that represents a novel or script")
  @ApiResponse(responseCode = "400", description = "Invalid")
  public Page<BookDto> getAll(@RequestParam(required = false) String queryTitle,
                              @RequestParam(required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                              @RequestParam(required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                              @RequestParam(required = false) Set<BookState> state,
                              @ParameterObject @PageableDefault(size = 20, sort = "name") Pageable pageable) throws UnsupportedEncodingException {
    return ghostWriterService.loadAllBooks(queryTitle, startDate, endDate, state, pageable);
  }

  @Operation(summary = "Get a Book", description = "Get a single book that represents a novel or script")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "Invalid"),
      @ApiResponse(responseCode = "404", description = "Not Found")})
  public BookDto getBook(@PathVariable Integer id) {
    return ghostWriterService.getBook(id);
  }

  @GetMapping("/genders")
  @Operation(summary = "List all the genders", description = "Get all the genders")
  @ApiResponse(responseCode = "400", description = "Invalid")
  public List<CharacterGenderDto> getAllGenders() {
    return ghostWriterService.getAllGenders();
  }

}
