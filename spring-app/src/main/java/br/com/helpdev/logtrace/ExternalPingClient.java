package br.com.helpdev.logtrace;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ExternalPingClient", url = "${controller.ping.external.host}")
public interface ExternalPingClient {

  @GetMapping(path = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
  String ping();

}
