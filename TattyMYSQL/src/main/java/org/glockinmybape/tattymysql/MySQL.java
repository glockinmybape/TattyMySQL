package org.glockinmybape.tattymysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MySQL {
    protected static HashMap<String, Connection> connections = new HashMap();
    protected static ArrayList<MySQL> users = new ArrayList();
    private Connection connection;
    private String host;
    private String user;
    private String password;
    private JavaPlugin plugin;
    private int sended;
    private long online;

    private MySQL(JavaPlugin plugin, String host, String user, String password) {
        this.plugin = plugin;
        this.host = host;
        this.user = user;
        this.password = password;
        if (!connections.containsKey(this.host)) {
            this.connection = this.connect();
            connections.put(this.host, this.connection);
        } else {
            this.connection = (Connection)connections.get(this.host);
        }

        if (this.connection == null) {
            Logger.info(this.plugin,  ChatColor.RED+ "Ошибка при соединении с базой данных.");
            this.online = 0L;
        } else {
            Logger.info(this.plugin, ChatColor.RED + "Соединение с базой данных успешно установлено.");
            this.online = System.currentTimeMillis();
        }

    }

    public static MySQL get(JavaPlugin plugin, String host, int port, String database, String user, String password) {
        return get(plugin, buildHostString(host, port, database), user, password);
    }

    public static MySQL get(JavaPlugin plugin, String hoststring, String user, String password) {
        MySQL mysql = new MySQL(plugin, hoststring, user, password);
        users.add(mysql);
        return mysql;
    }

    private static String buildHostString(String host, int port, String database) {
        return "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=UTF-8";
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    private Connection connect() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                return this.connection;
            } else {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                return this.connection = DriverManager.getConnection(this.host, this.user, this.password);
            }
        } catch (InstantiationException | ClassNotFoundException | SQLException | IllegalAccessException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public boolean disconnect() {
        try {
            this.connection.close();
            connections.remove(this.host);
            users.remove(this);
            return true;
        } catch (SQLException var2) {
            return false;
        }
    }

    public void execute(String query, MySQL.ExecuteInvoker run, Object... args) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                this.executeSync(query, run, args);
            });
        });
    }

    public void executeSync(String query, MySQL.ExecuteInvoker run, Object... args) {
        this.connect();
        Logger.debug(this.plugin, query + " " + Arrays.toString(args));

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, 1);
            Throwable var5 = null;

            try {
                for(int i = 0; i < args.length; ++i) {
                    preparedStatement.setObject(i + 1, args[i]);
                }

                preparedStatement.execute();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                Throwable var7 = null;

                try {
                    if (run != null) {
                        if (rs.next()) {
                            run.run(rs.getInt(1));
                        } else {
                            run.run(0);
                        }
                    }
                } catch (Throwable var32) {
                    var7 = var32;
                    throw var32;
                } finally {
                    if (rs != null) {
                        if (var7 != null) {
                            try {
                                rs.close();
                            } catch (Throwable var31) {
                                var7.addSuppressed(var31);
                            }
                        } else {
                            rs.close();
                        }
                    }

                }

                ++this.sended;
            } catch (Throwable var34) {
                var5 = var34;
                throw var34;
            } finally {
                if (preparedStatement != null) {
                    if (var5 != null) {
                        try {
                            preparedStatement.close();
                        } catch (Throwable var30) {
                            var5.addSuppressed(var30);
                        }
                    } else {
                        preparedStatement.close();
                    }
                }

            }
        } catch (SQLException var36) {
            Logger.error(this.plugin, var36.getMessage());
        }

    }

    public void execute(String query, Object... args) {
        this.execute(query, (MySQL.ExecuteInvoker)null, args);
    }

    public void executeSync(String query, Object... args) {
        this.executeSync(query, (MySQL.ExecuteInvoker)null, args);
    }

    public void executeQuery(String query, MySQL.QueryInvoker run, Object... args) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            this.executeSyncQuery(query, run, args);
        });
    }

    public void executeSyncQuery(String query, MySQL.QueryInvoker run, Object... args) {
        this.connect();
        Logger.debug(this.plugin, query + " " + Arrays.toString(args));

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            Throwable var5 = null;

            try {
                for(int i = 0; i < args.length; ++i) {
                    preparedStatement.setObject(i + 1, args[i]);
                }

                ResultSet rs = preparedStatement.executeQuery();
                Throwable var7 = null;

                try {
                    if (run != null) {
                        run.run(rs);
                    }
                } catch (Throwable var32) {
                    var7 = var32;
                    throw var32;
                } finally {
                    if (rs != null) {
                        if (var7 != null) {
                            try {
                                rs.close();
                            } catch (Throwable var31) {
                                var7.addSuppressed(var31);
                            }
                        } else {
                            rs.close();
                        }
                    }

                }

                ++this.sended;
            } catch (Throwable var34) {
                var5 = var34;
                throw var34;
            } finally {
                if (preparedStatement != null) {
                    if (var5 != null) {
                        try {
                            preparedStatement.close();
                        } catch (Throwable var30) {
                            var5.addSuppressed(var30);
                        }
                    } else {
                        preparedStatement.close();
                    }
                }

            }
        } catch (SQLException var36) {
            Logger.error(this.plugin, var36.getMessage());
        }

    }

    public void executeQuery(String query, Object... args) {
        this.executeQuery(query, (MySQL.QueryInvoker)null, args);
    }

    public void executeSyncQuery(String query, Object... args) {
        this.executeSyncQuery(query, (MySQL.QueryInvoker)null, args);
    }

    public int getSended() {
        return this.sended;
    }

    public long getOnline() {
        return this.online;
    }

    public interface ExecuteInvoker {
        void run(int var1);
    }

    public interface QueryInvoker {
        void run(ResultSet var1) throws SQLException;
    }
}
