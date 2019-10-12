package work.codehub.library.model;

import lombok.Data;
import work.codehub.library.pojo.AuthorVO;

/**
 * 认证信息
 */
@Data
public class TokenInfo {

    private String token;

    private AuthorVO authorVO;

}
