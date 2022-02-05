package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbnRun {
    public static void main(String[] args) {
        Candidate rsl = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            VacanciesBase base = VacanciesBase.of("java candidates");
            base.addVacancy(Vacancy.of("Junior Java Developer", 1000));
            base.addVacancy(Vacancy.of("Middle Java Developer", 2000));
            base.addVacancy(Vacancy.of("Senior Java Developer", 3000));
            session.save(Candidate.of("Roman", 3, 1000, base));
            session.save(Candidate.of("Ivan", 5, 2000, base));
            session.save(Candidate.of("Alex", 7, 3000, base));
            rsl = session.createQuery("select distinct c from Candidate c"
                    + " join fetch c.vacanciesBase vb"
                    + " join fetch vb.vacancies v"
                    + " where c.id = :c_id", Candidate.class)
                    .setParameter("c_id", 2)
                    .uniqueResult();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(rsl);
    }
}
