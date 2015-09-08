package com.yoshigenius.lib.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yoshigenius.lib.storage.SimpleMap;

public final class SQLTable {

    private final SQLManager parent;
    private final String name;

    SQLTable(SQLManager parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void refreshConnection() {
        this.parent.refresh();
    }

    public ResultSet preparedQuery(String sql, Object... params) {
        this.refreshConnection();
        try {
            PreparedStatement statement = this.parent.getConnection().prepareStatement(sql.trim());
            if (params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            ResultSet set = statement.executeQuery();
            set.next();
            return set;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet query(String sql) {
        return this.preparedQuery(sql);
    }

    public ResultSet select(String... criteria) {
        String where = "";
        if (criteria != null && criteria.length > 0) {
            for (String criterion : criteria) {
                if (criterion == null || criterion.isEmpty()) {
                    continue;
                }
                if (where.isEmpty()) {
                    where += "WHERE " + criterion + " ";
                } else {
                    where += "AND " + criterion + " ";
                }
            }
        }
        return this.query(("SELECT * FROM " + this.name + " " + where.trim()).trim());
    }

    public int preparedUpdate(String sql, Object... params) {
        this.refreshConnection();
        try {
            PreparedStatement statement = this.parent.getConnection().prepareStatement(sql.trim());
            if (params.length > 0) {
                int counter = 0;
                statement.setObject(counter++, params[counter - 1]);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int update(String sql) {
        return this.preparedUpdate(sql);
    }

    public int insertInto(SimpleMap<String, Object> columns) {
        if (columns.size() > 0) {
            String cols = "";
            String vals = "";

            for (SimpleMap.Cell<String, Object> cell : columns.getCells()) {
                if (!(cell == null || cell.key().isEmpty() || cell.value() == null)) {
                    cols += (cols.equals("") ? "" : ", ") + "`" + cell.key() + "`";
                    String val = cell.value() instanceof String ? "'" + cell.value() + "'" : cell.value() + "";
                    vals += (vals.equals("") ? "" : ", ") + val;
                }
            }

            return this.update("INSERT INTO " + this.name + " (" + cols.trim() + ") VALUES (" + vals.trim() + ")");
        }
        return -1;
    }

    public int count(String... criteria) {
        String where = "";
        if (criteria != null && criteria.length > 0) {
            for (String criterion : criteria) {
                if (criterion == null || criterion.isEmpty()) {
                    continue;
                }
                if (where.isEmpty()) {
                    where += "WHERE " + criterion + " ";
                } else {
                    where += "AND " + criterion + " ";
                }
            }
        }
        ResultSet set = this.query("SELECT COUNT(*) FROM " + this.name + " " + where);
        try {
            set.next();
            int count = set.getInt(1);
            return count;
        } catch (SQLException ex) {
        }
        return -1;
    }

    public int set(String column, Object value, String... criteria) {
        String where = "";
        if (criteria != null && criteria.length > 0) {
            for (String criterion : criteria) {
                if (criterion == null || criterion.isEmpty()) {
                    continue;
                }
                if (where.isEmpty()) {
                    where += "WHERE " + criterion + " ";
                } else {
                    where += "AND " + criterion + " ";
                }
            }
        }
        return this.update(("UPDATE " + this.name + " SET " + column + " = " + value + " " + where.trim()).trim());
    }

    public ResultSet setMultiple(SimpleMap<String, String> sets, String... criteria) {
        String where = "";
        if (criteria != null && criteria.length > 0) {
            for (String criterion : criteria) {
                if (criterion == null || criterion.isEmpty()) {
                    continue;
                }
                if (where.isEmpty()) {
                    where += "WHERE " + criterion + " ";
                } else {
                    where += "AND " + criterion + " ";
                }
            }
        }
        if (sets != null && sets.size() > 0) {
            String prep = "";
            for (String key : sets.keySet()) {
                String value = sets.get(key);
                if (key != null && key.length() > 0 && value != null && value.length() > 0) {
                    prep += (prep.isEmpty() ? "" : ", ") + key + " = " + value;
                }
            }
            return this.query(("UPDATE " + this.name + " SET " + prep.trim() + " " + where.trim()).trim());
        }
        return null;
    }

    // COLUMNS

    public SQLColumn[] getColumns() {
        try {
            List<SQLColumn> list = new ArrayList<>();
            ResultSet res = this.preparedQuery("SHOW COLUMNS FROM " + this.name + " FROM " + this.parent.database);
            while (res.next()) {
                String name = res.getString("Field");
                String type = res.getString("Type");
                boolean nullable = res.getString("Null").equalsIgnoreCase("yes");
                String key = res.getString("Key");
                String def = res.getString("Default");
                String extra = res.getString("Extra");

                list.add(new SQLColumn(this, name, type, nullable, key, def, extra));
            }
            return list.toArray(new SQLColumn[list.size()]);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int addColumn(String column, String type) {
        return this.update("ALTER TABLE " + this.getName() + " ADD " + column + " " + type);
    }

}
