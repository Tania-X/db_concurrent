package com.max.db_concurrent.service;

import com.max.db_concurrent.entity.ResultEntity;
import com.max.db_concurrent.entity.dto.OrderDto;
import java.util.List;

public interface OrderService {

  ResultEntity operate(OrderDto orderDto);

  ResultEntity batchUpdate(List<String> orderNoList);

}
