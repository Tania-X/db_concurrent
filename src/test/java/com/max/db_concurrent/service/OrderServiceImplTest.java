package com.max.db_concurrent.service;

import com.max.db_concurrent.DbConcurrentApplicationTests;
import com.max.db_concurrent.entity.ResultEntity;
import com.max.db_concurrent.entity.dto.OrderDto;
import com.max.db_concurrent.util.GeneratorUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest extends DbConcurrentApplicationTests {

  @Resource
  private OrderService orderService;

  @Test
  public void testOperate() {
    OrderDto orderDto = OrderDto.builder().orderNo(GeneratorUtil.generateOrderNo())
        .orderDetail("detail").orderFlag("A").build();
    ResultEntity resultEntity = orderService.insertAndUpdate(orderDto);
    assert resultEntity.getCode() == 200;
  }

}
