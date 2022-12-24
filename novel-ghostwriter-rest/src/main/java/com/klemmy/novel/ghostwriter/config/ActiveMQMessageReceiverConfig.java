package com.klemmy.novel.ghostwriter.config;

import jakarta.jms.JMSException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import java.util.List;

@Configuration
@EnableJms
@EnableConfigurationProperties(MessageBusProperties.class)
@ConditionalOnProperty(name = "message-bus.type", havingValue = "activemq")
public class ActiveMQMessageReceiverConfig {

  private final MessageBusProperties messageBusProperties;

  public ActiveMQMessageReceiverConfig(MessageBusProperties messageBusProperties) {
    this.messageBusProperties = messageBusProperties;
  }

  @Bean
  public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() throws JMSException {
    ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
    activeMQConnectionFactory.setBrokerURL(this.messageBusProperties.brokerUrl());
   // activeMQConnectionFactory.setTrustAllPackages(true);
   // activeMQConnectionFactory.setTrustedPackages(List.of("com.klemmy.novelideas.api"));
    activeMQConnectionFactory.setDeserializationWhiteList("com.klemmy.novelideas.service");
    return activeMQConnectionFactory;
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws JMSException {
    DefaultJmsListenerContainerFactory activeMQListenerFactory =  new DefaultJmsListenerContainerFactory();
    activeMQListenerFactory.setConnectionFactory(receiverActiveMQConnectionFactory());
    activeMQListenerFactory.setPubSubDomain(true); // For topics

    return activeMQListenerFactory;
  }

  @Bean(name="ActiveMQTopic")
  public String activeMQTopic(){
    return this.messageBusProperties.topic();
  }

}
