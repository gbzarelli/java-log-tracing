package br.com.helpdev.logtrace;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/ping")
public class GreetingResource {

  private static final Logger LOG = LoggerFactory.getLogger(GreetingResource.class);

  @ConfigProperty(name = "controller.ping.external.enable")
  boolean externalPingEnabled;

  @ConfigProperty(name = "quarkus.application.name")
  String appName;

  private final ExternalPingClient externalPingClient;

  public GreetingResource(@RestClient final ExternalPingClient externalPingClient) {
    this.externalPingClient = externalPingClient;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello(@Context HttpHeaders headers) {
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