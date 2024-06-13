package hr.tvz.application.endpoint;


import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.Nonnull;
import hr.tvz.application.dto.NewsDTO;
import hr.tvz.application.services.NewsService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;


import java.util.List;

@Endpoint
public class NewsEndpoint {

    private final NewsService newsService;

    public NewsEndpoint(NewsService newsService) {
        this.newsService = newsService;
    }

    @PermitAll
    @Nonnull
    public List<NewsDTO> getActiveNews() {
        return newsService.getActiveNews();
    }

    @PermitAll
    @Nonnull
    public List<NewsDTO> getAllNews() {
        return newsService.getAllNews();
    }

    @RolesAllowed("ROLE_ADMIN")
    public NewsDTO getNewsById(@Nonnull Long id) {
        return newsService.getNewsById(id);
    }

    @RolesAllowed("ROLE_ADMIN")
    public NewsDTO createNews(@Nonnull NewsDTO newsDTO) {
        return newsService.createNews(newsDTO);
    }

    @RolesAllowed("ROLE_ADMIN")
    public NewsDTO updateNews(@Nonnull Long id, @Nonnull NewsDTO newsDTO) {
        return newsService.updateNews(id, newsDTO);
    }

    @RolesAllowed("ROLE_ADMIN")
    public boolean deleteNews(@Nonnull Long id) {
        return newsService.deleteNews(id);
    }
}
