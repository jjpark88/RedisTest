package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {
                // list
                // 1. stack
//                jedis.rpush("stack1", "a");
//                jedis.rpush("stack1", "b");
//                jedis.rpush("stack1", "c");
//
//                System.out.println(jedis.rpop("stack1"));
//                System.out.println(jedis.rpop("stack1"));
//                System.out.println(jedis.rpop("stack1"));
//
//                List<String> stack1 = jedis.lrange("stack1", 0, -1);
//                stack1.forEach(System.out::println);
//
//                // 2. queue
//                jedis.rpush("queue2", "d");
//                jedis.rpush("queue2", "a");
//                jedis.rpush("queue2", "c");
//
//                System.out.println(jedis.lpop("queue2"));
//                System.out.println(jedis.lpop("queue2"));
//                System.out.println(jedis.lpop("queue2"));

                // 3. block brpop, blpop
                while (true) {
                    List<String> blpop = jedis.blpop(30, "queue:blocking");
                    if (blpop != null) {
                        blpop.forEach(System.out::println);
                    }
                }

            }
        }
    }
}