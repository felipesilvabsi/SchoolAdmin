/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.open.schooladmin.dao;

import br.com.open.schooladmin.modelo.Estado;
import br.com.open.schooladmin.persistencia.AbstractDao;
import br.com.open.schooladmin.persistencia.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe Silva
 */
public class EstadoDAO extends AbstractDao<Estado>{

    public EstadoDAO(boolean owner) {
        super(owner);
    }
    
    @Override
    public long save(Estado obj) throws DaoException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = getConnection();

            this.beginTransaction();

            String sql = "INSERT INTO estado (sigla, nome) VALUES (?, ?)";

            stm = con.prepareStatement(sql);
            stm.setString(1, obj.getSigla());
            stm.setString(2, obj.getNome());

            stm.executeUpdate();

            this.commitTransaction();

        } catch (SQLException e) {
            try {
                this.rollbackTransaction();
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        } finally {
            this.free(con, stm, null, null);
        }
        return 0;
    }

    @Override
    public void saveAll(List<Estado> objs) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Estado obj) throws DaoException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = getConnection();

            this.beginTransaction();

            String sql = "UPDATE estado SET sigla = ?, nome = ? WHERE sigla = ?";

            stm = con.prepareStatement(sql);
            stm.setString(1, obj.getSigla());
            stm.setString(2, obj.getNome());
            stm.setString(3, obj.getSigla());

            stm.executeUpdate();

            this.commitTransaction();

        } catch (SQLException e) {
            try {
                this.rollbackTransaction();
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        } finally {
            this.free(con, stm, null, null);
        }
    }

    @Override
    public void delete(Estado obj) throws DaoException {
        Connection con = null;
        PreparedStatement stm = null;

        try {

            con = getConnection();
            this.beginTransaction();

            String sql = "DELETE FROM estado WHERE sigla = ?";

            stm = con.prepareStatement(sql);
            stm.setString(1, obj.getSigla());

            stm.executeUpdate();

            this.commitTransaction();

        } catch (SQLException e) {
            try {
                this.rollbackTransaction();
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        } finally {
            this.free(con, stm, null, null);
        }
    }

    @Override
    public void deleteByParams(HashMap<String, Object> params) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Estado> findAll() throws DaoException {
        List<Estado> lista = new ArrayList<Estado>();
        Estado estado;

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;

        try {

            con = getConnection();

            stm = con.createStatement();
            String sql = "SELECT * FROM estado ORDER BY sigla";

            rs = stm.executeQuery(sql);

            while (rs.next()) {
                estado = new Estado();
                estado.setSigla(rs.getString("sigla"));
                estado.setNome(rs.getString("nome"));

                lista.add(estado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        } finally {
            this.free(con, null, stm, rs);
        }
        return lista;
    }

    @Override
    public List<Estado> findByParams(HashMap<String, Object> params) throws DaoException {
        List<Estado> lista = new ArrayList<Estado>();
        Estado estado;

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            con = getConnection();

            String sql = "SELECT * FROM estado";

            stm = this.getPreparedStatementByHashMap(con, sql, params);

            rs = stm.executeQuery();

            while (rs.next()) {
                estado = new Estado();
                estado.setSigla(rs.getString("sigla"));
                estado.setNome(rs.getString("nome"));

                lista.add(estado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        } finally {
            this.free(con, null, stm, rs);
        }
        return lista;
    }

    @Override
    public Estado findById(long id) throws DaoException {
        throw new UnsupportedOperationException("Não Implementado");
    }

    @Override
    public void clean() throws DaoException {
        //
    }

    @Override
    public int getCount() throws DaoException {
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;

        try {

            con = getConnection();

            stm = con.createStatement();
            String sql = "SELECT * FROM estado";

            rs = stm.executeQuery(sql);

            if (rs.next()) {
                //rs.last();
                return rs.getRow();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        } finally {
            this.free(con, null, stm, rs);
        }

        return 0;
    }
    
    public Estado findBySigla(String sigla) throws DaoException{
        Estado estado = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT * FROM estado WHERE sigla = ?";

            con = getConnection();

            stm = con.prepareStatement(sql);

            stm.setString(1, sigla);

            rs = stm.executeQuery();

            if (rs.next()) {

                estado = new Estado();
                estado.setSigla(rs.getString("sigla"));
                estado.setNome(rs.getString("nome"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        } finally {
            this.free(con, null, stm, rs);
        }

        return estado;
    }
    
}
