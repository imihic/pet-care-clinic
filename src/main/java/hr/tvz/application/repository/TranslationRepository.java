package hr.tvz.application.repository;

import hr.tvz.application.data.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Translation findByLanguageAndKey(String language, String key);
}
