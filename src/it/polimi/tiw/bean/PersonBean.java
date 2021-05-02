package it.polimi.tiw.bean;

public class PersonBean {
    private String idperson;
    private String name;
    private String lastname;
    private String city;

    public PersonBean(){}

    public PersonBean(String idperson, String name, String lastname, String city) {
        this.idperson = idperson;
        this.name = name;
        this.lastname = lastname;
        this.city = city;
    }

    public String getIdperson() {
        return idperson;
    }

    public void setIdperson(String idperson) {
        this.idperson = idperson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
