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
}
