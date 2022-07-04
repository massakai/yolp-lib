package com.github.massakai.yolp.address.directory.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.massakai.yolp.address.directory.response.deserializer.BoundingBoxDeserializer;
import com.github.massakai.yolp.address.directory.response.deserializer.CoordinatesDeserializer;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class Geometry {

  String type;
  @JsonDeserialize(using = CoordinatesDeserializer.class)
  Coordinates coordinates;
  @JsonDeserialize(using = BoundingBoxDeserializer.class)
  BoundingBox boundingBox;
}
