package com.repositories;

import com.model.Masina;
import com.model.MasinaPunctControl;
import com.model.PunctControl;
import com.util.JdbcUtils;
import org.apache.tomcat.jni.Local;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MasinaPunctControlRepository implements IRepository<Integer, MasinaPunctControl>{
    private JdbcUtils dbUtils;

    public MasinaPunctControlRepository(Properties properties){
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {
        assert false : "Not implemented";
        return 0;
    }

    @Override
    public void save(MasinaPunctControl entity) {
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement("insert into MasinaPunctControl values (0,?,?,?)")) {
            preStmt.setInt(1, entity.getMasina().getId());
            preStmt.setInt(2, entity.getPunctControl().getId());
            preStmt.setDate(3, Date.valueOf(entity.getTimpTrecere()));

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
    public void update(Integer integer, MasinaPunctControl entity) {
        assert false : "Not implemented";

    }

    @Override
    public MasinaPunctControl findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from MasinaPunctControl where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    int idMasina = result.getInt("idMasina");
                    int idPunctControl = result.getInt("idPunctControl");
                    LocalDate timpTrecere = result.getDate("timpTrecere").toLocalDate();

                    MasinaHBNRepositpry masinaHBNRepositpry = new MasinaHBNRepositpry();
                    Masina masina = masinaHBNRepositpry.findOne(idMasina);

                    PunctControlRepository punctControlRepository = new PunctControlRepository(JdbcUtils.getProps());
                    PunctControl punctControl = punctControlRepository.findOne(idPunctControl);

                    MasinaPunctControl masinaPunctControl = new MasinaPunctControl(id, masina, punctControl, timpTrecere);


                    return masinaPunctControl;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<MasinaPunctControl> findAll() {
        Connection con=dbUtils.getConnection();
        List<MasinaPunctControl> masinaPunctControls =new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from MasinaPunctControl")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("id");
                    int idMasina = result.getInt("idMasina");
                    int idPunctControl = result.getInt("idPunctControl");
                    LocalDate timpTrecere = result.getDate("timpTrecere").toLocalDate();

                    MasinaHBNRepositpry masinaHBNRepositpry = new MasinaHBNRepositpry();
                    Masina masina = masinaHBNRepositpry.findOne(idMasina);

                    PunctControlRepository punctControlRepository = new PunctControlRepository(JdbcUtils.getProps());
                    PunctControl punctControl = punctControlRepository.findOne(idPunctControl);

                    MasinaPunctControl masinaPunctControl = new MasinaPunctControl(id, masina, punctControl, timpTrecere);

                    masinaPunctControls.add(masinaPunctControl);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return masinaPunctControls;
    }
}
