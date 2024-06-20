package hr.tvz.test.data;

import hr.tvz.application.data.News;
import hr.tvz.application.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class NewsRepositoryTest {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void testFindById() {
        News news = new News();
        news.setTitle("Test News");
        news = newsRepository.save(news);

        Optional<News> foundNews = newsRepository.findById(news.getId());

        assertTrue(foundNews.isPresent());
        assertEquals("Test News", foundNews.get().getTitle());
    }

    @Test
    public void testFindByActiveTrue() {
        News news = new News();
        news.setActive(true);
        newsRepository.save(news);

        List<News> activeNews = newsRepository.findByActiveTrue();

        assertEquals(1, activeNews.size());
        assertTrue(activeNews.get(0).isActive());
    }
}
