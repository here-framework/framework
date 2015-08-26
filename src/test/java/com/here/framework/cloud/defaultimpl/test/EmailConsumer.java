package com.here.framework.cloud.defaultimpl.test;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/** 
 * queue:消息消费者 
 *  
 * @author  文超 
 * @version  [版本号, Apr 27, 2013 5:28:49 PM ] 
 * @see  [相关类/方法] 
 * @since  [产品/模块版本] 
 */  
public class EmailConsumer {  
    public static void main(String[] args) {  
        String user = ActiveMQConnection.DEFAULT_USER;  
        String password = ActiveMQConnection.DEFAULT_PASSWORD;  
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;  
        String subject = "SENDREGISTEREMAIL";  
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory( user, password, url);  
        Connection connection;  
        try {  
            connection = connectionFactory.createConnection();  
            connection.start();  
            final Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);  
            Destination destination = session.createQueue(subject);  
            MessageConsumer message = session.createConsumer(destination);  
            message.setMessageListener(new MessageListener() {  
                public void onMessage(Message msg) {  
                      
                }  
            });  
            session.close();  
            connection.close();  
        } catch (JMSException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  