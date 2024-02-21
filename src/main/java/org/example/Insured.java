package org.example;


public class Insured {


    private int id;

    private String name;
    private String surname;
    private String birthDate;
    private String address;
    private String bankAccountNumber;


    public Insured(int id, String name, String surname, String birthDate, String address, String bankAccountNumber) {
        this.id =  id ;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.address = address;
        this.bankAccountNumber = bankAccountNumber;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }


    @Override
    public String toString() {
        return STR."ID: \{id}, Jméno: \{name}, Příjmení: \{surname}, Datum narození: \{birthDate}, Adresa: \{address}, Číslo bankovního účtu: \{bankAccountNumber}" ;
    }

    public int findForTheInsuredById(int idToFind) {
        return idToFind;
    }
}
