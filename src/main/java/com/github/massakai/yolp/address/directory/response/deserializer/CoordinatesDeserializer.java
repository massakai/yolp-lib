package com.github.massakai.yolp.address.directory.response.deserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.massakai.yolp.address.directory.response.Coordinates;
import java.io.IOException;
import java.math.BigDecimal;

public class CoordinatesDeserializer extends JsonDeserializer<Coordinates> {

  @Override
  public Coordinates deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext)
      throws IOException {
    String[] pairs = jsonParser.readValueAs(String.class)
                               .split(",");

    if (pairs.length != 2) {
      throw new JsonParseException(jsonParser,
                                   "Coordinates must contains 2 numbers separated by comma.");
    }

    return Coordinates.builder()
                      .longitude(new BigDecimal(pairs[0]))
                      .latitude(new BigDecimal(pairs[1]))
                      .build();
  }
}
