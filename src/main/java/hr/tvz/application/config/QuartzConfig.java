package hr.tvz.application.config;

import hr.tvz.application.job.InactiveNewsJob;
import org.quartz.*;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail inactiveNewsJobDetail() {
        return JobBuilder.newJob(InactiveNewsJob.class)
                .withIdentity("inactiveNewsJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger inactiveNewsJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(1)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(inactiveNewsJobDetail())
                .withIdentity("inactiveNewsTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(Trigger inactiveNewsJobTrigger, JobDetail inactiveNewsJobDetail) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springBeanJobFactory());
        factory.setJobDetails(inactiveNewsJobDetail);
        factory.setTriggers(inactiveNewsJobTrigger);
        return factory;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new AutowiringSpringBeanJobFactory(beanFactory);
    }

    private static class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory {
        private final AutowireCapableBeanFactory beanFactory;

        AutowiringSpringBeanJobFactory(AutowireCapableBeanFactory beanFactory) {
            this.beanFactory = beanFactory;
        }

        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            final Object job = super.createJobInstance(bundle);
            beanFactory.autowireBean(job);
            return job;
        }
    }

    @Autowired
    private AutowireCapableBeanFactory beanFactory;
}
