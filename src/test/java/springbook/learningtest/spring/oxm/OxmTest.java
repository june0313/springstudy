package springbook.learningtest.spring.oxm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by wayne on 2015. 11. 1..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OxmTest {
	@Autowired
	private Unmarshaller unmarshaller;

	@Test
	public void testUnmarshalSqlMap() throws XmlMappingException, IOException {
		Source xmlSource = new StreamSource(getClass().getResourceAsStream("/test-sqlmap.xml"));
		Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(xmlSource);

		List<SqlType> sqlTypeList = sqlmap.getSql();
		assertThat(sqlTypeList.size(), is(3));
		assertThat(sqlTypeList.get(0).getKey(), is("add"));
		assertThat(sqlTypeList.get(1).getKey(), is("get"));
		assertThat(sqlTypeList.get(2).getKey(), is("delete"));
	}
}
