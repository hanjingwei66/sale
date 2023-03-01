package cn.iocoder.yudao.module.system.dal.redis.common;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.CAPTCHA_CODE;
@Repository
public class AreaRedisDAO<T> {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate redisTemplate;
    private Objects T;
    public String get(String uuid) {
        String redisKey = formatKey(uuid);
        return stringRedisTemplate.opsForValue().get(redisKey);
    }

    public void set(String uuid, String code, Duration timeout) {
        String redisKey = formatKey(uuid);
        stringRedisTemplate.opsForValue().set(redisKey, code, timeout);
    }

    public void delete(String uuid) {
        String redisKey = formatKey(uuid);
        stringRedisTemplate.delete(redisKey);
    }

    private static String formatKey(String uuid) {
        return String.format(CAPTCHA_CODE.getKeyTemplate(), uuid);
    }
    public void setList(String key, List<T> valueList, Duration timeout) {
        redisTemplate.opsForList().leftPushAll(key,valueList);
    }
    public List<T>  getList(String key) {
        List<T> list = redisTemplate.opsForList().range(key, 0, -1);
        return list;
    }

}
