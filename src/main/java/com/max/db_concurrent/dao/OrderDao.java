package com.max.db_concurrent.dao;

import com.max.db_concurrent.entity.bean.OrderBean;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderDao {

  int insertOrder(OrderBean orderBean);

  int updateFlagByOrderNo(@Param("orderNo") String orderNo, @Param("orderFlag") String orderFlag);

  OrderBean queryOrderByOrderNo(@Param("orderNo") String orderNo);

  List<OrderBean> queryAll();

}
