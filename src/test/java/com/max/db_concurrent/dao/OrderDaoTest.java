package com.max.db_concurrent.dao;

import com.max.db_concurrent.DbConcurrentApplicationTests;
import com.max.db_concurrent.entity.bean.OrderBean;
import com.max.db_concurrent.util.GeneratorUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

public class OrderDaoTest extends DbConcurrentApplicationTests {

  @Resource
  private OrderDao orderDao;

  @Test
  public void testInsert() {
    OrderBean orderBean = OrderBean.builder().orderNo(GeneratorUtil.generateOrderNo())
        .orderDetail("detail").orderFlag("A").build();
    int affectedRows = orderDao.insertOrder(orderBean);
    assert affectedRows == 1;
  }

  @Test
  public void testQuery() {
    OrderBean orderBean = orderDao.queryOrderByOrderNo("DW5U93PK90DB");
    System.out.println(orderBean);
  }

  @Test
  public void testUpdate() {
    int affectedRows = orderDao.updateFlagByOrderNo("DW5U93PK90DB", "B");
    assert affectedRows == 1;
  }

}
