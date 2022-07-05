package com.github.massakai.yolp.address.directory.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoundingBoxTest {

  private BoundingBox boundingBox;

  @BeforeEach
  void setUp() {
    boundingBox = BoundingBox.builder()
                             .left(BigDecimal.valueOf(-90))
                             .right(BigDecimal.valueOf(90))
                             .bottom(BigDecimal.valueOf(-45))
                             .top(BigDecimal.valueOf(45))
                             .build();
  }

  static Stream<Arguments> containedCoordinatesProvider() {
    return Stream.of(
        arguments(new Coordinates(BigDecimal.valueOf(0), BigDecimal.valueOf(0))),
        arguments(new Coordinates(BigDecimal.valueOf(89.999), BigDecimal.valueOf(44.999))),
        arguments(new Coordinates(BigDecimal.valueOf(89.999), BigDecimal.valueOf(-44.999))),
        arguments(new Coordinates(BigDecimal.valueOf(-89.999), BigDecimal.valueOf(44.999))),
        arguments(new Coordinates(BigDecimal.valueOf(-89.999), BigDecimal.valueOf(-44.999)))
    );
  }

  static Stream<Arguments> notContainedCoordinatesProvider() {
    return Stream.of(
        arguments(new Coordinates(BigDecimal.valueOf(90.001), BigDecimal.valueOf(45.001))),
        arguments(new Coordinates(BigDecimal.valueOf(90.001), BigDecimal.valueOf(-45.001))),
        arguments(new Coordinates(BigDecimal.valueOf(-90.001), BigDecimal.valueOf(45.001))),
        arguments(new Coordinates(BigDecimal.valueOf(-90.001), BigDecimal.valueOf(-45.001))),
        // 境界上は含まれない
        arguments(new Coordinates(BigDecimal.valueOf(90), BigDecimal.valueOf(45))),
        arguments(new Coordinates(BigDecimal.valueOf(90), BigDecimal.valueOf(-45))),
        arguments(new Coordinates(BigDecimal.valueOf(-90), BigDecimal.valueOf(45))),
        arguments(new Coordinates(BigDecimal.valueOf(-90), BigDecimal.valueOf(-45)))
    );
  }

  @ParameterizedTest
  @MethodSource("containedCoordinatesProvider")
  void testContainsReturnsTrue(final Coordinates coordinates) {
    assertThat(boundingBox.contains(coordinates)).isTrue();
  }

  @ParameterizedTest
  @MethodSource("notContainedCoordinatesProvider")
  void testContainsReturnsFalse(final Coordinates coordinates) {
    assertThat(boundingBox.contains(coordinates)).isFalse();
  }
}