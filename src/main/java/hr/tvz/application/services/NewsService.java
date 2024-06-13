package hr.tvz.application.services;

import hr.tvz.application.data.News;
import hr.tvz.application.dto.NewsDTO;
import hr.tvz.application.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<NewsDTO> getActiveNews() {
        return newsRepository.findByActiveTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NewsDTO getNewsById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        return Objects.requireNonNull(news.map(this::convertToDTO).orElse(null));
    }

    public NewsDTO createNews(NewsDTO newsDTO) {
        News news = convertToEntity(newsDTO);
        news = newsRepository.save(news);
        return convertToDTO(news);
    }

    public NewsDTO updateNews(Long id, NewsDTO newsDTO) {
        if (newsRepository.existsById(id)) {
            News news = convertToEntity(newsDTO);
            news.setId(id);
            news = newsRepository.save(news);
            return convertToDTO(news);
        }
        return null;
    }

    public boolean deleteNews(Long id) {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private NewsDTO convertToDTO(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setContent(news.getContent());
        return newsDTO;
    }

    private News convertToEntity(NewsDTO newsDTO) {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        return news;
    }
}
