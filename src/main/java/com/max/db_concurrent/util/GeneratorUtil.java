package com.max.db_concurrent.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneratorUtil {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final SecureRandom RANDOM = new SecureRandom();

  public static String generateOrderNo() {
    StringBuilder orderNo = new StringBuilder(6);

    for (int i = 0; i < 12; i++) {
      int index = RANDOM.nextInt(CHARACTERS.length());
      orderNo.append(CHARACTERS.charAt(index));
    }

    SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
    String time = sdf.format(new Date());
    orderNo.append(time);

    return orderNo.toString();
  }

}
