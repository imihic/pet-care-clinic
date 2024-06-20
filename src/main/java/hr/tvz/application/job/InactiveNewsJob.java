package hr.tvz.application.job;

import hr.tvz.application.data.News;
import hr.tvz.application.repository.NewsRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class InactiveNewsJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(InactiveNewsJob.class);

    @Autowired
    private NewsRepository newsRepository;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("InactiveNewsJob started at {}", new Date());

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        Date thirtyDaysAgoDate = Date.from(thirtyDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<News> oldNews = newsRepository.findByPublishDateBeforeAndActiveTrue(thirtyDaysAgoDate);
        int totalNewsAnalyzed = oldNews.size();
        int totalNewsChanged = 0;

        for (News news : oldNews) {
            if (news.isActive()) {
                news.setActive(false);
                newsRepository.save(news);
                totalNewsChanged++;
            }
        }

        logger.info("InactiveNewsJob completed at {}", new Date());
        logger.info("Total news analyzed: {}", totalNewsAnalyzed);
        logger.info("Total news changed: {}", totalNewsChanged);
    }
}
