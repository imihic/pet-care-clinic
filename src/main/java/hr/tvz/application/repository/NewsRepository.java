package hr.tvz.application.repository;

import hr.tvz.application.data.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    // get news by id
    Optional<News> findById(Long id);

    //get list of active news
    List<News> findByActiveTrue();

    List<News> findByPublishDateBeforeAndActiveTrue(Date date);

}
