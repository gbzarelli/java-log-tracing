package br.com.helpdev.logtrace;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import org.slf4j.MDC;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Configuration;

@Configuration
@Provider
public class TraceJaxRsFilter implements ContainerRequestFilter, ContainerResponseFilter {

  public static final String HEADER_B3_TRACE_ID = "x-b3-traceid";

  public static final String MDC_KEY_TRACE_ID = "X.traceId";
  public static final String MDC_KEY_SPAN_ID = "X.spanId";

  private final Tracer tracer;

  TraceJaxRsFilter(final Tracer tracer) {
    this.tracer = tracer;
  }

  @Override
  public void filter(ContainerRequestContext containerRequestContext) {
    final var scope = tracer.startScopedSpan(String.valueOf(System.currentTimeMillis())).context();
    var traceId = containerRequestContext.getHeaderString(HEADER_B3_TRACE_ID);
    if (traceId == null) {
      traceId = scope.traceId();
    }
    MDC.put(MDC_KEY_TRACE_ID, traceId);
    MDC.put(MDC_KEY_SPAN_ID, scope.spanId());
  }

  @Override
  public void filter(final ContainerRequestContext containerRequestContext, final ContainerResponseContext containerResponseContext) {
    MDC.remove(MDC_KEY_TRACE_ID);
    MDC.remove(MDC_KEY_SPAN_ID);
  }
}
