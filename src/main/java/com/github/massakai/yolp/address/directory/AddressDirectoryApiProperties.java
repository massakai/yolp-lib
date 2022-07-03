package com.github.massakai.yolp.address.directory;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("com.github.massakai.yolp.address.directory")
@ConstructorBinding
@Value
public class AddressDirectoryApiProperties {

  String appId;
  String url;
}
