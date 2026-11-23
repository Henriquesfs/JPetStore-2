package org.springframework.samples.jpetstore;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public final class TestDatabase {

	public static final String DRIVER = "org.hsqldb.jdbc.JDBCDriver";

	public static final String URL = "jdbc:hsqldb:mem:jpetstore";

	private static final String SCHEMA = "db/hsqldb/jpetstore-hsqldb-schema.sql";

	private static final String DATA = "db/hsqldb/jpetstore-hsqldb-dataload.sql";

	private TestDatabase() {
	}

	public static void reset(DataSource dataSource) throws IOException {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("SHUTDOWN");
		for (String statement : statements(SCHEMA)) {
			if (!statement.toLowerCase().startsWith("drop")) {
				jdbc.execute(statement);
			}
		}
		for (String statement : statements(DATA)) {
			jdbc.execute(statement);
		}
	}

	private static List<String> statements(String path) throws IOException {
		String script = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.ISO_8859_1);
		List<String> statements = new ArrayList<String>();
		for (String statement : script.split(";")) {
			if (statement.trim().length() > 0) {
				statements.add(statement.trim());
			}
		}
		return statements;
	}
}
