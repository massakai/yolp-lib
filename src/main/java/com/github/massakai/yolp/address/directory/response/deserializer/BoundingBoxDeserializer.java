package com.github.massakai.yolp.address.directory.response.deserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.massakai.yolp.address.directory.response.BoundingBox;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BoundingBoxDeserializer extends JsonDeserializer<BoundingBox> {

  @Override
  public BoundingBox deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext)
      throws IOException {
    String[] points = jsonParser.readValueAs(String.class)
                                .split(" ");
    if (points.length != 2) {
      throw new JsonParseException(jsonParser,
                                   "BoundingBox must contains 2 coordinates separated by space.");
    }

    List<BigDecimal> values = Arrays.stream(points)
                                    .flatMap(point -> Arrays.stream(point.split(",")))
                                    .map(BigDecimal::new)
                                    .collect(Collectors.toList());

    if (values.size() != 4) {
      throw new JsonParseException(jsonParser, "BoundingBox must contains 4 numbers.");
    }

    return BoundingBox.builder()
                      .left(values.get(0))
                      .top(values.get(1))
                      .right(values.get(2))
                      .bottom(values.get(3))
                      .build();
  }
}
