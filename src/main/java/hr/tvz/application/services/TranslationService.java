package hr.tvz.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TranslationService {

    @Autowired
    private TranslationRepository translationRepository;

    public Translation saveTranslation(Translation translation) {
        return translationRepository.save(translation);
    }

    public Optional<Translation> getTranslationById(Long id) {
        return translationRepository.findById(id);
    }

    public Translation getTranslationByLanguageAndKey(String language, String key) {
        return translationRepository.findByLanguageAndKey(language, key);
    }

    public void deleteTranslation(Long id) {
        translationRepository.deleteById(id);
    }
}
