package work.codehub.library.api.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.codehub.library.api.model.RequestEntity;
import work.codehub.library.api.model.ResponseEntity;
import work.codehub.library.domain.Feedback;
import work.codehub.library.service.IFeedbackService;

import javax.annotation.Resource;

/**
 * 问题反馈控制器 .<br>
 *
 * @author andy.sher
 * @date 2019/10/14 14:23
 */
@RestController
@RequestMapping("/v1")
public class FeedbackController {

    @Resource
    private IFeedbackService feedbackService;

    @PostMapping("/feedback")
    public ResponseEntity add(@RequestBody RequestEntity requestEntity) {
        Feedback feedback = JSONObject.parseObject(requestEntity.getData(), Feedback.class);
        feedbackService.save(feedback);
        return ResponseEntity.ok();
    }

}
