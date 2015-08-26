package com.here.framework.cloud.defaultimpl.test;

import java.util.Date;  

import javax.jms.Connection;  
import javax.jms.Destination;  
import javax.jms.JMSException;  
import javax.jms.MapMessage;  
import javax.jms.MessageProducer;  
import javax.jms.Session;  
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;  
import org.apache.activemq.ActiveMQConnectionFactory;  
  
  
/** 
 * 消息的发送者 
 */  
public class EmailProducer {  
    public static void main(String[] args) {  
        String user = ActiveMQConnection.DEFAULT_USER;  
        String password = ActiveMQConnection.DEFAULT_PASSWORD;  
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;  
        String subject = "SENDREGISTEREMAIL";  
        /**创建连接工厂*/  
        ActiveMQConnectionFactory contectionFactory = new ActiveMQConnectionFactory( user, password, url);  
          
        try {  
            /**创建连接*/  
            Connection connection = contectionFactory.createConnection();  
            connection.start();//开启连接  
            /**创建session*/  
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);  
            /**指定消息的目的地*/  
            Destination destination = session.createQueue(subject);  
            /**创建发送消息对象*/  
            MessageProducer producer = session.createProducer(destination);  
          
            TextMessage textMessage = session.createTextMessage();
            producer.send(textMessage);
            
            session.commit();  
            session.close();  
            connection.close();  
        } catch (JMSException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  