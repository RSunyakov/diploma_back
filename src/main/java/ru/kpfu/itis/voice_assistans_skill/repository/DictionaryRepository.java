package ru.kpfu.itis.voice_assistans_skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.voice_assistans_skill.entity.TermEntity;

import java.util.Optional;

@Repository
public interface DictionaryRepository  extends JpaRepository<TermEntity, Long> {
    @Query(value = " select t1.* "
            + " from public.term t1 "
            + " join public.term_reference r1 on r1.term1_id = t1.id "
            + " join public.term t2 on r1.term2_id = t2.id "
            + " where t2.term = :termToTranslate and t2.language = :language", nativeQuery = true)
    Optional<TermEntity> findTranslation(String language, String termToTranslate);
}
