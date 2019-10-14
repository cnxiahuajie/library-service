package work.codehub.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.codehub.library.domain.Feedback;
import work.codehub.library.repository.mapper.FeedbackMapper;
import work.codehub.library.service.IFeedbackService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author codehub
 * @since 2019-08-31
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

}
