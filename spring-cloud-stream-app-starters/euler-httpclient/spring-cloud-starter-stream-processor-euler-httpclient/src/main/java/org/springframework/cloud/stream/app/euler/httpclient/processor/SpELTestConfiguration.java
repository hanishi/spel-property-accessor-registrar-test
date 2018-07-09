package org.springframework.cloud.stream.app.euler.httpclient.processor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.PropertyAccessor;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.expression.SpelPropertyAccessorRegistrar;
import org.springframework.integration.json.JsonPropertyAccessor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBinding(Processor.class)
@ComponentScan
@IntegrationComponentScan
@Slf4j
public class SpELTestConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        return objectMapper;
    }

    @Bean
    public JsonPropertyAccessor jsonPropertyAccessor(ObjectMapper objectMapper) {
        JsonPropertyAccessor jsonPropertyAccessor = new JsonPropertyAccessor();
        jsonPropertyAccessor.setObjectMapper(objectMapper);
        return jsonPropertyAccessor;
    }

    @Bean
    public SpelPropertyAccessorRegistrar spelPropertyAccessorRegistrar(JsonPropertyAccessor jsonPropertyAccessor) {
        return new SpelPropertyAccessorRegistrar(jsonPropertyAccessor);
    }


//    @Bean
//    public SpelPropertyAccessorRegistrar spelPropertyAccessorRegistrar(JsonPropertyAccessor jsonPropertyAccessor) {
//        Map<String, PropertyAccessor> map = new HashMap<>();
//        map.put("someKey", jsonPropertyAccessor);
//        return new SpelPropertyAccessorRegistrar(map);
//    }

    @Bean
    public IntegrationFlow transferIntegrationFlow(SpELTestProperties properties) {
        return IntegrationFlows.from(Processor.INPUT)
                .handle((p, h) -> properties.getDecimalField().getValue(p, String.class)).channel(Processor.OUTPUT).get();
    }
}
