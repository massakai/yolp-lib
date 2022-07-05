package com.github.massakai.yolp.address.directory.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class BoundingBox {

  BigDecimal left;
  BigDecimal top;
  BigDecimal right;
  BigDecimal bottom;

  /**
   * 座標がバウンディングボックスの内部に含まれているかどうか
   *
   * @param coordinates 座標
   * @return 含まれる場合は{@code true}, 含まれない場合は{@code false}
   */
  public boolean contains(final Coordinates coordinates) {
    return left.compareTo(coordinates.getLongitude()) < 0
        && right.compareTo(coordinates.getLongitude()) > 0
        && bottom.compareTo(coordinates.getLatitude()) < 0
        && top.compareTo(coordinates.getLatitude()) > 0;
  }
}
