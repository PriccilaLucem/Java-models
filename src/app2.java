package src;

import src.model.dao.DaoFactory;
import src.model.dao.DepartmentDao;
import src.model.entities.Department;

public class app2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        departmentDao.createTable();

        System.out.println("Teste 1");
        System.out.println(departmentDao.get(1));

        System.out.println("-------------------");
        System.out.println("Teste 2");
        System.out.println("-------------------");

        System.out.print(departmentDao.getAll());

        System.out.println("-------------------");
        System.out.println("Teste 3");
        System.out.println("-------------------");

        Department department = departmentDao.insert("D1");
        System.out.println(department);

        departmentDao.delete(department.getId());

        System.out.println("-------------------");
        System.out.println("teste 4");
        System.out.println("-------------------");

        System.out.println(departmentDao.update(1, "maria"));
    }
}
