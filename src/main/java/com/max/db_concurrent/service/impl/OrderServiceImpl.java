package com.max.db_concurrent.service.impl;

import com.max.db_concurrent.dao.OrderDao;
import com.max.db_concurrent.entity.ResultEntity;
import com.max.db_concurrent.entity.bean.OrderBean;
import com.max.db_concurrent.entity.dto.OrderDto;
import com.max.db_concurrent.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

  @Resource
  private OrderDao orderDao;


  @Override
  @Transactional
  public ResultEntity operate(OrderDto orderDto) {
    OrderBean orderBean = OrderBean.builder().orderNo(orderDto.getOrderNo())
        .orderDetail(orderDto.getOrderDetail()).orderFlag(
            orderDto.getOrderFlag()).build();
    int affectedRows = orderDao.insertOrder(orderBean);
    if (affectedRows != 1) {
      log.error("order {} insert error.", orderBean);
      return new ResultEntity(9999, null, "order insert error");
    }
    String orderNo = orderDto.getOrderNo();
    String newOrderFlag = "B";
    affectedRows = orderDao.updateFlagByOrderNo(orderNo, newOrderFlag);
    if (affectedRows != 1) {
      log.error("order {} update error.", orderBean);
      return new ResultEntity(9998, null, "order update error");
    }
    log.info("order {} operate success, order flag changed to {}.", orderBean, newOrderFlag);
    return new ResultEntity(200, null, null);
  }
}
