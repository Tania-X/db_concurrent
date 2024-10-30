package com.max.db_concurrent.entity.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderBean {

  private Integer id;

  private String orderNo;

  private String orderDetail;

  private String orderFlag;

}
