package src.model.entities;

import java.util.Date;

import java.util.Objects;

public class Seller {

    private Integer id;
    private String name;
    private String email;
    private Date birth_date;
    private Double base_salary;
    private Department department;

    public Seller(Integer id, String name, String email, Date birth_date, Double base_salary, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth_date = birth_date;
        this.base_salary = base_salary;
        this.department = department;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth_date() {
        return this.birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Double getBase_salary() {
        return this.base_salary;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Seller)) {
            return false;
        }
        Seller seller = (Seller) o;
        return Objects.equals(id, seller.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", email='" + getEmail() + "'" +
                ", birth_date='" + getBirth_date() + "'" +
                ", base_salary='" + getBase_salary() + "'" +
                ", department='" + getDepartment() + "'" +
                "}";
    }

}
