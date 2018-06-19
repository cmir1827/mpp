package com.repositories;

import com.model.Game;
import com.model.Intrebare;
import com.model.TSUser;
import com.model.TestCultura;
import com.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class GameRepository implements IRepository<Integer, Game> {

    private JdbcUtils dbUtils;

    public GameRepository(Properties properties){
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {
        assert false : "Not implemented";
        return 0;
    }

    @Override
    public void save(Game entity) {
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement("insert into Game values (0,?,?,?)")) {
            preStmt.setInt(1, entity.getUser().getUserId());
            preStmt.setInt(2, entity.getTestCultura().getId());
            preStmt.setInt(3,entity.getPunctaj());

            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public void delete(Integer integer) {
        assert false : "Not implemented";
    }

    @Override
    public void update(Integer integer, Game entity) {
        assert false : "Not implemented";
    }

    @Override
    public Game findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Game where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    int idUser = result.getInt("idUser");
                    int idTest = result.getInt("idTest");
                    int punctaj = result.getInt("Punctaj");

                    TestHBNRepository testHBNRepository = new TestHBNRepository();
                    TestCultura testCultura = testHBNRepository.findOne(idTest);

                    UserRepo userRepo = new UserRepo(JdbcUtils.getProps());
                    TSUser user = userRepo.findOne(idUser);


                    Game game = new Game(id,user,testCultura,punctaj);


                    return game;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<Game> findAll() {
        Connection con=dbUtils.getConnection();
        List<Game> games =new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Game")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("id");
                    int idUser = result.getInt("idUser");
                    int idTest = result.getInt("idTest");
                    int punctaj = result.getInt("Punctaj");

                    TestHBNRepository testHBNRepository = new TestHBNRepository();
                    TestCultura testCultura = testHBNRepository.findOne(idTest);

                    UserRepo userRepo = new UserRepo(JdbcUtils.getProps());
                    TSUser user = userRepo.findOne(idUser);


                    Game game = new Game(id,user,testCultura,punctaj);

                    games.add(game);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return games;
    }
}
