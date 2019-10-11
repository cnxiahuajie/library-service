package work.codehub.library.api.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.codehub.library.annotation.Login;
import work.codehub.library.annotation.Logout;
import work.codehub.library.api.model.RequestEntity;
import work.codehub.library.api.model.ResponseEntity;

/**
 * 安全认证控制器 .<br>
 *
 * @author andy.sher
 * @date 2019/10/11 15:18
 */
@RestController
@RequestMapping("/v1")
public class SecurityController {

    @Login
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody RequestEntity requestEntity) {
        JSONObject requestData = JSONObject.parseObject(requestEntity.getData());
        String username = requestData.getString("username");
        String password = requestData.getString("password");
        if ("root".equals(username) && "root".equals(password)) {
            return ResponseEntity.build(HttpStatus.OK);
        } else {
            return ResponseEntity.error("认证失败。");
        }
    }

    @Logout
    @PostMapping("/logout")
    public ResponseEntity logout() {
        return ResponseEntity.build(HttpStatus.OK);
    }

}
