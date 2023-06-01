package src.utils;

import src.model.entities.Department;
import src.model.entities.Seller;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utils {
    public static Department instanceDepartment(ResultSet rs) throws SQLException {
        Department department = new Department(rs.getInt("id"), rs.getString("name"));
        return department;
    }

    public static Seller instanceSeller(ResultSet rs, Department department) throws SQLException {
        Seller seller = new Seller(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                rs.getDate("birthDate"), rs.getDouble("baseSalary"), department);
        return seller;
    }

}
