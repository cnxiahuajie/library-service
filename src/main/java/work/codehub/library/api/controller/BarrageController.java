package work.codehub.library.api.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.codehub.library.api.model.RequestEntity;
import work.codehub.library.api.model.ResponseEntity;
import work.codehub.library.constants.DomainConstants;
import work.codehub.library.plugins.activemq.producer.BarrageMessageProducer;
import work.codehub.library.pojo.BarrageVO;
import work.codehub.library.service.IBarrageService;

import javax.annotation.Resource;

/**
 * 弹幕控制器 .<br>
 *
 * @author andy.sher
 * @date 2019/10/17 17:36
 */
@RestController
@RequestMapping("/v1")
public class BarrageController {

    @Resource
    private IBarrageService barrageService;

    @Resource
    private BarrageMessageProducer barrageMessageProducer;

    @PostMapping("/anon/barrage")
    public ResponseEntity add(@RequestBody RequestEntity requestEntity) {
        BarrageVO barrageVO = JSONObject.parseObject(requestEntity.getData(), BarrageVO.class);
        barrageVO.setType(DomainConstants.Barrage.TYPE_1);
        barrageVO.setTarget("ALL");
        // 保存弹幕
        barrageService.save(barrageVO);

        barrageMessageProducer.send(barrageVO);
        return ResponseEntity.ok();
    }

}
