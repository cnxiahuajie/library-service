package work.codehub.library.pojo;

import lombok.Data;
import work.codehub.library.domain.Author;

import java.util.List;

@Data
public class AuthorVO extends Author {

    private List<ArticleCategoryVO> interests;

}
