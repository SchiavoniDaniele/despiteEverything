package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import main.models.Sayre;
import utils.DataBaseLoader;

public class SayreDAO {

	public Sayre save(Sayre sayre) {
		String query = "INSERT INTO de_db.sayre (game_start, happiness, weapon_id, book_id, scepter_id, coins) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DataBaseLoader.connect()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				Timestamp gameStart = Timestamp.valueOf(sayre.getStartDate());
				pstmt.setTimestamp(1, gameStart);
				pstmt.setInt(2, sayre.getHappiness());
				pstmt.setInt(3, sayre.getWeapon().getId());
				pstmt.setInt(4, sayre.getBook().getId());
				pstmt.setInt(5, sayre.getScepter().getId());
				pstmt.setInt(6, sayre.getCoins());
				pstmt.executeUpdate();
				
				conn.commit();
				
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int id = generatedKeys.getInt(1);
						return findById(id);
					} else {
						throw new SQLException("Could not retrieve id");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Sayre update(int id, Sayre sayre) {
		String query = "UPDATE de_db.sayre SET happiness = ?, coins = ?, weapon_id = ?, scepter_id = ?, book_id = ? WHERE id = ?";
		try (Connection conn = DataBaseLoader.connect();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, sayre.getHappiness());
			pstmt.setInt(2, sayre.getCoins());
			pstmt.setInt(3, sayre.getWeapon() != null ? sayre.getWeapon().getId() : 1);
			pstmt.setInt(4, sayre.getScepter() != null ? sayre.getScepter().getId() : 1);
			pstmt.setInt(5, sayre.getBook() != null ? sayre.getBook().getId() : 1);
			pstmt.setInt(6, id);
			pstmt.executeUpdate();
			return findById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Sayre donateBook(Sayre sayre) {
		String query = "UPDATE de_db.sayre SET book_id = null WHERE id = ?";
		try (Connection conn = DataBaseLoader.connect();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, sayre.getId());
			pstmt.executeUpdate();
			return findById(sayre.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void lose(Sayre sayre) {
		String query = "UPDATE de_db.sayre SET is_playing = false WHERE id = ?";
		try (Connection conn = DataBaseLoader.connect();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, sayre.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void startPlaying(Sayre sayre) {
		String query = "UPDATE de_db.sayre SET is_playing = true WHERE id = ?";
		try (Connection conn = DataBaseLoader.connect();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, sayre.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Sayre findById(int id) {
		String query = "SELECT * FROM de_db.sayre WHERE id = ? LIMIT 1";
		try (Connection conn = DataBaseLoader.connect();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Sayre(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Sayre getPlaying() {
		String query = "SELECT * FROM de_db.sayre WHERE is_playing = true LIMIT 1";
		try (Connection conn = DataBaseLoader.connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {
			if (rs.next()) {
				return new Sayre(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateMoney(int id, int coins) {
		String query = "UPDATE de_db.sayre SET coins = ? WHERE id = ?";
		try (Connection conn = DataBaseLoader.connect();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, coins);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
