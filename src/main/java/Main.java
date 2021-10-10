import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;
import java.util.HashMap;
import java.util.Random;
import static java.lang.System.*;
import static java.lang.Thread.sleep;

public class Main
{
    private static final Random RANDOM = new Random();

    public static void main(String[] args)  {
        sitesConnect ("localhost", 6379);
        out.println ("---------------------------");
        redisConnect ();
    }

    private static int getRandom() {
        return RANDOM.nextInt(RedisStorage.AMOUNT) + 1;
    }


    public static void waitSomeTime(long second) {
        try {
            sleep(second);
        } catch (InterruptedException | JedisException exx) {
            exx.printStackTrace();
        }
    }

    public static void sitesConnect(String host, int port) {
        Jedis jedis = new Jedis (host,port);
        SityList sityList = new SityList (jedis);
        sityList.start (2,2);
    }

    public static void redisConnect() {

        RedisStorage redisStorage = new RedisStorage();
        redisStorage.start ();
        redisStorage.getInit ();
        int n = 1;
        int a = 1;

        while (true) {
            for (int i = 1; i <= RedisStorage.AMOUNT; i++) {
                System.out.println(n + " Показываем пользователя " + redisStorage.peek());
                redisStorage.add (i);
                redisStorage.removeFirstUser ();
                redisStorage.removeIdUser (a);
                if ((i + 1) % 10 == 0) {
                    a = getRandom() ;
                    System.out.println ("Пользователь " + a + " оплатил платную услугу");
                    redisStorage.push (a);
                }
            n++;
            }
            waitSomeTime (2000);
        }
    }

    public static void jedisConnect(String host, int port) {
        Jedis jedis = new Jedis(host,port);
        jedis.hset("Ivanov I.I.","Web-developer","1");
        jedis.hset("Ivanov I.I.","Data Science","4");

        HashMap<String, String> client1 = (HashMap<String, String>) jedis.hgetAll("Ivanov I.I.");
        for (String s : client1.keySet()) {
            out.println("Course " + s + " counts " + client1.get(s));
        }
        jedis.hincrBy("Ivanov I.I.", "Data Science", 1);
        out.println("Count from Data Science is " + jedis.hget("Ivanov I.I.", "Data Science"));
    }

}

