package src.model.dao;

import java.util.List;

import src.model.entities.Department;

public interface DepartmentDao {

    Department insert(String name);

    Department update(Integer id, String name);

    void delete(Integer id);

    Department get(Integer id);

    List<Department> getAll();

    void createTable();

}
