package com.github.massakai.yolp.address.directory.response;


import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.massakai.yolp.ResourceReader;
import java.io.IOException;
import java.net.URL;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AddressDirectoryResponseTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final ResourceReader RESOURCE_READER = new ResourceReader();

  @ParameterizedTest
  @MethodSource("jsonUrlProvider")
  void deserializeTest(final URL jsonUrl) throws IOException {

    AddressDirectory addressDirectory = OBJECT_MAPPER.readValue(jsonUrl, AddressDirectory.class);

    assertThat(addressDirectory).isNotNull();

  }

  static Stream<Arguments> jsonUrlProvider() {
    return IntStream.range(1, 48)
                    .boxed()
                    .map(i -> String.format("fixtures/address/directory/response/%02d.json", i))
                    .map(RESOURCE_READER::getResourceUrl)
                    .map(Arguments::arguments);
  }

}