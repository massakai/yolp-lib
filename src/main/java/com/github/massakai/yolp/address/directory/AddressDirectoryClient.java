package com.github.massakai.yolp.address.directory;

import com.github.massakai.yolp.address.directory.response.AddressDirectoryResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AddressDirectoryClient {

  private final AddressDirectoryApiProperties addressDirectoryApiProperties;
  private final WebClient webClient;

  public AddressDirectoryClient(
      final AddressDirectoryApiProperties addressDirectoryApiProperties,
      final WebClient.Builder webClientBuilder) {
    this.addressDirectoryApiProperties = addressDirectoryApiProperties;
    webClient = webClientBuilder.baseUrl(addressDirectoryApiProperties.getUrl())
                                .build();
  }

  Mono<AddressDirectoryResponse> get(final String ac, final String mode) {
    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .queryParam("appid", addressDirectoryApiProperties.getAppId())
                    .queryParam("ac", ac)
                    .queryParam("mode", mode)
                    .queryParam("output", "json")
                    .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(AddressDirectoryResponse.class);
  }
}
