package net.protsenko.tasklist.repository;

import net.protsenko.tasklist.config.DataSourceConfig;
import net.protsenko.tasklist.domain.Status;
import net.protsenko.tasklist.domain.Task;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;

    public TaskRepositoryImpl(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public Optional<Task> findById(Long id) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    SELECT t.id              as task_id,
                           t.title           as task_title,
                           t.description     as task_description,
                           t.expiration_date as task_expiration,
                           t.status          as task_status
                    FROM tasks t
                    WHERE t.id = ?""");

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getLong("task_id"));
                    task.setTitle(rs.getString("task_title"));
                    task.setDescription(rs.getString("task_description"));
                    task.setStatus(Status.valueOf(rs.getString("task_status")));
                    Timestamp timestamp = rs.getTimestamp("task_expiration_date");
                    task.setExpirationDate(timestamp.toLocalDateTime());
                    return Optional.of(task);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    SELECT t.id              as task_id,
                           t.title           as task_title,
                           t.description     as task_description,
                           t.expiration_date as task_expiration,
                           t.status          as task_status
                    FROM tasks t
                             JOIN public.users_tasks ut on t.id = ut.task_id
                    WHERE ut.user_id = ?""");

            statement.setLong(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
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
                return tasks;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Task task) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    UPDATE tasks
                    SET title = ?,
                        description = ?,
                        expiration_date = ?,
                        status = ?
                    WHERE id = ?""");

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            statement.setString(4, task.getStatus().name());
            statement.setLong(5, task.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Task task) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO tasks (title, description, expiration_date, status)
                    VALUES (?, ?, ?, ?)""");

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            statement.setString(4, task.getStatus().name());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    DELETE FROM tasks
                    WHERE id = ?""");

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void assignTaskToUser(Long taskId, Long userId) {

    }

}
