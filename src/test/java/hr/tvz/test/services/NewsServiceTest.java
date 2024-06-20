package hr.tvz.test.services;

import hr.tvz.application.data.News;
import hr.tvz.application.dto.NewsDTO;
import hr.tvz.application.repository.NewsRepository;
import hr.tvz.application.services.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsService newsService;

    @Test
    public void testGetActiveNews() {
        News news = new News();
        when(newsRepository.findByActiveTrue()).thenReturn(Collections.singletonList(news));

        List<NewsDTO> activeNews = newsService.getActiveNews();

        assertEquals(1, activeNews.size());
        verify(newsRepository, times(1)).findByActiveTrue();
    }

    @Test
    public void testGetAllNews() {
        News news = new News();
        when(newsRepository.findAll()).thenReturn(Collections.singletonList(news));

        List<NewsDTO> allNews = newsService.getAllNews();

        assertEquals(1, allNews.size());
        verify(newsRepository, times(1)).findAll();
    }

    @Test
    public void testGetNewsById() {
        News news = new News();
        news.setId(1L);
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));

        NewsDTO result = newsService.getNewsById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(newsRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateNews() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("Title");
        newsDTO.setContent("Content");

        News news = new News();
        when(newsRepository.save(any(News.class))).thenReturn(news);

        NewsDTO result = newsService.createNews(newsDTO);

        assertNotNull(result);
        verify(newsRepository, times(1)).save(any(News.class));
    }

    @Test
    public void testUpdateNews() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("Updated Title");
        newsDTO.setContent("Updated Content");

        News news = new News();
        when(newsRepository.existsById(1L)).thenReturn(true);
        when(newsRepository.save(any(News.class))).thenReturn(news);

        NewsDTO result = newsService.updateNews(1L, newsDTO);

        assertNotNull(result);
        verify(newsRepository, times(1)).existsById(1L);
        verify(newsRepository, times(1)).save(any(News.class));
    }

    @Test
    public void testUpdateNews_NotFound() {
        NewsDTO newsDTO = new NewsDTO();
        when(newsRepository.existsById(1L)).thenReturn(false);

        NewsDTO result = newsService.updateNews(1L, newsDTO);

        assertNull(result);
        verify(newsRepository, times(1)).existsById(1L);
        verify(newsRepository, times(0)).save(any(News.class));
    }

    @Test
    public void testDeleteNews() {
        when(newsRepository.existsById(1L)).thenReturn(true);

        boolean result = newsService.deleteNews(1L);

        assertTrue(result);
        verify(newsRepository, times(1)).existsById(1L);
        verify(newsRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteNews_NotFound() {
        when(newsRepository.existsById(1L)).thenReturn(false);

        boolean result = newsService.deleteNews(1L);

        assertFalse(result);
        verify(newsRepository, times(1)).existsById(1L);
        verify(newsRepository, times(0)).deleteById(1L);
    }
}
