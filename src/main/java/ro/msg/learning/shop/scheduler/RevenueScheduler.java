package ro.msg.learning.shop.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.service.RevenueService;

@Component
public class RevenueScheduler {

    @Autowired
    private RevenueService revenueService;

    @Scheduled(cron = "${cron.save.revenues}")
    public void saveRevenue() {
        revenueService.saveRevenuesForToday();
    }

}
