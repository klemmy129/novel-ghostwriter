package com.klemmy.novel.ghostwriter.listener;

import com.klemmy.novelideas.api.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;

@Component
@Slf4j
@ConditionalOnProperty(name = "message-bus.type", havingValue = "activemq")
public class ActiveMQReceiver implements MessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQReceiver.class);

  @Override
  @JmsListener(destination = "#{ActiveMQTopic}")
  public void onMessage(Message message) {
    ObjectMessage objectMessage = (ObjectMessage) message;
    try {
      BookDto bookDto = (BookDto) objectMessage.getObject();
      LOGGER.info("Someone is looking at a book titled: '{}'", bookDto.getName());
    } catch (JMSException e) {
      LOGGER.error("Received Exception: '{}'", e.getMessage());
    }
  }

}
