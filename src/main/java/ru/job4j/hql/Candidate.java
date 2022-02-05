package ru.job4j.hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int experience;

    private int salary;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "base_id")
    private VacanciesBase vacanciesBase;

    public static Candidate of(String name, int experience, int salary, VacanciesBase base) {
        Candidate candidate = new Candidate();
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        candidate.vacanciesBase = base;
        return candidate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public VacanciesBase getVacanciesBase() {
        return vacanciesBase;
    }

    public void setVacanciesBase(VacanciesBase vacanciesBase) {
        this.vacanciesBase = vacanciesBase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Candidate: id=%s, name=%s, experience=%s, salary=%s, vacanciesBase=%s", id, name, experience, salary, vacanciesBase);
    }
}
