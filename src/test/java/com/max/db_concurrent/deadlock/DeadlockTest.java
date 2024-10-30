package com.max.db_concurrent.deadlock;

import com.max.db_concurrent.DbConcurrentApplicationTests;
import com.max.db_concurrent.entity.dto.OrderDto;
import com.max.db_concurrent.service.OrderService;
import com.max.db_concurrent.util.GeneratorUtil;
import jakarta.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DeadlockTest extends DbConcurrentApplicationTests {

  @Resource
  private OrderService orderService;

  @Test
  public void testDeadlock() {

    int concurrentThread = 2;

    CountDownLatch doneSignal = new CountDownLatch(concurrentThread);

    for (int i = 0; i < concurrentThread; i++) {
      OrderDto orderDto = OrderDto.builder().orderNo(GeneratorUtil.generateOrderNo())
          .orderDetail("detail").orderFlag("A").build();
      new Thread(() -> {
        try {
          orderService.operate(orderDto);
        } catch (Exception e) {
          log.info("order service operate error,", e);
          throw new RuntimeException(e);
        } finally {
          doneSignal.countDown();
        }
      }, "worker-" + i).start();
    }

    try {
      doneSignal.await();
    } catch (InterruptedException e) {
      log.info("done signal await error,", e);
      throw new RuntimeException(e);
    }


  }

}
