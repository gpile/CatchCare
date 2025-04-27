package it.catchcare.trapiot.config;

import it.catchcare.trapiot.service.TrapEventService;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.List;

/**
 * Configuration class for setting up MQTT integration using Spring Integration.
 * <p>
 * This class configures the MQTT broker connection, message channels, and message handlers
 * to enable communication with an MQTT broker. It uses the Spring Integration MQTT module.
 * </p>
 * <p>
 * For more details, refer to the official documentation:
 * <a href="https://docs.spring.io/spring-integration/reference/mqtt.html">
 * Spring Integration MQTT Documentation
 * </a>
 * </p>
 */
@Configuration
public class MqttConfig {

    @Value("${mqtt.url}")
    private String brokerUrl;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.inbound-topics}")
    private List<String> inboundTopics;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId, clientFactory(), inboundTopics.toArray(new String[0]));
        adapter.setOutputChannel(mqttInputChannel());
        adapter.setQos(1);
        return adapter;
    }

    @Bean
    public MqttPahoClientFactory clientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{brokerUrl});
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler(TrapEventService trapEventService) {

        return message -> {
            String payload = (String) message.getPayload();
//            System.out.println("Received via MQTT: " + payload);
            trapEventService.processEvent(payload);

            // TODO qui puoi usare TrapEventParser.parse(payload)
        };

    }
}
