package hr.tvz.test.endpoints;

import hr.tvz.application.dto.NewsDTO;
import hr.tvz.application.endpoint.NewsEndpoint;
import hr.tvz.application.services.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsEndpointTest {

    @Mock
    private NewsService newsService;

    @InjectMocks
    private NewsEndpoint newsEndpoint;

    @Test
    public void testGetActiveNews() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("Active News");
        when(newsService.getActiveNews()).thenReturn(Collections.singletonList(newsDTO));

        List<NewsDTO> result = newsEndpoint.getActiveNews();

        assertEquals(1, result.size());
        assertEquals("Active News", result.get(0).getTitle());
        verify(newsService, times(1)).getActiveNews();
    }

    @Test
    public void testGetAllNews() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("All News");
        when(newsService.getAllNews()).thenReturn(Collections.singletonList(newsDTO));

        List<NewsDTO> result = newsEndpoint.getAllNews();

        assertEquals(1, result.size());
        assertEquals("All News", result.get(0).getTitle());
        verify(newsService, times(1)).getAllNews();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetNewsById() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(1L);
        newsDTO.setTitle("News Title");
        when(newsService.getNewsById(1L)).thenReturn(newsDTO);

        NewsDTO result = newsEndpoint.getNewsById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("News Title", result.getTitle());
        verify(newsService, times(1)).getNewsById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateNews() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("New News");
        when(newsService.createNews(any(NewsDTO.class))).thenReturn(newsDTO);

        NewsDTO result = newsEndpoint.createNews(newsDTO);

        assertNotNull(result);
        assertEquals("New News", result.getTitle());
        verify(newsService, times(1)).createNews(any(NewsDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateNews() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(1L);
        newsDTO.setTitle("Updated News");
        when(newsService.updateNews(eq(1L), any(NewsDTO.class))).thenReturn(newsDTO);

        NewsDTO result = newsEndpoint.updateNews(1L, newsDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated News", result.getTitle());
        verify(newsService, times(1)).updateNews(eq(1L), any(NewsDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteNews() {
        when(newsService.deleteNews(1L)).thenReturn(true);

        boolean result = newsEndpoint.deleteNews(1L);

        assertTrue(result);
        verify(newsService, times(1)).deleteNews(1L);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetNewsById_Unauthorized() {
        assertThrows(AccessDeniedException.class, () -> {
            newsEndpoint.getNewsById(1L);
        });
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testCreateNews_Unauthorized() {
        NewsDTO newsDTO = new NewsDTO();
        assertThrows(AccessDeniedException.class, () -> {
            newsEndpoint.createNews(newsDTO);
        });
    }
}
