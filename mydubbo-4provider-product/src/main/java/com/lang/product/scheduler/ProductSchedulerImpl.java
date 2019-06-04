package com.lang.product.scheduler;

import com.alibaba.dubbo.config.annotation.Service;
import com.lang.inter.product.ProductScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author faraway
 * @date 2019/5/24 17:52
 * 一个可以点击执行 还能定时执行的任务
 */
@Service
public class ProductSchedulerImpl implements ProductScheduler {

    private static Logger logger = LoggerFactory.getLogger(ProductSchedulerImpl.class);

    @Override
    //@Scheduled(cron = "0/10 * * * * ?")
    public void updProductStatus() {
        logger.info("测试定时任务！");
    }
}
