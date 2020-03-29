package com.activitymq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息接受
 *
 * @author wangweichun
 * @create 2018-07-22 19:19
 **/
public class QueueReceiver {

    public static void main(String[] args) throws JMSException {

        System.out.println("============消费启动============");

        //实例化连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin",
                "admin", "tcp://192.168.91.128:61616");
        //通过连接工厂获取连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();

        //创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个名称为HelloWorld的消息队列  
        Destination destination = session.createQueue("wwc-queue");
        //创建消息消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);

        //接受消息
        while (true) {
            //接收者接收消息
            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            if(textMessage != null){
                System.out.println("收到的消息:" + textMessage.getText());
            }else {
                break;
            }
        }

        session.commit();
        session.close();
        connection.close();

    }

}
