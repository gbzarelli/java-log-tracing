package br.com.helpdev.logtrace;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Path("/ping")
@Component
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

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String ping(@Context HttpHeaders headers) {
    LOG.info("new request log sample - {}", appName);
    LOG.debug("request headers - {}", headers.getRequestHeaders());

    if (externalPingEnabled) {
      LOG.info("external ping is enabled, executing external call");
      return "Pong from " + appName + " by external: " + externalPingClient.ping();
    }

    LOG.info("external ping is disabled! return direct PONG");
    return "Pong from " + appName + ")";
  }
}