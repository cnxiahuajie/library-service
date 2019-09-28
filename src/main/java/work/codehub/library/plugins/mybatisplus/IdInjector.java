package work.codehub.library.plugins.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import work.codehub.library.constants.CommonConstants;
import work.codehub.library.util.StringUtils;

import java.util.UUID;

/**
 * TODO .<br>
 *
 * @author andy.sher
 * @date 2019/9/28 17:03
 */
@Component
public class IdInjector implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("id", (UUID.randomUUID().toString() + UUID.randomUUID().toString()).replaceAll(CommonConstants.Symbol.MINUS, StringUtils.EMPTY));
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

}
