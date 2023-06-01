package src.model.dao;

import src.db.DB;
import src.model.dao.impl.DepartmentDaoJdbc;
import src.model.dao.impl.SellerDaoJdbc;

public class DaoFactory {
    public static SellerDao createSellerDao() {
        return new SellerDaoJdbc(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJdbc(DB.getConnection());
    }
}