package kr.neoventureholdings.realword_backend.tag.service;

import java.util.Collection;
import java.util.List;
import kr.neoventureholdings.realword_backend.tag.domains.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacadeTagService {
  private final TagService tagService;

  public List<Tag> saveTags(Collection<Tag> tags) {
    return tagService.saveTags(tags);
  }

  public Tag findTag(String tagValue) {
    return tagService.findByTagValue(tagValue);
  }
}
