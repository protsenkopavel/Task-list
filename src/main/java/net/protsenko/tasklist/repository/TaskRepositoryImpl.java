package net.protsenko.tasklist.repository;

import net.protsenko.tasklist.config.DataSourceConfig;
import net.protsenko.tasklist.domain.Task;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;

    public TaskRepositoryImpl(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public Task findById(Long id) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    SELECT t.id              as task_id,
                           t.title           as task_title,
                           t.description     as task_description
                    FROM tasks t
                    WHERE id = ?""");

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getLong("task_id"));
                    task.setTitle(rs.getString("task_title"));
                    task.setDescription(rs.getString("task_description"));
                    return task;
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
                           t.description     as task_description
                    FROM tasks t
                             JOIN users u on t.id = u.id
                    WHERE u.user_id = ?""");

            statement.setLong(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
                List<Task> tasks = new ArrayList<>();
                while (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getLong("task_id"));
                    if (!rs.wasNull()) {
                        task.setTitle(rs.getString("task_title"));
                        task.setDescription(rs.getString("task_description"));
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
                    WHERE id = ?""");

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setLong(3, task.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Task task) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO tasks (title, description)
                    VALUES (?, ?)""");

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
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

}
