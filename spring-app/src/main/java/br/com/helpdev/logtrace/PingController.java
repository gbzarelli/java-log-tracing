package br.com.helpdev.logtrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

  private static final Logger LOG = LoggerFactory.getLogger(PingController.class);

  @Value("${controller.ping.external.enable}")
  boolean externalPingEnabled;

  @Value("${spring.application.name}")
  String appName;

  private final ExternalPingClient externalPingClient;

  public PingController(final ExternalPingClient externalPingClient) {
    this.externalPingClient = externalPingClient;
  }

  @GetMapping(path = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
  public String ping() {
    LOG.info("new request log sample - {}", appName);

    if (externalPingEnabled) {
      LOG.info("external ping is enabled, executing external call");
      return "Pong from " + appName + " by external: " + externalPingClient.ping();
    }

    LOG.info("external ping is disabled! return direct PONG");
    return "Pong from " + appName + ")";
  }
}