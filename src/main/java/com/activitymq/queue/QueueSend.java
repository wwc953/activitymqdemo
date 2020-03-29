package com.activitymq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息发送 队列模式
 *
 * @author wangweichun
 * @create 2018-07-22 19:17
 **/
public class QueueSend {
    public static void main(String[] args) throws JMSException, InterruptedException {

        System.out.println("============生产启动========");

        //实例化连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin",
                "admin", "tcp://192.168.91.128:61616");
        //通过连接工厂获取连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();

        //创建session
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //创建一个名称为HelloWorld的消息队列
        Destination destination = session.createQueue("wwc-queue");
        //创建消息生产者
        MessageProducer messageProducer = session.createProducer(destination);

        //发送消息
        for (int i = 0; i < 10; i++) {
            //创建一条文本消息
            TextMessage message = session.createTextMessage("ActiveMQ + " +i);

            System.out.println("发送消息：Activemq 发送消息" + i);
            //通过消息生产者发出消息
            messageProducer.send(message);
        }

        session.commit();
        session.close();
        connection.close();

    }
}
