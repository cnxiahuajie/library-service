package work.codehub.library.api.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.codehub.library.api.model.ResponseEntity;
import work.codehub.library.constants.CommonConstants;
import work.codehub.library.util.AesUtils;
import work.codehub.library.util.StringUtils;

/**
 * AES工具接口 .<br>
 *
 * @author andy.sher
 * @date 2019/10/29 10:40
 */
@RestController
@RequestMapping("/v1")
public class AesUtilController {

    @GetMapping("/anon/aes/{content}/{key}/{iv}/{mode}")
    public ResponseEntity aes(@PathVariable("content") String content, @PathVariable("key") String key, @PathVariable("iv") String iv, @PathVariable("mode") String mode) {
        String result = StringUtils.EMPTY;
        switch (mode) {
            case CommonConstants.Symbol.PLUS:
                result = AesUtils.encrypt(content, key, iv);
                break;
            case CommonConstants.Symbol.MINUS:
                result = AesUtils.decrypt(content, key, iv);
                break;
            default:
                break;
        }

        JSONObject data = new JSONObject();
        data.put("result", result);
        return ResponseEntity.ok(data);
    }

}
