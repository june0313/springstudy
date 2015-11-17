package springbook.user.sqlservice;

import org.springframework.context.annotation.Import;
import springbook.config.SqlServiceContext;

/**
 * Created by wayne on 2015. 11. 17..
 */
@Import(value = SqlServiceContext.class)
public @interface EnableSqlService {
}
