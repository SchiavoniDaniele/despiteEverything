package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.models.Scepter;
import utils.DataBaseLoader;

public class ScepterDAO {

	public Scepter save(Scepter scepter) {
		String query = "INSERT INTO de_db.scepters(name, price, magic_power, level) VALUES(?, ?, ?, ?)";
		try (Connection conn = DataBaseLoader.connect();
		     PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, scepter.getName());
			pstmt.setInt(2, scepter.getPrice());
			pstmt.setInt(3, scepter.getPower());
			pstmt.setInt(4, scepter.getLevel());
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

	public Scepter losePower(Scepter scepter) {
		String query = "UPDATE de_db.scepters SET magic_power = ? WHERE id = ?";
		int newPower = (int) (scepter.getPower() * 0.8);
		try (Connection conn = DataBaseLoader.connect();
		     PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, newPower);
			pstmt.setInt(2, scepter.getId());
			pstmt.executeUpdate();
			return findById(scepter.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Scepter findById(int id) {
		String query = "SELECT * FROM de_db.scepters WHERE id = ? LIMIT 1";
		try (Connection conn = DataBaseLoader.connect();
		     PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Scepter(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
