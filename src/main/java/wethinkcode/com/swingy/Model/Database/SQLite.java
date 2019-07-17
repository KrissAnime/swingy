package wethinkcode.com.swingy.Model.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wethinkcode.com.swingy.Model.Artifacts.ArtifactFactory;
import wethinkcode.com.swingy.Model.Inventory.Inventory;
import wethinkcode.com.swingy.Model.Stats.Artifact;
import wethinkcode.com.swingy.Model.Units.GameCharacters;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLite {
    private String query;

    private Connection connect() {
        String url = "jdbc:sqlite:swingy.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public SQLite() {
        try {
            SetUpUserTable();
            SetUpUserEquipmentTable();
            SetUpUserInventoryTable();
            SetUpArtifactsTable();

            UploadArtifactData();

            CreateDummyUsers();
            CreateDummyUsers2();

            SetDummySetup();
            SetDummySetup2();
        } catch (Exception e) {
            System.out.println("Could not set up DB");
            System.exit(2);
        }

    }

    private void SetUpUserTable() {
        Connection connection = this.connect();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `USERS` (" +
                    "`hero_name` VARCHAR(30) NOT NULL PRIMARY KEY," +
                    "`hero_class` VARCHAR(30) NOT NULL ," +
                    "`level` INT(3) NOT NULL ," +
                    "`experience` INT(3) NOT NULL ," +
                    "`attack` INT(3) NOT NULL ," +
                    "`defence` INT(3) NOT NULL ," +
                    "`hitPoints` INT(3) NOT NULL ," +
                    "`maxHitPoints` INT(3) NOT NULL ," +
                    "`luck` INT(3) NOT NULL ," +
                    "`x_position` INT(2) NOT NULL ," +
                    "`y_position` INT(2) NOT NULL ," +
                    "`map_size` INT(3) DEFAULT 10 ," +
                    "`turn` INT(3) DEFAULT 0 ," +
                    "`potions` INT(3) NOT NULL )");
            statement.close();
        } catch (SQLException error) {
            System.out.println("SQL exception occurred during database table setup");
            error.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void SetUpUserEquipmentTable() {
        Connection connection = this.connect();
        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `USER_EQUIPMENT` (" +
                    "`equipment` VARCHAR(105) DEFAULT NULL ," +
                    "`hero_name` VARCHAR(30) NOT NULL PRIMARY KEY)");
            statement.close();
        } catch (SQLException error) {
            System.out.println("SQL exception occurred during database table setup");
            error.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void SetUpUserInventoryTable() {
        Connection connection = this.connect();
        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `USER_INVENTORY` (" +
                    "`items` VARCHAR(320) DEFAULT NULL ," +
                    "`hero_name` VARCHAR(30) NOT NULL PRIMARY KEY)");
            statement.close();
        } catch (SQLException error) {
            System.out.println("SQL exception occurred during database table setup");
            error.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void SetUpArtifactsTable() {
        Connection connection = this.connect();
        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `ARTIFACTS` (" +
                    "`artifact_name` VARCHAR(30) NOT NULL PRIMARY KEY," +
                    "`artifact_stat` INT(4) NOT NULL ," +
                    "`artifact_rarity` VARCHAR(30) NOT NULL," +
                    "`artifact_class` VARCHAR(30) NOT NULL)");
            statement.close();
        } catch (SQLException error) {
            System.out.println("SQL exception occurred during database table setup");
            error.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void InsertArtifactData(String[] artifactData) {
        Connection connection = this.connect();
        try {
            query = "INSERT INTO `ARTIFACTS` (`artifact_name`, `artifact_stat`, `artifact_rarity`, `artifact_class`) VALUES(?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, artifactData[0]);
            statement.setInt(2, Integer.valueOf(artifactData[1]));
            statement.setString(3, artifactData[2]);
            statement.setString(4, artifactData[3]);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException error) {
            System.out.println("SQL exception occurred during data insertion");
            error.printStackTrace();
            System.exit(2);
        } finally {
            try {
                connection.close();
            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void UploadArtifactData() {
        Connection connection = this.connect();
        try {
            Statement statement = connection.createStatement();

            query = "SELECT COUNT(1) FROM `ARTIFACTS`";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                BufferedReader reader = new BufferedReader(new FileReader("EquipmentSetup"));
                String line = reader.readLine();
                statement.close();
                resultSet.close();
                if (line != null) {
                    while ((line = reader.readLine()) != null) {
                        InsertArtifactData(line.split(";"));
                    }
                }
            }
        } catch (FileNotFoundException error) {
            System.out.println("Database setup could not find EquipmentSetup File");
            error.printStackTrace();
        } catch (IOException error) {
            System.out.println("There was an error attempting to read the EquipmentSetupFile");
            error.printStackTrace();
        } catch (SQLException error) {
            System.out.println("There was an error uploading artifact data");
            error.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void CreateDummyUsers() {
        Connection connection = this.connect();
        try {
            Statement statement = connection.createStatement();
            query = "SELECT COUNT(1) FROM `USERS` WHERE `hero_name` == 'KrissAnime'";

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                query = "INSERT INTO `USERS` (`hero_name`, `hero_class`, `level`, `experience`, `attack`, `defence`, `hitPoints`, `maxHitPoints`, `luck`, `x_position`, `y_position`, `potions`, `turn`) VALUES " +
                        "('KrissAnime', 'Mage', 3, 5000, 100, 80, 280, 400, 5, 8, 7, 2, 12)";
                statement.executeUpdate(query);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException error) {
            System.out.println("There was an error creating initial users");
            error.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void CreateDummyUsers2() {
        Connection connection = this.connect();
        try {
            Statement statement = connection.createStatement();
            query = "SELECT COUNT(1) FROM `USERS` WHERE `hero_name` == 'Potato'";

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                query = "INSERT INTO `USERS` (`hero_name`, `hero_class`, `level`, `experience`, `attack`, `defence`, `hitPoints`, `maxHitPoints`, `luck`, `x_position`, `y_position`, `potions`, `turn`) VALUES " +
                        "('Potato', 'Support', 3, 5000, 75, 88, 420, 500, 5, 8, 7, 2, 12)";
                statement.executeUpdate(query);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException error) {
            System.out.println("There was an error creating initial users");
            error.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void SetDummySetup(){
        Connection connection = this.connect();
        try {
            query = "SELECT COUNT(1) FROM `USER_EQUIPMENT` WHERE `hero_name` == 'KrissAnime'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                query = "INSERT INTO `USER_EQUIPMENT` (`equipment`, `hero_name`) VALUES " +
                        "('Gae Bolg,Armour of Achilles,Helm of Terror', 'KrissAnime')";
                statement.executeUpdate(query);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException error) {
            System.out.println("There was an error creating initial users");
            error.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void SetDummySetup2(){
        Connection connection = this.connect();
        try {
            query = "SELECT COUNT(1) FROM `USER_EQUIPMENT` WHERE `hero_name` == 'Potato'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                query = "INSERT INTO `USER_EQUIPMENT` (`equipment`, `hero_name`) VALUES " +
                        "('Void Sword,Babr-e Bayan,Guardian Helmet', 'Potato')";
                statement.executeUpdate(query);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException error) {
            System.out.println("There was an error creating initial users");
            error.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    public List<Artifact> RetrieveUserEquipment(String hero_name) {
        List<Artifact> EquippedArtifacts = new ArrayList<>();
        Connection connection = this.connect();
        try {
            query = "SELECT * FROM `USER_EQUIPMENT` WHERE `hero_name` = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, hero_name);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String[] equipment = resultSet.getString("equipment").split(",");
                for (int i = 0; i < equipment.length; i++) {
                    if (!equipment[i].isEmpty()) {
                        EquippedArtifacts.add(RetrieveArtifactForEquipping(equipment[i]));
                    }
                }
            }
            statement.close();
            resultSet.close();
        } catch (SQLException
                error) {
            System.out.println("SQL exception occurred during database table setup");
            error.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
        return EquippedArtifacts;
    }

    public List<Artifact> RetrieveUserInventory(String hero_name) {
        List<Artifact> InventoryArtifacts = new ArrayList<>();
        Connection connection = this.connect();
        try {
            query = "SELECT * FROM `USER_INVENTORY` WHERE `hero_name` = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, hero_name);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                InventoryArtifacts.add(ArtifactFactory.newArtifact(resultSet.getString("artifact_class"),
                        resultSet.getString("artifact_name"), resultSet.getString("artifact_rarity"),
                        resultSet.getInt("artifact_stat")));
            }
            statement.close();
            resultSet.close();
        } catch (SQLException error) {
            System.out.println("SQL exception occurred during database table setup");
            error.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
        return InventoryArtifacts;
    }

    public JSONArray RetrieveArtifactsByRarity(String artifact_rarity) {
        JSONArray PossibleDrop = new JSONArray();
        Connection connection = this.connect();
        try {
            query = "SELECT * FROM `ARTIFACTS` WHERE `artifact_rarity` = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, artifact_rarity);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                JSONObject artifact = new JSONObject();
                artifact.put("artifact_class", resultSet.getString("artifact_class"));
                artifact.put("artifact_name", resultSet.getString("artifact_name"));
                artifact.put("artifact_rarity", resultSet.getString("artifact_rarity"));
                artifact.put("artifact_stat", resultSet.getInt("artifact_stat"));
                PossibleDrop.put(artifact);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException error) {
            System.out.println("SQL exception occurred during Artifact retrieval by rarity");
            error.printStackTrace();
        } catch (JSONException e) {
            System.out.println("There was an error with the JSON Artifact objects");
            e.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
        return PossibleDrop;
    }

    public Artifact RetrieveArtifactForEquipping(String artifact_name) {
        Artifact equipping = null;
        Connection connection = this.connect();

        try {
            query = "SELECT * FROM `ARTIFACTS` WHERE `artifact_name` = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, artifact_name);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            equipping = ArtifactFactory.newArtifact(resultSet.getString("artifact_class"), resultSet.getString("artifact_name"), resultSet.getString("artifact_rarity"), resultSet.getInt("artifact_stat"));
            statement.close();
            resultSet.close();
        } catch (SQLException error) {
            System.out.println("SQL exception occurred during artifact retrieval for equipping");
            error.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
        return equipping;
    }

    public ArrayList<String> RetrieveNames() {
        ArrayList<String> Players = new ArrayList<>();
        Connection connection = this.connect();
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM `USERS`");
            while (resultSet.next()) {
                Players.add(resultSet.getString("hero_name"));
            }
            statement.close();
            resultSet.close();
        } catch (SQLException error) {
            System.out.println("SQL exception occurred during database table setup");
            error.printStackTrace();
        } catch (JSONException e) {
            System.out.println("There was an error with the JSON Artifact objects");
            e.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
        return Players;
    }

    public JSONArray RetrievePlayers() {
        JSONArray Players = new JSONArray();
        Connection connection = this.connect();
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM `USERS`");
            while (resultSet.next()) {
                JSONObject player = new JSONObject();
                player.put("hero_name", resultSet.getString("hero_name"));
                player.put("hero_class", resultSet.getString("hero_class"));
                player.put("level", resultSet.getString("level"));
                player.put("experience", resultSet.getString("experience"));
                player.put("attack", resultSet.getString("attack"));
                player.put("defence", resultSet.getString("defence"));
                player.put("hitPoints", resultSet.getString("hitPoints"));
                player.put("maxHitPoints", resultSet.getString("maxHitPoints"));
                player.put("luck", resultSet.getString("luck"));
                player.put("x_position", resultSet.getString("x_position"));
                player.put("y_position", resultSet.getString("y_position"));
                player.put("map_size", resultSet.getString("map_size"));
                player.put("potions", resultSet.getString("potions"));
                player.put("turn", resultSet.getString("turn"));
                Players.put(player);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException error) {
            System.out.println("SQL exception occurred during database table setup");
            error.printStackTrace();
        } catch (JSONException e) {
            System.out.println("There was an error with the JSON Artifact objects");
            e.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
        return Players;
    }

    public void SaveGame(GameCharacters player, Inventory inventory){
        Connection connection = this.connect();
        try {
            query = "SELECT COUNT(1) FROM `USERS` WHERE `hero_name` = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, player.getName());

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                statement.close();
                resultSet.close();
                connection.close();
                SaveNewPlayer(player);
                SaveNewPlayerEquipment(player, inventory);
            } else {
                statement.close();
                resultSet.close();
                connection.close();
                SaveExistingPlayer(player);
                SaveExistingPlayerEquipment(player, inventory);
            }
        } catch (SQLException error) {
            System.out.println("There was a problem saving the game of " + player.getName());
            error.printStackTrace();
        }
    }

    private void SaveNewPlayer(GameCharacters player) {
        Connection connection = this.connect();
        try {
            query = "INSERT INTO `USERS` (`hero_name`, `hero_class`, `level`, `experience`, `attack`, `defence`" +
                    ", `hitPoints`, `maxHitPoints`, `luck`, `x_position`, `y_position`, `potions`, `map_size`) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, player.getName());
            statement.setString(2, player.getHeroClass());
            statement.setInt(3, player.getUnitStats().getLevel());
            statement.setInt(4, player.getUnitStats().getExperience());
            statement.setInt(5, player.getUnitStats().getAttack());
            statement.setInt(6, player.getUnitStats().getDefence());
            statement.setInt(7, player.getUnitStats().getHitPoints());
            statement.setInt(8, player.getUnitStats().getMaxHitPoints());
            statement.setInt(9, player.getUnitStats().getLuck());
            statement.setInt(10, player.getMapPosition().getXPosition());
            statement.setInt(11, player.getMapPosition().getYPosition());
            statement.setInt(12, player.getUnitStats().getPotions());
            statement.setInt(13, player.getMapPosition().getMapSize());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException error) {
            System.out.println("There was a problem saving the game of new player " + player.getName());
            error.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void SaveNewPlayerEquipment(GameCharacters player, Inventory inventory) {
        Connection connection = this.connect();
        try {
            ArrayList<String> equipment = new ArrayList<>();
            query = "INSERT INTO `USER_EQUIPMENT` (`equipment`, `hero_name`) VALUES " +
                    "(?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            equipment = SetUpDataBaseEquipment(equipment, inventory);

            statement.setString(1, String.join(",", equipment));
            statement.setString(2, player.getName());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException error) {
            System.out.println("There was a problem saving the game of new player " + player.getName());
            error.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void SaveExistingPlayerEquipment(GameCharacters player, Inventory inventory) {
        Connection connection = this.connect();
        try {
            query = "UPDATE `USER_EQUIPMENT` SET `equipment` = ? WHERE `hero_name` = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            ArrayList<String> equipment = new ArrayList<>();
            equipment = SetUpDataBaseEquipment(equipment, inventory);

            statement.setString(1, String.join(",", equipment));
            statement.setString(2, player.getName());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException error) {
            System.out.println("There was a problem saving the game of new player " + player.getName());
            error.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private void SaveExistingPlayer(GameCharacters player) {
        Connection connection = this.connect();

        try {
            query = "UPDATE `USERS` SET `level` = ?, `experience` = ?, `attack` = ?, `defence` = ?, `hitPoints` = ?," +
                    "`maxHitPoints` = ?, `luck` = ?, `x_position` = ?, `y_position` = ?, `potions` = ?, `map_size` = ?" +
                    "WHERE `hero_name` = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, player.getUnitStats().getLevel());
            statement.setInt(2, player.getUnitStats().getExperience());
            statement.setInt(3, player.getUnitStats().getAttack());
            statement.setInt(4, player.getUnitStats().getDefence());
            statement.setInt(5, player.getUnitStats().getHitPoints());
            statement.setInt(6, player.getUnitStats().getMaxHitPoints());
            statement.setInt(7, player.getUnitStats().getLuck());
            statement.setInt(8, player.getMapPosition().getXPosition());
            statement.setInt(9, player.getMapPosition().getYPosition());
            statement.setInt(10, player.getUnitStats().getPotions());
            statement.setInt(11, player.getMapPosition().getMapSize());
            statement.setString(12, player.getName());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException error) {
            System.out.println("There was a problem updating the save game of " + player.getName());
            error.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException error) {
                System.out.println("There was a problem closing the DB connection");
                error.printStackTrace();
            }
        }
    }

    private ArrayList<String> SetUpDataBaseEquipment(ArrayList<String> equipment, Inventory inventory) {
        for (Artifact artifact: inventory.getEquippedItems()) {
            switch (artifact.getArtifactClass()) {
                case "Weapon":
                    equipment.add(artifact.getArtifactName());
                    break;
                case "Armor":
                    equipment.add(artifact.getArtifactName());
                    break;
                case "Helmet":
                    equipment.add(artifact.getArtifactName());
                    break;
            }
        }
        if (equipment.size() == 1) {
            equipment.add("");
        }
        return equipment;
    }
}