package org.clara.translate.app.repository;

import org.clara.translate.app.domain.Combination;
import org.clara.translate.app.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CombinationRepository extends JpaRepository<Combination, Long> {

    Optional<Combination> findByOriginLanguageAndTargetLanguage(Language originLanguage, Language targetLanguage);
}
