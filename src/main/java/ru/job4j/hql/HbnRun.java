package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbnRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            session.save(Candidate.of("Roman", 3, 1000));
            session.save(Candidate.of("Ivan", 5, 2000));
            session.save(Candidate.of("Alex", 7, 3000));
            session.createQuery("from ru.job4j.hql.Candidate")
                    .getResultList()
                    .forEach(System.out::println);
            System.out.println(session.createQuery("from ru.job4j.hql.Candidate c where c.id = :c_id")
                    .setParameter("c_id", 3)
                    .uniqueResult());
            session.createQuery("from ru.job4j.hql.Candidate c where c.name = :c_name")
                    .setParameter("c_name", "Ivan").list()
                    .forEach(System.out::println);
            session.createQuery("update ru.job4j.hql.Candidate c set c.experience = :c_exp, c.salary = :c_slr where c.id = :c_id")
                    .setParameter("c_exp", 15)
                    .setParameter("c_slr", 5000)
                    .setParameter("c_id", 1)
                    .executeUpdate();
            session.createQuery("delete from ru.job4j.hql.Candidate c where c.id = :c_Id")
                    .setParameter("c_Id", 2)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
