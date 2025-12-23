package sumdu.edu.ua.persistence.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sumdu.edu.ua.core.domain.Comment;
import sumdu.edu.ua.core.domain.Page;
import sumdu.edu.ua.core.domain.PageRequest;
import sumdu.edu.ua.core.port.CommentRepositoryPort;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

@Repository
public class JdbcCommentRepository implements CommentRepositoryPort {

    private static final Logger log = LoggerFactory.getLogger(JdbcCommentRepository.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public void add(long bookId, String author, String text) {
        try (var c = dataSource.getConnection();
            var ps = c.prepareStatement(
                    "insert into comments(book_id, author, text) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, bookId);
            ps.setString(2, author);
            ps.setString(3, text);
            ps.executeUpdate();

            try (var keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    log.info("DB: new comment #{}, book={}, author='{}', len={}",
                            id, bookId, author, text.length());
                }
            }
        } catch (SQLException e) {
            log.error("DB insert error", e);
            throw new RuntimeException("DB insert error", e);
        }
    }

    @Override
    public Page<Comment> list(long bookId, String author, Instant since, PageRequest request) {
        var items = new ArrayList<Comment>();
        long total = 0;

        StringBuilder sql = new StringBuilder(
                "select id, author, text, created_at from comments where book_id = ?"
        );

        if (author != null && !author.isBlank()) {
            sql.append(" and author like ?");
        }
        if (since != null) {
            sql.append(" and created_at >= ?");
        }

        sql.append(" order by created_at desc");
        sql.append(" limit ? offset ?");

        try (var c = dataSource.getConnection();
            var ps = c.prepareStatement(sql.toString())) {

            int i = 1;
            ps.setLong(i++, bookId);

            if (author != null && !author.isBlank()) {
                ps.setString(i++, "%" + author + "%");
            }
            if (since != null) {
                ps.setTimestamp(i++, Timestamp.from(since));
            }

            ps.setInt(i++, request.getSize());
            ps.setInt(i, request.getPage() * request.getSize());

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(new Comment(
                            rs.getLong("id"),
                            bookId,
                            rs.getString("author"),
                            rs.getString("text"),
                            rs.getTimestamp("created_at").toInstant()
                    ));
                }
            }

            try (var countPs = c.prepareStatement(
                    "select count(*) from comments where book_id = ?")) {
                countPs.setLong(1, bookId);
                try (var rs = countPs.executeQuery()) {
                    if (rs.next()) {
                        total = rs.getLong(1);
                    }
                }
            }

        } catch (SQLException e) {
            log.error("DB query error", e);
            throw new RuntimeException("DB query error", e);
        }

        return new Page<>(items, request, total);
    }

    @Override
    public Comment findById(long bookId, long commentId) {
        String sql = "select id, author, text, created_at from comments " +
                "where id = ? and book_id = ?";

        try (var c = dataSource.getConnection();
            var ps = c.prepareStatement(sql)) {
            ps.setLong(1, commentId);
            ps.setLong(2, bookId);

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Comment(
                            rs.getLong("id"),
                            bookId,
                            rs.getString("author"),
                            rs.getString("text"),
                            rs.getTimestamp("created_at").toInstant()
                    );
                }
            }
            return null;
        } catch (SQLException e) {
            log.error("DB query error", e);
            throw new RuntimeException("DB query error", e);
        }
    }

    @Override
    public void delete(long bookId, long commentId) {
        try (var c = dataSource.getConnection();
            var ps = c.prepareStatement(
                    "delete from comments where id=? and book_id=?")) {
            ps.setLong(1, commentId);
            ps.setLong(2, bookId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.info("DB: deleted comment #{} for book {}", commentId, bookId);
            }
        } catch (SQLException e) {
            log.error("DB delete error", e);
            throw new RuntimeException("DB delete error", e);
        }
    }
}

