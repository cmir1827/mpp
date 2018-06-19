package com.repositories;

import com.model.Intrebare;
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
public class IntrebareRepository implements IRepository<Integer, Intrebare>{
    private JdbcUtils dbUtils;

    public IntrebareRepository(Properties properties){
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {
        assert false : "Not implemented";
        return 0;
    }

    @Override
    public void save(Intrebare entity) {
        assert false : "Not implemented";

    }

    @Override
    public void delete(Integer integer) {
        assert false : "Not implemented";

    }

    @Override
    public void update(Integer integer, Intrebare entity) {
        assert false : "Not implemented";

    }

    @Override
    public Intrebare findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Intrebare where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String content = result.getString("content");
                    String correctAnswer = result.getString("correctAnswer");
                    int idTest = result.getInt("idTest");

                    TestHBNRepository testHBNRepository = new TestHBNRepository();
                    TestCultura testCultura = testHBNRepository.findOne(idTest);

                    Intrebare intrebare = new Intrebare(content,correctAnswer,testCultura);


                    return intrebare;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<Intrebare> findAll() {
        Connection con=dbUtils.getConnection();
        List<Intrebare> intrebari =new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Intrebare")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("id");
                    String content = result.getString("content");
                    String correctAnswer = result.getString("correctAnswer");
                    int idTest = result.getInt("idTest");

                    TestHBNRepository testHBNRepository = new TestHBNRepository();
                    TestCultura testCultura = testHBNRepository.findOne(idTest);

                    Intrebare intrebare = new Intrebare(id,content,correctAnswer,testCultura);



                    intrebari.add(intrebare);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return intrebari;
    }
}
