package com.repositories;

import com.model.PunctControl;
import com.model.TSUser;
import com.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PunctControlRepository implements IRepository<Integer, com.model.PunctControl> {

    private JdbcUtils dbUtils;

    public PunctControlRepository(Properties properties){
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {
        assert false : "Not implemented";
        return 0;
    }

    @Override
    public void save(PunctControl entity) {
        assert false : "Not implemented";
    }

    @Override
    public void delete(Integer integer) {
        assert false : "Not implemented";
    }

    @Override
    public void update(Integer integer, PunctControl entity) {
        assert false : "Not implemented";
    }

    @Override
    public PunctControl findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from PunctControl where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    int idUser = result.getInt("idUser");
                    int numarControl = result.getInt("numarControl");


                    UserRepo userRepo = new UserRepo(JdbcUtils.getProps());
                    TSUser user = userRepo.findOne(idUser);


                    PunctControl punctControl = new PunctControl(id, numarControl, user);


                    return punctControl;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<PunctControl> findAll() {
        Connection con=dbUtils.getConnection();
        List<PunctControl> punctControls =new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from PunctControl")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("id");
                    int idUser = result.getInt("idUser");
                    int numarControl = result.getInt("numarControl");


                    UserRepo userRepo = new UserRepo(JdbcUtils.getProps());
                    TSUser user = userRepo.findOne(idUser);


                    PunctControl punctControl = new PunctControl(id, numarControl, user);


                    punctControls.add(punctControl);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return punctControls;
    }
}
