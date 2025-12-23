package sumdu.edu.ua.persistence.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class DbInit implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DbInit.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing database schema...");
        
        try (Connection c = dataSource.getConnection();
            Statement st = c.createStatement()) {

            try (var in = new ClassPathResource("schema.sql").getInputStream()) {
                String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                for (String cmd : sql.split(";")) {
                    if (!cmd.isBlank()) {
                        st.execute(cmd);
                    }
                }
            }

            var rs = st.executeQuery("SELECT COUNT(*) FROM books");
            if (rs.next() && rs.getInt(1) == 0) {
                log.info("Loading initial data...");
                try (var in = new ClassPathResource("initial-data.sql").getInputStream()) {
                    String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                    for (String cmd : sql.split(";")) {
                        if (!cmd.isBlank() && !cmd.trim().startsWith("--")) {
                            st.execute(cmd);
                        }
                    }
                }
            }
            
            log.info("Database initialization completed successfully.");
        } catch (Exception e) {
            log.error("Database initialization failed", e);
            throw new RuntimeException("DB schema init failed", e);
        }
    }
}

