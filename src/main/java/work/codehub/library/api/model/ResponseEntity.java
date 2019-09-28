package work.codehub.library.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 响应实体 .
 *
 * @author andy.sher
 * @date 2019/8/28 16:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity implements Serializable {

    private static final long serialVersionUID = 537317725743820683L;

    private int code;

    private String msg;

    private Object data;

    public static final ResponseEntity error() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(400);
        responseEntity.setMsg("出现问题了，攻城狮们正在紧急抢修中。");
        return responseEntity;
    }

    public static final ResponseEntity error(String message) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(400);
        responseEntity.setMsg(message);
        return responseEntity;
    }

    /**
     * 生成一个响应对象（响应码和响应消息取自 HttpStatus） .
     *
     * @param status 响应状态
     * @return work.codehub.model.ResponseEntity 响应对象
     * @author andy.sher
     * @date 2019/8/30 9:49
     */
    public static final ResponseEntity build(HttpStatus status) {
        return build(status, null);
    }

    /**
     * 生成一个响应对象（响应码和响应消息取自 HttpStatus） .
     *
     * @param status 响应状态
     * @param data   响应内容
     * @return work.codehub.model.ResponseEntity 响应对象
     * @author andy.sher
     * @date 2019/8/30 9:49
     */
    public static final ResponseEntity build(HttpStatus status, Object data) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(status.value());
        responseEntity.setMsg(status.getReasonPhrase());
        if (null != data) {
            responseEntity.setData(data);
        }
        return responseEntity;
    }

}
