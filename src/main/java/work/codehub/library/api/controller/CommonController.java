package work.codehub.library.api.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.codehub.library.api.model.RequestEntity;
import work.codehub.library.api.model.ResponseEntity;
import work.codehub.library.repository.redis.VerificationCodeRedisTemplate;
import work.codehub.library.util.StringUtils;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1")
public class CommonController {

    /**
     * 验证码长度
     */
    private static int CODE_LENGTH = 6;

    @Resource
    private VerificationCodeRedisTemplate verificationCodeRedisTemplate;

    @PostMapping("/common/send-email-verification-code")
    public ResponseEntity sendEmailVerificationCode(@RequestBody RequestEntity requestEntity) {
        JSONObject data = JSONObject.parseObject(requestEntity.getData());
        verificationCodeRedisTemplate.add(data.getString("username"), StringUtils.getRandomString(CODE_LENGTH));
        return ResponseEntity.ok();
    }

}