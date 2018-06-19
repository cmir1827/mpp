package com.repositories;

import com.model.TSUser;
import com.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepo implements IRepository<Integer, TSUser> {
    private JdbcUtils dbUtils;

    @Override
    public void update(Integer integer, TSUser entity) {
        //
    }

    public UserRepo(Properties props){
        dbUtils=new JdbcUtils(props);
    }
    public UserRepo(JdbcUtils utils){
        dbUtils=utils;
    }


    @Override
    public int size() {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from User")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(TSUser entity) {
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement("insert into User values (0,?,?)")) {
            preStmt.setString(1, entity.getUsername());
            preStmt.setString(2, entity.getPassword());

            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public void delete(Integer integer) {
        //
    }



    @Override
    public TSUser findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from User where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("username");
                    String password = result.getString("password");

                    TSUser usr = new TSUser(id,name,password);

                    return usr;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }


    public TSUser findUserName(String username) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from User where username=?")){
            preStmt.setString(1,username);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("username");
                    String password = result.getString("password");

                    TSUser usr = new TSUser(id,name,password);

                    return usr;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<TSUser> findAll() {
        Connection con=dbUtils.getConnection();
        List<TSUser> users =new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from User")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("username");
                    String password = result.getString("password");

                    TSUser usr = new TSUser(id,name,password);


                    users.add(usr);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return users;
    }

}
