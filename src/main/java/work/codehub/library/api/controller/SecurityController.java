package work.codehub.library.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.codehub.library.annotation.Login;
import work.codehub.library.annotation.Logout;
import work.codehub.library.api.model.RequestEntity;
import work.codehub.library.api.model.ResponseEntity;
import work.codehub.library.domain.Author;
import work.codehub.library.exception.UnauthenticationException;
import work.codehub.library.pojo.AuthorVO;
import work.codehub.library.repository.redis.VerificationCodeRedisTemplate;
import work.codehub.library.service.IAuthorService;
import work.codehub.library.util.BeanUtils;
import work.codehub.library.util.StringUtils;

import javax.annotation.Resource;

/**
 * 安全认证控制器 .<br>
 *
 * @author andy.sher
 * @date 2019/10/11 15:18
 */
@RestController
@RequestMapping("/v1")
public class SecurityController {

    @Resource
    private VerificationCodeRedisTemplate verificationCodeRedisTemplate;

    @Resource
    private IAuthorService authorService;

    @Login
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody RequestEntity requestEntity) {
        JSONObject requestData = JSONObject.parseObject(requestEntity.getData());
        String username = requestData.getString("username");
        String password = requestData.getString("password");

        Assert.hasLength(username, "邮箱不能为空。");
        Assert.hasLength(password, "口令不能为空。");

        String realPassword = verificationCodeRedisTemplate.get(username);

        if (StringUtils.isNotBlank(realPassword) && password.equals(realPassword)) {
            LambdaQueryWrapper<Author> wr = new QueryWrapper<Author>().lambda();
            wr.eq(Author::getEmail, username);
            Author author = authorService.getOne(wr);
            AuthorVO authorVO = null;

            // 如果没有找到用户就新增一个新用户
            if (null == author) {
                authorVO = new AuthorVO();
                authorVO.setEmail(username);
                authorVO.setName("路人" + StringUtils.getRandomString(6));
                authorService.save(authorVO);
            } else {
                authorVO = BeanUtils.copy(author, AuthorVO.class);
            }

            JSONObject data = new JSONObject();
            data.put("authorDetails", authorVO);
            return ResponseEntity.build(HttpStatus.OK, data);
        } else {
            throw new UnauthenticationException("认证失败。");
        }
    }

    @Logout
    @PostMapping("/logout")
    public ResponseEntity logout() {
        return ResponseEntity.build(HttpStatus.OK);
    }

}
