package com.github.massakai.yolp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceReader {

  public URL getResourceUrl(final String name) {
    return getClass().getClassLoader()
                     .getResource(name);
  }

  public String readResource(final String name) {
    try {
      return Files.readString(Paths.get(getResourceUrl(name).toURI()));
    } catch (final URISyntaxException | IOException e) {
      // テストでカジュアルにファイルを読み込みたいので非チェック例外にする
      throw new RuntimeException(e);
    }
  }
}
