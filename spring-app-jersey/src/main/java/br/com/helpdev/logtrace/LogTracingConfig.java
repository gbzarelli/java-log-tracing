package br.com.helpdev.logtrace;

import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.http.HttpServerHandler;
import org.springframework.cloud.sleuth.instrument.web.servlet.TracingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTracingConfig {

  @Bean
  public TracingFilter tracingFilter(
      CurrentTraceContext currentTraceContext, HttpServerHandler httpServerHandler) {
    return TracingFilter.create(currentTraceContext, httpServerHandler);
  }

}