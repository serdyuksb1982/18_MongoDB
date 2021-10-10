import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.RedisConnectionException;

import org.redisson.config.Config;

public class RedisStorage {
    private static final String HOST = "redis://127.0.0.1:6379";
    public static String KEY = "users";
    public static final int AMOUNT = 20;
    private RedissonClient redissonClient;
    private RDeque<Integer> rDeque;

    public void start() {
        Config config = new Config();
        config.useSingleServer().setAddress(HOST);
        try {
            redissonClient = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            System.out.println("Нет подключения к Redis");
            System.out.println(Exc.getMessage());
        }
        rDeque = redissonClient.getDeque(KEY);
    }

    public void getInit() {

        for (int i = 0; i < AMOUNT; i++) {
            rDeque.add(i);
        }
    }

    public void add(int id) {
        rDeque.addLast(id);
    }


    public void push(int id) {
        rDeque.push(id);
    }

    public Integer peek() {
        return rDeque.peekFirst ();
    }


    public Integer removeFirstUser() {
        return rDeque.removeFirst ();
    }

    public Boolean removeIdUser(int id ) {
        return rDeque.remove(id);
    }


}