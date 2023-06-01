package src.model.dao.impl;

import src.db.DB;
import src.db.exceptions.DbException;
import src.model.dao.SellerDao;
import src.model.entities.Department;
import src.model.entities.Seller;
import src.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellerDaoJdbc implements SellerDao {
    private Connection conn = null;

    @Override
    public void createTable() {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("CREATE TABLE IF NOT EXISTS sellers (id int(11) NOT NULL AUTO_INCREMENT," +
                    "name varchar(60) NOT NULL," +
                    "email varchar(100) NOT NULL," +
                    "birthDate datetime NOT NULL," +
                    "baseSalary double NOT NULL," +
                    "department_id int(11) NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (department_id) REFERENCES departments (id))");
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    public SellerDaoJdbc(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM sellers WHERE id= ? ";
        PreparedStatement st = null;

        try {

            st = conn.prepareStatement(SQL);
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller get(Integer id) {

        String SQL = "SELECT sellers.*, departments.name as department_name FROM sellers INNER JOIN departments ON sellers.department_id  = departments.id  WHERE sellers.id  = ?;";

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(SQL);
            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                Department department = new Department(rs.getInt("department_id"), rs.getString("department_name"));
                Seller seller = Utils.instanceSeller(rs, department);
                return seller;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

    }

    @Override
    public List<Seller> getAll() {
        String SQL = "SELECT sellers.*, departments.name as department_name FROM sellers INNER JOIN departments ON sellers.department_id  = departments.id ";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Seller> list = new ArrayList<>();
        try {
            st = conn.prepareStatement(SQL);
            rs = st.executeQuery();
            while (rs.next()) {
                Department department = new Department(rs.getInt("department_id"), rs.getString("department_name"));
                Seller seller = Utils.instanceSeller(rs, department);
                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);

        }
    }

    @Override
    public Seller insert(String name, String email, Date birthDate, Double baseSalary, Department department) {
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement("INSERT INTO sellers (name, email,birthDate, baseSalary, department_id)" +
                    "VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setDate(3, new java.sql.Date(birthDate.getTime()));
            statement.setDouble(4, baseSalary);
            statement.setInt(5, department.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    Seller seller = new Seller(rs.getInt(1), name, email, birthDate, baseSalary, department);
                    return seller;
                }
                DB.closeResultSet(rs);
            }
            throw new DbException("error: Failed to create seller");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public Connection getConnection() {
        return conn;
    }

    @Override
    public Seller update(int id, String name, String email, Date birthDate, Double BaseSalary,
            Department department) {
        String SQL = "UPDATE sellers SET name=?, email=?, birthDate=?, baseSalary=?, department_id=? WHERE id=?";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            Seller seller = get(id);
            st = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            if (name != null) {
                st.setString(1, name);
            } else {
                st.setString(1, seller.getName());
            }

            if (email != null) {
                st.setString(2, email);
            } else {
                st.setString(2, seller.getEmail());
            }

            if (birthDate != null) {
                st.setDate(3, new java.sql.Date(birthDate.getTime()));
            } else {
                st.setDate(3, new java.sql.Date(seller.getBirth_date().getTime()));
            }

            if (BaseSalary != null) {
                st.setDouble(4, BaseSalary);
            } else {
                st.setDouble(4, seller.getBase_salary());
            }

            if (department != null) {
                st.setInt(5, department.getId());
            } else {
                st.setInt(5, seller.getDepartment().getId());
            }

            st.setInt(6, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                return get(id);
            }
            throw new DbException("error: update failed");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}