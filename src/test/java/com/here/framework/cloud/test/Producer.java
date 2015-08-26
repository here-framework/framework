package com.here.framework.cloud.test;

import java.util.Date;  

import javax.jms.Connection;  
import javax.jms.Destination;  
import javax.jms.JMSException;  
import javax.jms.MapMessage;  
import javax.jms.MessageProducer;  
import javax.jms.Session;  

import org.apache.activemq.ActiveMQConnection;  
import org.apache.activemq.ActiveMQConnectionFactory;  
  
  
/** 
 * 消息的发送者 
 */  
public class Producer {  
    public static void main(String[] args) {  
        String user = ActiveMQConnection.DEFAULT_USER;  
        String password = ActiveMQConnection.DEFAULT_PASSWORD;  
        String url =  "tcp://20.12.6.163:61616";  
        String subject = "TOOL.DEFAULT";  
        /**创建连接工厂*/  
        ActiveMQConnectionFactory contectionFactory = new ActiveMQConnectionFactory( "system", "111111", url);  
          
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
          
            for (int i = 0; i <= 2; i++) {  
                MapMessage message = session.createMapMessage();  
                Date date = new Date();   
                message.setLong("date", date.getTime());  
                message.setString("seqnum", String.valueOf(i));  
                Thread.sleep(1000);  
                producer.send(message);  
                System.out.println("发送端发送消息：" + date);  
            }  
            session.commit();  
            session.close();  
            connection.close();  
        } catch (JMSException e) {  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
}  