import redis.clients.jedis.Jedis;

import java.util.Set;

public class SityList {
    private Jedis jedis;

    public SityList(Jedis client) {
        this.jedis = client;
    }

    public void start(int min, int max){
        init();
        getMin (min);
        getMax (max);
        remove();
    }

    private void init (){
        jedis.zadd("City",5000, "Москва");
        jedis.zadd("City",3800, "Санкт-Петербург");
        jedis.zadd("City",2700, "Тверь");
        jedis.zadd("City",2300, "Орел");
        jedis.zadd("City",3100, "Курск");
        jedis.zadd("City",2900, "Нижний-Новгород");
        jedis.zadd("City",1800, "Ржев");
        jedis.zadd("City",1500, "Бологое");
        jedis.zadd("City",4800, "Калининград");
    }

    private void getMin(int num){
        Set<String> city = jedis.zrange("City", 0, num);
        System.out.println("Дешевые билеты: " + city + System.lineSeparator ());
    }

    private void getMax(int num){
        Set<String> city = jedis.zrevrange("City", 0, num);
        System.out.println("Дорогие билеты: " + city + System.lineSeparator ());

    }

    private void remove() {
        jedis.del("City");
    }
}
