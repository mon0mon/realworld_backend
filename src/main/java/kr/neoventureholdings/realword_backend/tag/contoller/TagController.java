package kr.neoventureholdings.realword_backend.tag.contoller;

import kr.neoventureholdings.realword_backend.common.dto.CommonResponseDto;
import kr.neoventureholdings.realword_backend.tag.domains.Tag;
import kr.neoventureholdings.realword_backend.tag.service.FacadeTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
  private final FacadeTagService facadeTagService;

  @GetMapping
  public ResponseEntity<CommonResponseDto> getTags() {
    return ResponseEntity
        .ok(CommonResponseDto.builder()
            .tags(
                facadeTagService.getTags()
                    .stream()
                    .map(Tag::getValue)
                    .toList()
            )
            .build()
        );
  }
}
