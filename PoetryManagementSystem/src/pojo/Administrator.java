package pojo;

import database.DataSelece;

public class Administrator {
    private int id;
    private String account;
    private String password;
    private String name;
    private String telephone;

    public Administrator(int id, String account, String password, String name, String telephone) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.telephone = telephone;
    }

    public Administrator() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public static void main(String[] args) {
        Administrator administrator = DataSelece.findByAdminID(1);

        assert administrator != null;
        System.out.println(administrator.id);
        System.out.println(administrator.account);
        System.out.println(administrator.password);
        System.out.println(administrator.telephone);
        System.out.println(administrator.name);
    }
}
