package org.mushroom.currencyexchanger.utils;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionManager {
    //    private static final String URL_KEY = "db.url";
//    private static final String USER_KEY = "db.user";
//    private static final String PASSWORD_KEY = "db.password";
//    private static final String DRIVER_KEY = "db.driver";
    private static final HikariDataSource HIKARI_DATA_SOURCE;

    static {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/currency_database");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setPassword("admin");
        hikariConfig.setUsername("postgres");

        HIKARI_DATA_SOURCE = new HikariDataSource(hikariConfig);
    }

    public static Connection getConnection() throws SQLException {
        return HIKARI_DATA_SOURCE.getConnection();
    }
}

//    static {
//        try {
//            Class.forName(PropertiesUtil.get(DRIVER_KEY));
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(
//                PropertiesUtil.get(URL_KEY),
//                PropertiesUtil.get(USER_KEY),
//                PropertiesUtil.get(PASSWORD_KEY)
/// /                PropertiesUtil.get(DRIVER_KEY)
/// /
//        );
//    }
//}

//    private static final int DEFAULT_POOL_SIZE = 10;
//    private static final String POOL_SIZE_KEY = "db.pool.size";
//    private static BlockingQueue<Connection> pool;

//    static {
//        initConnectionPool();
//    }
//
//    private static void initConnectionPool() {
//        String poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
//        int size;
//        try {
//            size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
//        } catch (NumberFormatException e) {
//            throw new DataBaseConnectException("Некорректный формат", e);
//        }
//
//        pool = new ArrayBlockingQueue<>(size);
//        for (int i = 0; i < size; i++) {
//            Connection connection = open();
//            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
//                    new Class[]{Connection.class},
//                    (proxy, method, args) -> method.getName().equals("close") ?
//                            pool.add((Connection) proxy) : method.invoke(connection, args));
//            pool.add(proxyConnection);
//
//        }
//    }
//
//    public static Connection get() throws GetConnactionException {
//        try {
//            return pool.take();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new GetConnactionException("Отсутствует подключение");
//        }
//    }
//
//    private static Connection open() throws RuntimeException {
//        try {
//            return DriverManager.getConnection(
//                    PropertiesUtil.get(URL_KEY),
//                    PropertiesUtil.get(USER_KEY),
//                    PropertiesUtil.get(PASSWORD_KEY));
//        } catch (SQLException e) {
//            throw new RuntimeException();
//        }
//    }
//}
