package work.codehub.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.codehub.library.domain.Author;
import work.codehub.library.repository.mapper.AuthorMapper;
import work.codehub.library.service.IAuthorService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author codehub
 * @since 2019-08-31
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements IAuthorService {

}
