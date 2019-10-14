package work.codehub.library.repository.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Repository
public class VerificationCodeRedisTemplate {

    /**
     * 过期时间
     */
    private static int EXPIRE = 300;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加验证码 .
     *
     * @param
     * @return void
     * @author andy.sher
     * @date 2019/10/11 16:01
     */
    public void add(String key, String value) {
        stringRedisTemplate.opsForValue().set(getKey(key), value, EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 删除验证码 .
     *
     * @param
     * @return void
     * @author andy.sher
     * @date 2019/10/11 16:01
     */
    public void delete(String key) {
        stringRedisTemplate.delete(getKey(key));
    }

    /**
     * 获取验证码 .
     *
     * @param
     * @return void
     * @author andy.sher
     * @date 2019/10/11 16:01
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(getKey(key));
    }

    private String getKey(String key) {
        return "verification-code:" + key;
    }

}
