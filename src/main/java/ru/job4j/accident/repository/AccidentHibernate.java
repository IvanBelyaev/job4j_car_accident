package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
public class AccidentHibernate {
    private SessionFactory sf;

    public AccidentHibernate(SessionFactory sessionFactory) {
        this.sf = sessionFactory;
    }

    public void saveAccident(Accident accident) {
        txConsumer(
                session -> session.saveOrUpdate(accident)
        );
    }

    public List<Accident> getAllAccidents() {
        return txFunction(
                session -> session.createQuery("select distinct a from Accident a join fetch a.type join fetch a.rules", Accident.class)
                        .list()
        );
    }

    public Accident getAccidentById(int id) {
        return txFunction(
                session -> session.createQuery(
                        "from Accident a join fetch a.type join fetch a.rules where a.id = :id", Accident.class)
                        .setParameter("id", id)
                        .uniqueResult()
        );
    }

    public List<AccidentType> getAllAccidentTypes() {
        return txFunction(
                session -> session.createQuery("from AccidentType", AccidentType.class).list()
        );
    }

    public AccidentType getAccidentTypeById(int id) {
        return txFunction(
                session -> session.createQuery("from AccidentType where id = :id", AccidentType.class)
                        .setParameter("id", id)
                        .uniqueResult()
        );
    }

    public List<Rule> getAllRules() {
        return txFunction(
                session -> session.createQuery("from Rule", Rule.class).list()
        );
    }

    public Rule getRuleById(int id) {
        return txFunction(
                session -> session.createQuery("from Rule where id = :id", Rule.class)
                .setParameter("id", id)
                .uniqueResult()
        );
    }

    private <T> T txFunction(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private void txConsumer(Consumer<Session> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            command.accept(session);
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
