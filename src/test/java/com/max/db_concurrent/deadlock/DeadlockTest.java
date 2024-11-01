package com.max.db_concurrent.deadlock;

import com.max.db_concurrent.DbConcurrentApplicationTests;
import com.max.db_concurrent.dao.OrderDao;
import com.max.db_concurrent.entity.bean.OrderBean;
import com.max.db_concurrent.entity.dto.OrderDto;
import com.max.db_concurrent.service.OrderService;
import com.max.db_concurrent.util.GeneratorUtil;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class DeadlockTest extends DbConcurrentApplicationTests {

  @Resource
  private OrderService orderService;

  @Resource
  private JdbcTemplate jdbcTemplate;

  @Resource
  private OrderDao orderDao;

  @Test
  public void testInsertAndUpdateDeadlock() {

    jdbcTemplate.execute("truncate table t_order;");
    jdbcTemplate.execute("create index order_no_index on t_order (order_no)");

    int concurrentThread = 20;

    CountDownLatch doneSignal = new CountDownLatch(concurrentThread);

    for (int i = 0; i < concurrentThread; i++) {
      OrderDto orderDto = OrderDto.builder().orderNo(GeneratorUtil.generateOrderNo())
          .orderDetail("detail").orderFlag("A").build();
      new Thread(() -> {
        try {
          orderService.operate(orderDto);
        } catch (Exception e) {
          log.error("order service operate error,", e);
        } finally {
          doneSignal.countDown();
        }
      }, "worker-" + i).start();
    }

    try {
      doneSignal.await();
    } catch (InterruptedException e) {
      log.error("done signal await error,", e);
    }

    jdbcTemplate.execute("drop index order_no_index on t_order;");

  }

  @Test
  public void testUpdateDeadlock() {

    jdbcTemplate.execute("truncate table t_order;");
    jdbcTemplate.execute("create index order_no_index on t_order (order_no)");

    int bulkInsertOrders = 10;
    for (int i = 0; i < bulkInsertOrders; i++) {
      OrderBean orderBean = OrderBean.builder().orderNo(GeneratorUtil.generateOrderNo())
          .orderDetail("detail").orderFlag("A").build();
      orderDao.insertOrder(orderBean);
    }

    int concurrentThread = 2;

    CountDownLatch doneSignal = new CountDownLatch(concurrentThread);

    for (int i = 0; i < concurrentThread; i++) {
      List<String> orderNoList = new ArrayList<>(
          orderDao.queryAll().stream().map(OrderBean::getOrderNo).toList());
      // 考虑打乱顺序
      Collections.shuffle(orderNoList);
      new Thread(() -> {
        try {
          orderService.batchUpdate(orderNoList);
        } catch (Exception e) {
          log.error("order dao update error,", e);
        } finally {
          doneSignal.countDown();
        }
      }, "worker-" + i).start();
    }

    try {
      doneSignal.await();
    } catch (InterruptedException e) {
      log.error("done signal await error,", e);
    }

    jdbcTemplate.execute("drop index order_no_index on t_order;");

  }

}
