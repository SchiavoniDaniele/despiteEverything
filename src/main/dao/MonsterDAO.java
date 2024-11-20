package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.enums.MonsterStatus;
import main.models.Monster;
import utils.DataBaseLoader;

public class MonsterDAO {

	public Monster save(Monster monster) {
		String query = "INSERT INTO de_db.monsters(species, intelligence, status) VALUES(?, ?, ?)";
		try (
			Connection conn = DataBaseLoader.connect();
			PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
		) {
			pstmt.setString(1, monster.getSpecies());
			pstmt.setInt(2, monster.getIntelligence());
			pstmt.setString(3, monster.getStatusToString());
			pstmt.executeUpdate();

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int id = generatedKeys.getInt(1);
					return findById(id);
				} else {
					throw new SQLException("Could not retrieve id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Monster updateStatus(int id, MonsterStatus status) {
		String query = "UPDATE de_db.monsters SET status = ? WHERE id = ?";
		try (
			Connection conn = DataBaseLoader.connect();
			PreparedStatement pstmt = conn.prepareStatement(query)
		) {
			pstmt.setString(1, status.getStatus());
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
			return findById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Monster> getMonstersByStatus(MonsterStatus status) {
		ArrayList<Monster> monsters = new ArrayList<>();
		String query = "SELECT * FROM de_db.monsters WHERE status = ?";
		try (
			Connection conn = DataBaseLoader.connect();
			PreparedStatement pstmt = conn.prepareStatement(query)
		) {
			pstmt.setString(1, status.getStatus().toLowerCase());
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					monsters.add(new Monster(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return monsters;
	}

	public Monster findById(int id) {
		String query = "SELECT * FROM de_db.monsters WHERE id = ? LIMIT 1";
		try (
			Connection conn = DataBaseLoader.connect();
			PreparedStatement pstmt = conn.prepareStatement(query)
		) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Monster(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Monster update(int id, Monster monster) {
		String query = "UPDATE de_db.monsters SET species = ?, intelligence = ?, status = ? WHERE id = ?";
		try (
			Connection conn = DataBaseLoader.connect();
			PreparedStatement pstmt = conn.prepareStatement(query)
		) {
			pstmt.setString(1, monster.getSpecies());
			pstmt.setInt(2, monster.getIntelligence());
			pstmt.setString(3, monster.getStatusToString().toLowerCase());
			pstmt.setInt(4, id);
			pstmt.executeUpdate();
			return findById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
