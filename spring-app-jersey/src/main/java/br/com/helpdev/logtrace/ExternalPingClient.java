package br.com.helpdev.logtrace;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ExternalPingClient", url = "${controller.ping.external.host}")
public interface ExternalPingClient {

  @GET
  @Path("/ping")
  @Consumes(javax.ws.rs.core.MediaType.TEXT_PLAIN)
  @Produces(MediaType.TEXT_PLAIN)
  String ping();

}
