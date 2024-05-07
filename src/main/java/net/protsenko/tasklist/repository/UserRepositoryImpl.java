package net.protsenko.tasklist.repository;

import net.protsenko.tasklist.config.DataSourceConfig;
import net.protsenko.tasklist.domain.Status;
import net.protsenko.tasklist.domain.Task;
import net.protsenko.tasklist.domain.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;

    public UserRepositoryImpl(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public User findById(Long id) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    SELECT u.id              as user_id,
                           u.name            as user_name,
                           u.email           as user_email,
                           u.password        as user_password,
                           t.id              as task_id,
                           t.title         as task_title,
                           t.description as task_description,
                           t.expiration_date as task_expiration_date,
                           t.status         as task_status
                    FROM users u
                        LEFT JOIN users_tasks ut on u.id = ut.user_id
                        LEFT JOIN tasks t on ut.task_id = t.id
                    WHERE u.id = ?""");

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("user_id"));
                    user.setName(rs.getString("user_name"));
                    user.setEmail(rs.getString("user_email"));
                    user.setPassword(rs.getString("user_password"));

                    List<Task> tasks = new ArrayList<>();
                    while (rs.next()) {
                        Task task = new Task();
                        task.setId(rs.getLong("task_id"));
                        if (!rs.wasNull()) {
                            task.setTitle(rs.getString("task_title"));
                            task.setDescription(rs.getString("task_description"));
                            task.setStatus(Status.valueOf(rs.getString("task_status")));
                            Timestamp timestamp = rs.getTimestamp("task_expiration_date");
                            task.setExpirationDate(timestamp.toLocalDateTime());
                        }
                        tasks.add(task);
                    }

                    user.setTasks(tasks);
                    return user;
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    UPDATE users
                    SET name = ?,
                        email = ?,
                        password = ?
                    WHERE id = ?""");

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(User user) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO users (name, email, password)
                    VALUES (?, ?, ?)""");

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    DELETE FROM users
                    WHERE id = ?""");

            statement.setLong(1, id);
            statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
