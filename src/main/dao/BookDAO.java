package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.models.Book;
import utils.DataBaseLoader;

public class BookDAO {
	public Book save(Book book) {
		String query = "INSERT INTO de_db.books(name,price,conversion_rate,level) VALUES(?,?,?,?)";
		try (
			Connection conn = DataBaseLoader.connect();
			PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
		) {
			pstmt.setString(1, book.getName());
			pstmt.setInt(2, book.getPrice());
			pstmt.setInt(3, book.getMinutesToConvert());
			pstmt.setInt(4, book.getLevel());
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

	public Book findById(int id) {
		String query = "SELECT * FROM de_db.books WHERE id=? LIMIT 1";
		try (
			Connection conn = DataBaseLoader.connect();
			PreparedStatement pstmt = conn.prepareStatement(query)
		) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Book(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
