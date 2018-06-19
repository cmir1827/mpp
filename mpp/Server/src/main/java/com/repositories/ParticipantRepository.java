//package com.repositories;
//
//import com.model.Participant;
//import com.util.JdbcUtils;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
///**
// * Created by sergiubulzan on 21/06/2017.
// */
//public class ParticipantRepository implements IRepository<Integer, Participant> {
//
//    private JdbcUtils dbUtils;
//
//    public ParticipantRepository(Properties properties){
//        dbUtils = new JdbcUtils(properties);
//    }
//
//    @Override
//    public int size() {
//        Connection con=dbUtils.getConnection();
//        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Participant")) {
//            try(ResultSet result = preStmt.executeQuery()) {
//                if (result.next()) {
//                    return result.getInt("SIZE");
//                }
//            }
//        }catch(SQLException ex){
//            System.out.println("Error DB "+ex);
//        }
//        return 0;
//    }
//
//    @Override
//    public void save(Participant entity) {
//        Connection con = dbUtils.getConnection();
//
//        try (PreparedStatement preStmt = con.prepareStatement("insert into Participant values (0,?,?)")) {
//            preStmt.setString(1, entity.getNume());
//            preStmt.setString(2, entity.getStatus());
//
//            int result = preStmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println("Error DB " + ex);
//        }
//    }
//
//    @Override
//    public void delete(Integer integer) {
//        Connection con=dbUtils.getConnection();
//        try(PreparedStatement preStmt=con.prepareStatement("delete from Participant where id=?")){
//            preStmt.setInt(1,integer);
//            int result=preStmt.executeUpdate();
//        }catch (SQLException ex){
//            System.out.println("Error DB "+ex);
//        }
//    }
//
//    @Override
//    public void update(Integer integer, Participant entity) {
//        Connection con = dbUtils.getConnection();
//
//        try (PreparedStatement preStmt = con.prepareStatement("UPDATE Participant SET " +
//                "nume=?, participationStatus=?" +
//                "WHERE id = ?" )){
//            preStmt.setString(1, entity.getNume());
//            preStmt.setString(2, entity.getStatus());
//            preStmt.setInt(3, integer);
//
//
//            int result = preStmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println("Error DB " + ex);
//        }
//    }
//
//    @Override
//    public Participant findOne(Integer integer) {
//        Connection con=dbUtils.getConnection();
//
//        try(PreparedStatement preStmt=con.prepareStatement("select * from Participant where id=?")){
//            preStmt.setInt(1,integer);
//            try(ResultSet result=preStmt.executeQuery()) {
//                if (result.next()) {
//                    int id = result.getInt("id");
//                    String nume = result.getString("nume");
//                    String participationStatus = result.getString("participationStatus");
//
//                    Participant participant = new Participant(nume);
//                    participant.setParticipantId(id);
//                    participant.setStatus(participationStatus);
//
//                    return participant;
//                }
//            }
//        }catch (SQLException ex){
//            System.out.println("Error DB "+ex);
//        }
//        return null;
//    }
//
//    @Override
//    public List<Participant> findAll() {
//        Connection con=dbUtils.getConnection();
//        List<Participant> participants =new ArrayList<>();
//        try(PreparedStatement preStmt=con.prepareStatement("select * from Participant")) {
//            try(ResultSet result=preStmt.executeQuery()) {
//                while (result.next()) {
//
//                    int id = result.getInt("id");
//                    String nume = result.getString("nume");
//                    String participationStatus = result.getString("participationStatus");
//
//                    Participant participant = new Participant(nume);
//                    participant.setParticipantId(id);
//
//                    participant.setStatus(participationStatus);
//
//
//                    participants.add(participant);
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error DB "+e);
//        }
//        return participants;
//    }
//}
