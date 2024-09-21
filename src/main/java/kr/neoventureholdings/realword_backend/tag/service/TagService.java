package kr.neoventureholdings.realword_backend.tag.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kr.neoventureholdings.realword_backend.exception.common.NoSuchElementException;
import kr.neoventureholdings.realword_backend.exception.common.UniqueConstraintViolationException;
import kr.neoventureholdings.realword_backend.tag.domains.Tag;
import kr.neoventureholdings.realword_backend.tag.repository.TagMapRepository;
import kr.neoventureholdings.realword_backend.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {
  private final TagRepository tagRepository;
  private final TagMapRepository tagMapRepository;

  @Transactional
  public List<Tag> saveTags(Collection<Tag> tags) {
    List<Tag> results = new ArrayList<>();

    for (Tag tag : tags) {
      try {
        Tag tagEntity = tagRepository.findByValue(tag.getValue())
            .orElseGet(() -> tagRepository.save(tag));
        results.add(tagEntity);
      } catch (UniqueConstraintViolationException e) {
        log.debug("이미 존재하는 tag {}", tag.getValue());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    return results;
  }

  public Tag findByTagValue(String tagValue) {
    return tagRepository.findByValue(tagValue)
        .orElseThrow(() -> new NoSuchElementException("no such tag"));
  }

  public List<Tag> getTags() {
    return tagRepository.findAll();
  }
}
