package src.model.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import src.model.entities.Department;
import src.model.entities.Seller;

public interface SellerDao {
    Seller insert(String name, String email, Date birthDate, Double BaseSalary, Department department);

    Seller update(int id, String name, String email, Date birthDate, Double BaseSalary, Department department);

    void delete(Integer id);

    Seller get(Integer id);

    List<Seller> getAll();

    void createTable();

    Connection getConnection();
}
