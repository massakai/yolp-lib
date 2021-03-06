package com.github.massakai.yolp.address.directory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.github.massakai.yolp.ResourceReader;
import com.github.massakai.yolp.address.directory.response.AddressDirectory;
import com.github.massakai.yolp.address.directory.response.AddressDirectoryResponse;
import com.github.massakai.yolp.address.directory.response.BoundingBox;
import com.github.massakai.yolp.address.directory.response.Coordinates;
import com.github.massakai.yolp.address.directory.response.Country;
import com.github.massakai.yolp.address.directory.response.Feature;
import com.github.massakai.yolp.address.directory.response.Geometry;
import com.github.massakai.yolp.address.directory.response.Property;
import com.github.massakai.yolp.address.directory.response.ResultInfo;
import java.math.BigDecimal;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.model.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockServerExtension.class)
class AddressDirectoryClientTest {

  private static final String PATH = "/search/address/V1/addressDirectory";
  private static final ResourceReader RESOURCE_READER = new ResourceReader();

  @Test
  void test(final MockServerClient mockServerClient) {
    mockServerClient.when(
                        request()
                            .withMethod("GET")
                            .withPath(PATH)
                            .withQueryStringParameter("appid", "TOKEN")
                            .withQueryStringParameter("ac", "01")
                            .withQueryStringParameter("mode", "1")
                            .withQueryStringParameter("output", "json")
                    )
                    .respond(
                        response()
                            .withContentType(MediaType.APPLICATION_JSON)
                            .withBody(RESOURCE_READER.readResource(
                                "fixtures/address/directory/response/01.json"))
                    );

    var properties = new AddressDirectoryApiProperties(
        "TOKEN",
        "http://localhost:" + mockServerClient.getPort() + PATH
    );
    var addressDirectoryClient = new AddressDirectoryClient(properties, WebClient.builder());

    AddressDirectoryResponse response = addressDirectoryClient.get("01", "1")
                                                              .block(Duration.ofSeconds(1));

    assertThat(response).isNotNull();

    assertThat(response.getResultInfo())
        .isNotNull()
        .isEqualTo(ResultInfo.builder()
                             .count(194)
                             .total(194)
                             .start(1)
                             .status(200)
                             .description("")
                             .copyright("")
                             .latency(0.02)
                             .build());

    assertThat(response.getFeature()).isNotNull()
                                     .hasSize(1);

    Feature feature = response.getFeature()
                              .get(0);
    assertThat(feature).isNotNull();
    assertThat(feature.getName()).isEqualTo("?????????");
    assertThat(feature.getGeometry()).isEqualTo(
        Geometry.builder()
                .type("point")
                .coordinates(
                    Coordinates.builder()
                               .longitude(new BigDecimal("141.34685706"))
                               .latitude(new BigDecimal("43.06451413"))
                               .build())
                .boundingBox(
                    BoundingBox.builder()
                               .left(new BigDecimal("139.3338800"))
                               .top(new BigDecimal("41.3516210"))
                               .right(new BigDecimal("148.8950450"))
                               .bottom(new BigDecimal("45.5577170"))
                               .build())
                .build()
    );

    Property property = feature.getProperty();
    assertThat(property).isNotNull();
    assertThat(property.getCassetteId()).isEqualTo("b22fee69b0dcaf2c2fe2d6a27906dafc");
    assertThat(property.getCountry()).isEqualTo(Country.builder()
                                                       .code("JP")
                                                       .name("??????")
                                                       .build());
    assertThat(property.getAddress()).isEqualTo("?????????");
    assertThat(property.getGovernmentCode()).isEqualTo("01");
    assertThat(property.getAddressDirectory()).hasSizeGreaterThan(1);

    // ??????????????????????????????????????????????????????1?????????????????????
    AddressDirectory addressDirectory = property.getAddressDirectory()
                                                .get(0);
    assertThat(addressDirectory).isEqualTo(
        AddressDirectory.builder()
                        .areaCode("01456")
                        .name("????????????????????????")
                        .kana("?????????????????????????????????????????????")
                        .geometry(
                            Geometry.builder()
                                    .type("point")
                                    .coordinates(
                                        Coordinates.builder()
                                                   .longitude(new BigDecimal("142.57797573"))
                                                   .latitude(new BigDecimal("43.90650582"))
                                                   .build())
                                    .build())
                        .build()
    );
  }
}