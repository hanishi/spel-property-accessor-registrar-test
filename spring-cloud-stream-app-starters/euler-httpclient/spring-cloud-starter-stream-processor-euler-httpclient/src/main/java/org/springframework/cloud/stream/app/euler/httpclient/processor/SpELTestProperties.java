package org.springframework.cloud.stream.app.euler.httpclient.processor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.expression.Expression;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("test")
@Validated
@Getter
@Setter
public class SpELTestProperties {

    private Expression decimalField;
}
