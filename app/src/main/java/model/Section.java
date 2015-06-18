package model;

import model.Product.Age;
import model.Product.Gender;

public enum Section {
    Hombres(Age.Adulto  , Gender.Masculino),
    Mujeres(Age.Adulto  , Gender.Femenino),
    Chicos (Age.Infantil, Gender.Masculino),
    Chicas (Age.Infantil, Gender.Femenino);

    public Age age;
    public Gender gender;

    Section(Age age, Gender gender) {
        this.age    = age;
        this.gender = gender;
    }
}
