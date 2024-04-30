package fr.hermannvincent.guildhood;

import fr.hermannvincent.guildhood.guild.Guild;
import fr.hermannvincent.guildhood.server.Database;
import fr.hermannvincent.guildhood.server.GuildHoodDedicated;

import java.sql.*;
import java.util.UUID;

public class GuildHoodManager {

    public static void playerJoinGuild(UUID playerUUID, UUID guildUUID) {
        Database database = new Database();
    }

    private boolean guildsTableExists() {
        boolean exists = false;
        try (Connection connection = GuildHoodDedicated.database.connect();
             Statement statement = connection.createStatement()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "guilds", null);
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    private void createGuildsTable() {
        String sql = "CREATE TABLE guilds (id UUID DEFAULT UUID_GENERATE_V4() PRIMARY KEY, ownerId UUID, name VARCHAR(255))";
        try (Connection connection = GuildHoodDedicated.database.connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createGuildsHasMemberTable() {
        String sql = "CREATE TABLE guild_has_member (id UUID DEFAULT UUID_GENERATE_V4() PRIMARY KEY, guildId UUID, playerId UUID)";
        try (Connection connection = GuildHoodDedicated.database.connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Guild createGuildForUser(UUID userId, String guildName) {
        String insertSql = "INSERT INTO guilds (ownerId, name) VALUES (?, ?)";
        String selectSql = "SELECT id, ownerId, name FROM guilds WHERE name = ?";

        try (Connection connection = GuildHoodDedicated.database.connect();
             PreparedStatement insertStatement = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {

            // Insert the new guild
            insertStatement.setObject(1, userId);
            insertStatement.setString(2, guildName);
            insertStatement.executeUpdate();

            // Retrieve the inserted guild's details
            selectStatement.setString(1, guildName);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    UUID guildId = UUID.fromString(resultSet.getString("id"));
                    UUID ownerId = UUID.fromString(resultSet.getString("ownerId"));
                    String name = resultSet.getString("name");
                    return new Guild(guildId, ownerId, name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if the guild couldn't be created or retrieved
    }

    public static GuildHoodManager init() {
        GuildHoodManager guildHoodManager = new GuildHoodManager();

        if (!guildHoodManager.guildsTableExists()) {
            guildHoodManager.createGuildsTable();
            guildHoodManager.createGuildsHasMemberTable();
        }

        return guildHoodManager;
    }
}
