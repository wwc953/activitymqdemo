package com.activitymq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wangweichun
 * @create 2018-07-24 17:20
 **/
public class TopicSend {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin",
                "admin", "tcp://127.0.0.1:61616");

        //通过connection factory来创建JMS connection
        Connection connection = connectionFactory.createConnection();

        //通过connection创建JMS session
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

        //创建JMS destination
        Destination destination = session.createTopic("wwc-Topic");

        //创建JMS producer
        MessageProducer producer = session.createProducer(destination);

        //设置传送模式为持久模式，默认为非持久
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //启动JMS connection
        connection.start();

        for (int i = 0; i < 10; i++) {
            TextMessage message = session.createTextMessage("message 3 -" + i);
            //发送message
            producer.send(message);
        }
        //关闭所有的JMS资源
        session.commit();
        session.close();
        connection.close();
    }

    /**
     *  1：要用持久化订阅，发送消息者要用 DeliveryMode.PERSISTENT 模式发现，在连接之前设定
     *  2：一定要设置完成后，再start 这个 connection
     */
}
