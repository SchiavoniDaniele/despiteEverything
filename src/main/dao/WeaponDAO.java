package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.models.Weapon;
import utils.DataBaseLoader;

public class WeaponDAO {

	public Weapon save(Weapon weapon) {
		String query = "INSERT INTO de_db.weapons(name, price, durability, rarity, max_durability) VALUES(?, ?, ?, ?, ?)";
		try (Connection conn = DataBaseLoader.connect();
		     PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, weapon.getName());
			pstmt.setInt(2, weapon.getPrice());
			pstmt.setInt(3, weapon.getDurability());
			pstmt.setInt(4, weapon.getLevel());
			pstmt.setInt(5, weapon.getMaxDurability());
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

	public Weapon use(Weapon weapon) {
		String query = "UPDATE de_db.weapons SET durability = ? WHERE id = ?";
		try (Connection conn = DataBaseLoader.connect();
		     PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, weapon.getDurability() - 1);
			pstmt.setInt(2, weapon.getId());
			pstmt.executeUpdate();
			return findById(weapon.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Weapon findById(int id) {
		String query = "SELECT * FROM de_db.weapons WHERE id = ? LIMIT 1";
		try (Connection conn = DataBaseLoader.connect();
		     PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Weapon(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
