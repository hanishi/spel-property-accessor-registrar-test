package org.springframework.cloud.stream.app.euler.httpclient.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Tests for EulerHttpClientProcessor.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@Slf4j
public abstract class SpELProcessorTests {

    @Autowired
    protected Processor channels;

    @Autowired
    protected MessageCollector messageCollector;

    @Autowired
    protected ObjectMapper objectMapper;

    @TestPropertySource(properties = {
            "test.decimalField=payload.value"
    })
    public static class HttpPostOperation extends SpELProcessorTests {

        @Test
        public void testForSpEL() throws Exception {
            String data = " {\n" +
                    "    \"payload\": {\"name\":\"Sensor ABC\",\"value\":10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.3}\n" +
                    "}";
            GenericMessage message = new GenericMessage<>(data);
            channels.input().send(message);
            String value = "10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.3";
            Message<?> m = messageCollector.forChannel(channels.output()).take();
            Object a = m.getPayload();
            assertThat(a, is(value));
        }
    }

    @SpringBootApplication
    public static class HttpClientProcessorApplication {

    }

}
