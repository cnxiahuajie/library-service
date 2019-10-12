package work.codehub.library.repository.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import work.codehub.library.model.TokenInfo;
import work.codehub.library.util.StringUtils;

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
     * 添加token认证信息 .
     *
     * @param
     * @return void
     * @author andy.sher
     * @date 2019/10/11 16:01
     */
    public void add(String token, TokenInfo tokenInfo) {
        stringRedisTemplate.opsForValue().set(token, JSONObject.toJSONString(tokenInfo), EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 删除token认证信息 .
     *
     * @param
     * @return void
     * @author andy.sher
     * @date 2019/10/11 16:01
     */
    public void delete(String token) {
        stringRedisTemplate.delete(token);
    }

    /**
     * 获取token认证信息 .
     *
     * @param
     * @return void
     * @author andy.sher
     * @date 2019/10/11 16:01
     */
    public TokenInfo get(String token) {
        String data = stringRedisTemplate.opsForValue().get(token);
        if (StringUtils.isNotBlank(data)) {
            return JSONObject.parseObject(data, TokenInfo.class);
        } else {
            return null;
        }
    }

}
