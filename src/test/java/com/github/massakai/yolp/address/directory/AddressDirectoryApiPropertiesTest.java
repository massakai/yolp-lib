package com.github.massakai.yolp.address.directory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class AddressDirectoryApiPropertiesTest {

  @Test
  void test() {
    new ApplicationContextRunner()
        .withPropertyValues(
            "com.github.massakai.yolp.address.directory.app-id=TOKEN",
            "com.github.massakai.yolp.address.directory.url=http://localhost:8888/search/address/V1/addressDirectory"
        )
        .withUserConfiguration(AddressDirectoryConfiguration.class)
        .run(context -> {
          AddressDirectoryApiProperties properties = context.getBean(
              AddressDirectoryApiProperties.class);

          assertThat(properties).isNotNull();
          assertThat(properties.getAppId()).isEqualTo("TOKEN");
          assertThat(properties.getUrl()).isEqualTo(
              "http://localhost:8888/search/address/V1/addressDirectory");
        });
  }
}