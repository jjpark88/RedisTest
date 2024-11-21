package org.example;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try(var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(var jedis = jedisPool.getResource()) {
                jedis.setbit("request3", 100, true);
                jedis.setbit("request3", 200, true);
                jedis.setbit("request3", 300, true);

                System.out.println(jedis.getbit("request3", 100));
                System.out.println(jedis.getbit("request3", 50));

                System.out.println(jedis.bitcount("request3"));

                // bitmap vs set
                //pipeline 쓰고 안쓰고 속도 차이 큼
                Pipeline pipelined = jedis.pipelined();
                IntStream.rangeClosed(0, 100000).forEach(i -> {
                    pipelined.sadd("request-set", String.valueOf(i), "1");
                    pipelined.setbit("request-bit", i, true);

                    if (i == 1000) {
                        pipelined.sync();
                    }
                });
                pipelined.sync();

                long memoryUsage = jedis.memoryUsage("request-set");
                System.out.println("Memory usage for 'request-set': " + memoryUsage + " bytes");

                long memoryUsage2 = jedis.memoryUsage("request-bit");
                System.out.println("Memory usage for 'request-bit': " + memoryUsage2 + " bytes");
            }
        }
    }
}