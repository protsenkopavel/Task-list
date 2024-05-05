package net.protsenko.tasklist.repository;

import net.protsenko.tasklist.config.DataSourceConfig;
import net.protsenko.tasklist.domain.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                           u.password        as user_password
                    FROM users u
                    WHERE u.id = ?""");

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("user_id"));
                    user.setName(rs.getString("user_name"));
                    user.setPassword(rs.getString("user_password"));
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
                        username = ?,
                        password = ?
                    WHERE id = ?""");

            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(User user) {
        try (Connection connection = dataSourceConfig.getDataSource().getConnection();) {

            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO users (name, username, password)
                    VALUES (?, ?, ?)""");

            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getId());
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
