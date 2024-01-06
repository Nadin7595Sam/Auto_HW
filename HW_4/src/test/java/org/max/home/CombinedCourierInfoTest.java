package org.max.home;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.persistence.PersistenceException;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CombinedCourierInfoTest extends AbstractTest {

    @BeforeAll
    static void setUp() {
        init(); // Вызываем метод инициализации из AbstractTest

        String createTableSQL = "CREATE TABLE IF NOT EXISTS courier_info ("
                + "courier_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "first_name VARCHAR(30) NOT NULL,"
                + "last_name VARCHAR(35) NOT NULL,"
                + "phone_number VARCHAR(20) NOT NULL,"
                + "delivery_type VARCHAR(5) NOT NULL"
                + ")";

        String[] insertQueries = {
                "INSERT INTO courier_info (first_name, last_name, phone_number, delivery_type) VALUES ('John', 'Rython', '+7 960 655 0954', 'foot')",
                "INSERT INTO courier_info (first_name, last_name, phone_number, delivery_type) VALUES ('Kate', 'Looran', '+7 960743 0146', 'car')",
                "INSERT INTO courier_info (first_name, last_name, phone_number, delivery_type) VALUES ('Bob', 'Kolaris', '+7 960 107 7798', 'car')",
                "INSERT INTO courier_info (first_name, last_name, phone_number, delivery_type) VALUES ('Michael', 'Frontal', '+7 960 566 5516', 'car')"
        };

        try (Session session = getSession()) {
            // Создаем таблицу
            session.createSQLQuery(createTableSQL).executeUpdate();

            // Заполняем таблицу данными
            for (String insertQuery : insertQueries) {
                session.createSQLQuery(insertQuery).executeUpdate();
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() throws SQLException {
        close(); // Вызываем метод закрытия из AbstractTest
    }

    @Test
    @Order(1)
    void getCouriers_whenValid_shouldReturn() {
        // given
        final Query<CourierInfoEntity> query = getSession()
                .createSQLQuery("SELECT * FROM courier_info")
                .addEntity(CourierInfoEntity.class);

        // when
        int countTableSize = query.list().size();

        // then
        Assertions.assertEquals(4, countTableSize);
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({"'John', 'Rython', '+7 960 655 0954', 'foot'",
            "'Kate', 'Looran', '+7 960743 0146', 'car'",
            "'Bob', 'Kolaris', '+7 960 107 7798', 'car'",
            "'Michael', 'Frontal', '+7 960 566 5516', 'car'"})
    void getCourierByFullNameAndPhone_whenValid_shouldReturn(String firstName, String lastName, String phoneNumber, String deliveryType) {
        // given
        final Query<CourierInfoEntity> query = getSession()
                .createSQLQuery("SELECT * FROM courier_info WHERE first_name = :firstName AND last_name = :lastName AND phone_number = :phoneNumber AND delivery_type = :deliveryType")
                .addEntity(CourierInfoEntity.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .setParameter("phoneNumber", phoneNumber)
                .setParameter("deliveryType", deliveryType);

        // when
        CourierInfoEntity courier = query.uniqueResult();

        // then
        Assertions.assertNotNull(courier);
        Assertions.assertEquals(firstName, courier.getFirstName());
        Assertions.assertEquals(lastName, courier.getLastName());
        Assertions.assertEquals(phoneNumber, courier.getPhoneNumber());
        Assertions.assertEquals(deliveryType, courier.getDeliveryType());
    }

    @Test
    @Order(3)
    void addCourier_whenValid_shouldSave() {
        // given
        CourierInfoEntity entity = new CourierInfoEntity();
        entity.setFirstName("New");
        entity.setLastName("Courier");
        entity.setPhoneNumber("+7 123 456 7890");
        entity.setDeliveryType("bike");

        // when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();

        final Query<CourierInfoEntity> query = getSession()
                .createSQLQuery("SELECT * FROM courier_info WHERE first_name = 'New' AND last_name = 'Courier'")
                .addEntity(CourierInfoEntity.class);
        CourierInfoEntity newCourier = query.uniqueResult();

        // then
        Assertions.assertNotNull(newCourier);
        Assertions.assertEquals("New", newCourier.getFirstName());
        Assertions.assertEquals("Courier", newCourier.getLastName());
        Assertions.assertEquals("+7 123 456 7890", newCourier.getPhoneNumber());
        Assertions.assertEquals("bike", newCourier.getDeliveryType());
    }

    @Test
    @Order(4)
    void deleteCourier_whenValid_shouldDelete() {
        // given
        final Query<CourierInfoEntity> query = getSession()
                .createSQLQuery("SELECT * FROM courier_info WHERE first_name = 'New' AND last_name = 'Courier'")
                .addEntity(CourierInfoEntity.class);
        CourierInfoEntity courier = query.uniqueResult();
        Assertions.assertNotNull(courier);

        // when
        Session session = getSession();
        session.beginTransaction();
        session.delete(courier);
        session.getTransaction().commit();

        final Query<CourierInfoEntity> queryAfterDelete = getSession()
                .createSQLQuery("SELECT * FROM courier_info WHERE first_name = 'New' AND last_name = 'Courier'")
                .addEntity(CourierInfoEntity.class);
        CourierInfoEntity deletedCourier = queryAfterDelete.uniqueResult();

        // then
        Assertions.assertNull(deletedCourier);
    }

    @Test
    @Order(5)
    void addCourier_whenNotValid_shouldThrow() {
        // given
        CourierInfoEntity entity = new CourierInfoEntity();

        // when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);

        // then
        Assertions.assertThrows(PersistenceException.class, () -> session.getTransaction().commit());
    }
}