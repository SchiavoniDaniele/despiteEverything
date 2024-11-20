package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import main.models.Book;
import main.models.Monster;
import main.models.Rehab;
import utils.DataBaseLoader;

public class RehabDAO {
    public Rehab save(Rehab rehab) {
        String query = rehab.getUsedBook() != null ?
            "INSERT INTO de_db.rehabs(name, max_capacity, guests, price, is_working, book_id) VALUES(?,?,?,?,?,?)" :
            "INSERT INTO de_db.rehabs(name, max_capacity, guests, price, is_working) VALUES(?,?,?,?,?)";
        try (Connection conn = DataBaseLoader.connect();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, rehab.getName());
            pstmt.setInt(2, rehab.getDimension());
            pstmt.setInt(3, rehab.getMonsters().size());
            pstmt.setInt(4, rehab.getPrice());
            if (rehab.getUsedBook() != null) {
                pstmt.setInt(6, rehab.getUsedBook().getId());
            }
            pstmt.setBoolean(5,rehab.isWorking());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return findById(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Could not retrieve id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<Monster> getMonstersFromRehab(Rehab rehab) {
        return getMonstersFromRehab(rehab.getId());
    }

    public ArrayList<Monster> getMonstersFromRehab(int id) {
        ArrayList<Monster> guests = new ArrayList<>();
        String query = "SELECT monster_id FROM de_db.monster_to_rehab WHERE rehab_id=?";
        try (Connection conn = DataBaseLoader.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                MonsterDAO monsterDAO = new MonsterDAO();
                while (rs.next()) {
                    guests.add(monsterDAO.findById(rs.getInt("monster_id")));
                }
            }
            return guests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<Rehab> getAllRehabs() {
        ArrayList<Rehab> rehabs = new ArrayList<>();
        String query = "SELECT * FROM de_db.rehabs";
        try (Connection conn = DataBaseLoader.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                rehabs.add(new Rehab(rs));
            }
            return rehabs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Rehab setMonsters(ArrayList<Monster> guests, Rehab rehab) {
        String query = "INSERT INTO de_db.monster_to_rehab (monster_id, rehab_id, entry_time, exit_time, book_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseLoader.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(2, rehab.getId());
            for (int i = 0; i < guests.size() && i < rehab.getDimension(); i++) {
                Monster guest = guests.get(i);
                pstmt.setInt(1, guest.getId());
                pstmt.setInt(5, rehab.getUsedBook().getId());
                Timestamp entryTime = Timestamp.valueOf(LocalDateTime.now());
                Timestamp exitTime = Timestamp.valueOf(LocalDateTime.now().plusMinutes(rehab.getUsedBook().getMinutesToConvert()));
                pstmt.setTimestamp(3, entryTime);
                pstmt.setTimestamp(4, exitTime);
                pstmt.executeUpdate();
                System.out.println("Mostro inserito in monster_to_rehab");
            }
            return findById(rehab.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Rehab setBook(Rehab rehab, Book book) {
        String query = "UPDATE de_db.rehabs SET book_id=? WHERE id=?";
        try (Connection conn = DataBaseLoader.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, book.getId());
            pstmt.setInt(2, rehab.getId());
            pstmt.executeUpdate();
            return findById(rehab.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Rehab findById(int id) {
        String query = "SELECT * FROM de_db.rehabs WHERE id=? LIMIT 1";
        try (Connection conn = DataBaseLoader.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Rehab(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getTopPrice() {
        String query = "SELECT price FROM de_db.rehabs ORDER BY id DESC LIMIT 1";
        try (Connection conn = DataBaseLoader.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
            	int topPrice=(int)(rs.getInt("price")*1.5);
                return topPrice;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 10; // Default value if no price is found
    }
    
    public Rehab setWorking(boolean isWorking, Rehab rehab) {
    	String query="UPDATE de_db.rehabs SET is_working=? WHERE id=?";
    	try(Connection conn=DataBaseLoader.connect();
    			PreparedStatement pstmt=conn.prepareStatement(query)){
    		pstmt.setBoolean(1, isWorking);
    		pstmt.setInt(2,rehab.getId());
    		pstmt.executeUpdate();
    		return findById(rehab.getId());
    	}catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
