package hr.tvz.application.job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SchedulerStarter implements CommandLineRunner {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        try {
            if (!scheduler.isStarted()) {
                scheduler.start();
                System.out.println("Scheduler started.");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}