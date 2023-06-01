package src;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import src.db.DB;
import src.model.dao.DaoFactory;
import src.model.dao.DepartmentDao;
import src.model.dao.SellerDao;
import src.model.entities.Department;
import src.model.entities.Seller;

public class app {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        departmentDao.createTable();
        sellerDao.createTable();

        Seller seller = sellerDao.get(sc.nextInt());
        System.out.println(seller);

        System.out.println("-----------------------------------");
        System.out.println("TESTE 2 ");

        System.out.println("-----------------------------------");

        List<Seller> sellers = sellerDao.getAll();
        System.out.println(sellers);

        System.out.println("-----------------------------------");
        System.out.println("Teste 3");
        System.out.println("-----------------------------------");

        Seller seller2 = sellerDao.insert("jose", "jose@gmail.com", new Date(), 2000.00,
                new Department(2, "Eletronics"));

        System.out.println(seller2);

        sellerDao.delete(seller2.getId());

        System.out.println("-----------------------------------");
        System.out.println("Teste 4");
        System.out.println("-----------------------------------");

        Seller seller3 = sellerDao.update(1, "maria", "maria@gmail.com", null, 3000.00, null);
        System.out.println(seller3);

        DB.closeConnection(sellerDao.getConnection());
        sc.close();

    }
}
