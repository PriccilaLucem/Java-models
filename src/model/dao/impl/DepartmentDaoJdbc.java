package src.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.db.DB;
import src.db.exceptions.DbException;
import src.model.dao.DepartmentDao;
import src.model.entities.Department;
import src.utils.Utils;

public class DepartmentDaoJdbc implements DepartmentDao {

    private Connection conn = null;

    public DepartmentDaoJdbc(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void createTable() {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("CREATE TABLE IF NOT EXISTS departments (" +
                    "id int(11) NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(60) NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ");");
            st.execute();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM departments WHERE id=?";
        PreparedStatement st = null;
        try {

            st = conn.prepareStatement(SQL);
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Department get(Integer id) {
        String SQL = "SELECT * from departments where id=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(SQL);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Department department = Utils.instanceDepartment(rs);
                return department;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Department> getAll() {
        String SQL = "SELECT * from departments";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Department> list = new ArrayList<>();
        try {
            st = conn.prepareStatement(SQL);
            rs = st.executeQuery();

            while (rs.next()) {
                Department department = Utils.instanceDepartment(rs);
                list.add(department);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Department insert(String name) {
        String SQL = "INSERT INTO departments (name) VALUES (?)";
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);

            int rows = st.executeUpdate();
            if (rows > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    Department department = new Department(rs.getInt(1), name);
                    return department;
                }
            }
            throw new DbException("error: Failed to insert into db");

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public Department update(Integer id, String name) {
        String sql = "UPDATE departments SET name=? WHERE id=?";
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, name);
            st.setInt(2, id);
            st.executeUpdate();

            return new Department(id, name);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
