package org.max.home;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.persistence.PersistenceException;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductsTest extends AbstractTest {

    @BeforeAll
    static void setUp() {
        init();

        // Создание таблицы products и заполнение данными, если она не существует
        String createTableSQL = "CREATE TABLE IF NOT EXISTS products ("
                + "product_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "menu_name VARCHAR(80) NOT NULL,"
                + "price FLOAT NOT NULL"
                + ")";

        // SQL-запросы для заполнения таблицы products
        String[] insertQueries = {
                "INSERT INTO products (menu_name, price) VALUES ('GOJIRA ROLL', 300)",
                "INSERT INTO products (menu_name, price) VALUES ('VIVA LAS VEGAS ROLL', 450)",
                "INSERT INTO products (menu_name, price) VALUES ('FUTOMAKI', 700)",
                "INSERT INTO products (menu_name, price) VALUES ('TOOTSY MAKI', 133)",
                "INSERT INTO products (menu_name, price) VALUES ('ZONIE ROLL', 254)",
                "INSERT INTO products (menu_name, price) VALUES ('NUTTY GRILLED SALAD', 1200)",
                "INSERT INTO products (menu_name, price) VALUES ('SASHIMI SALAD', 1114)",
                "INSERT INTO products (menu_name, price) VALUES ('SUNNY TEA', 456)",
                "INSERT INTO products (menu_name, price) VALUES ('COFFEE', 79)",
                "INSERT INTO products (menu_name, price) VALUES ('MINERAL WATER', 50)"
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
        close();
    }

    @Test
    @Order(1)
    void getProducts_whenValid_shouldReturn() {
        // given
        final Query<ProductsEntity> query = getSession()
                .createSQLQuery("SELECT * FROM products")
                .addEntity(ProductsEntity.class);

        // when
        int countTableSize = query.list().size();

        // then
        Assertions.assertEquals(10, countTableSize);
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({"'GOJIRA ROLL', 300", "'VIVA LAS VEGAS ROLL', 450", "'FUTOMAKI', 700"})
    void getProductByMenuName_whenValid_shouldReturn(String menuName, float expectedPrice) {
        // given
        final Query<ProductsEntity> query = getSession()
                .createSQLQuery("SELECT * FROM products WHERE menu_name = :menuName")
                .addEntity(ProductsEntity.class)
                .setParameter("menuName", menuName);

        // when
        ProductsEntity product = query.uniqueResult();

        // then
        Assertions.assertNotNull(product);
        Assertions.assertEquals(expectedPrice, Float.parseFloat(product.getPrice()));
    }

    @Test
    @Order(3)
    void addProduct_whenValid_shouldSave() {
        // given
        ProductsEntity entity = new ProductsEntity();
        entity.setMenuName("NEW ROLL");
        entity.setPrice("500");

        // when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();

        final Query<ProductsEntity> query = getSession()
                .createSQLQuery("SELECT * FROM products WHERE menu_name = 'NEW ROLL'")
                .addEntity(ProductsEntity.class);
        ProductsEntity newProduct = query.uniqueResult();

        // then
        Assertions.assertNotNull(newProduct);
        Assertions.assertEquals("500", newProduct.getPrice());
    }

    @Test
    @Order(4)
    void deleteProduct_whenValid_shouldDelete() {
        // given
        final Query<ProductsEntity> query = getSession()
                .createSQLQuery("SELECT * FROM products WHERE menu_name = 'NEW ROLL'")
                .addEntity(ProductsEntity.class);
        ProductsEntity product = query.uniqueResult();
        Assertions.assertNotNull(product);

        // when
        Session session = getSession();
        session.beginTransaction();
        session.delete(product);
        session.getTransaction().commit();

        final Query<ProductsEntity> queryAfterDelete = getSession()
                .createSQLQuery("SELECT * FROM products WHERE menu_name = 'NEW ROLL'")
                .addEntity(ProductsEntity.class);
        ProductsEntity deletedProduct = queryAfterDelete.uniqueResult();

        // then
        Assertions.assertNull(deletedProduct);
    }

    @Test
    @Order(5)
    void addProduct_whenNotValid_shouldThrow() {
        // given
        ProductsEntity entity = new ProductsEntity();

        // when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);

        // then
        Assertions.assertThrows(PersistenceException.class, () -> session.getTransaction().commit());
    }
}