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

    public static ResponseEntity unauthorized(String message) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(HttpStatus.UNAUTHORIZED.value());
        responseEntity.setMsg(message);
        return responseEntity;
    }

    public static ResponseEntity ok() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(HttpStatus.OK.value());
        responseEntity.setMsg(HttpStatus.OK.getReasonPhrase());
        return responseEntity;
    }

    public static ResponseEntity error() {
        return error("请求异常。");
    }

    public static ResponseEntity error(String message) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(HttpStatus.BAD_REQUEST.value());
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
    public static ResponseEntity build(HttpStatus status) {
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
    public static ResponseEntity build(HttpStatus status, Object data) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(status.value());
        responseEntity.setMsg(status.getReasonPhrase());
        if (null != data) {
            responseEntity.setData(data);
        }
        return responseEntity;
    }

}
