package work.codehub.library.repository.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * token redis 仓库 .<br>
 *
 * @author andy.sher
 * @date 2019/10/11 15:59
 */
@Repository
public class TokenRedisTemplate {

    /**
     * 过期时间
     */
    private static int EXPIRE = 86000;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加token .
     *
     * @param
     * @return void
     * @author andy.sher
     * @date 2019/10/11 16:01
     */
    public void add(String token) {
        stringRedisTemplate.opsForValue().set(token, token, EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 删除token .
     *
     * @param
     * @return void
     * @author andy.sher
     * @date 2019/10/11 16:01
     */
    public void delete(String token) {
        stringRedisTemplate.delete(token);
    }

}
