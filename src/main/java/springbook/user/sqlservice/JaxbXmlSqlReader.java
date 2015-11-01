package springbook.user.sqlservice;

import springbook.user.domain.UserDao;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * Created by wayne on 2015. 11. 1..
 */
public class JaxbXmlSqlReader implements SqlReader {
	private static final String DEFAULT_SQLMAP_FILE = "/sqlmap.xml";

	private String sqlMapFile = DEFAULT_SQLMAP_FILE;

	public void setSqlMapFile(String sqlMapFile) {
		this.sqlMapFile = sqlMapFile;
	}

	@Override
	public void read(SqlRegistry sqlRegistry) {
		String contextPath = Sqlmap.class.getPackage().getName();
		try {
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream is = UserDao.class.getResourceAsStream(this.sqlMapFile);
			Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(is);

			for(SqlType sql : sqlmap.getSql()) {
				sqlRegistry.registerSql(sql.getKey(), sql.getValue());
			}
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
