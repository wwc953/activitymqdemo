package com.activitymq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wangweichun
 * @create 2018-07-24 17:21
 **/
public class TopicReceiver {

    public static void main(String[] args) throws JMSException {
        //创建一个JMS connection factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin",
                "admin", "tcp://127.0.0.1:61616");

        //通过connection factory来创建JMS connection
        Connection connection = connectionFactory.createConnection();

        //设置ClientID
        connection.setClientID("con1");

        //通过connection创建JMS session
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

        //创建JMS destination
        Topic destination = session.createTopic("wwc-Topic");

        //创建JMS consumer
        TopicSubscriber ts = session.createDurableSubscriber(destination, "TT");

        //启动JMS connection
        connection.start();
        Message message = ts.receive();
        while (message != null) {
            TextMessage txtMsg = (TextMessage) message;
            session.commit();
            System.out.println("收到消息:" + txtMsg.getText());
            message = ts.receive(1000L);
        }
        //关闭所有的JMS资源
        session.close();
        connection.close();
    }

    /**
     *  1：需要在连接上设置消费者id，用来识别消费者
        2：需要创建TopicSubscriber来订阅
        3：要设置好了过后再start 这个 connection
        4：一定要先运行一次，等于向消息服务中间件注册这个消费者，然后再运行客户端发送信息，这个时候，
        无论消费者是否在线，都会接收到，不在线的话，下次连接的时候，会把没有收过的消息都接收下来。
     */

}
