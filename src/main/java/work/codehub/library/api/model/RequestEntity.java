package work.codehub.library.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求实体 .<br>
 *
 * @author andy.sher
 * @date 2019/8/28 16:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEntity {

    private String client;

    private String data;

}
