package org.mushroom.currencyexchanger.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mushroom.currencyexchanger.exception.DataBaseConnectException;
import org.mushroom.currencyexchanger.exception.GetConnactionException;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final int DEFAULT_POOL_SIZE = 10;
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static BlockingQueue<Connection> pool;

    static {
        initConnectionPool();
    }

    private static void initConnectionPool() {
        String poolSize = PropertiesUtil.get("db.pool.size");
        int size;
        try {
            size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        } catch (NumberFormatException e) {
            throw new DataBaseConnectException("Некорректный формат", e);
        }

        pool = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            Connection connection = open();
            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close") ?
                            pool.add((Connection) proxy) : method.invoke(connection, args));
            pool.add(proxyConnection);

        }
    }

    public static Connection get() throws GetConnactionException {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new GetConnactionException("Отсутствует подключение");
        }
    }

    private static Connection open() throws RuntimeException {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
