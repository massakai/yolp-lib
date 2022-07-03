package com.github.massakai.yolp;

import java.net.URL;

public class ResourceReader {

  public URL getResourceUrl(final String name) {
    return getClass().getClassLoader()
                     .getResource(name);
  }
}
