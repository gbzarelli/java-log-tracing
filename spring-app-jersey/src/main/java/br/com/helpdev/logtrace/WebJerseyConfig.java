package br.com.helpdev.logtrace;

import java.util.Objects;
import java.util.stream.Collectors;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

@Configuration
public class WebJerseyConfig extends ResourceConfig {

  public WebJerseyConfig() {
    var scanner = new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(Path.class));
    registerClasses(
        scanner.findCandidateComponents("br.com.helpdev.logtrace").stream()
            .map(
                beanDefinition ->
                    ClassUtils.resolveClassName(
                        Objects.requireNonNull(beanDefinition.getBeanClassName()),
                        getClassLoader()))
            .collect(Collectors.toSet()));

  }
}
