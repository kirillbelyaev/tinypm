/*
 * Linux Policy Machine (LPM) Prototype
 *   
 * Copyright (C) 2015-2016  Kirill A Belyaev
 * Colorado State University
 * Department of Computer Science,
 * Fort Collins, CO  80523-1873, USA
 *
 * E-mail contact:
 * kirillbelyaev@yahoo.com
 * kirill@cs.colostate.edu
 *   
 * This work is licensed under the Creative Commons Attribution-NonCommercial 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/ or send 
 * a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
 */
package edu.csu.lpm.DB.implementation;

import static edu.csu.lpm.DB.DAO.RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
import static edu.csu.lpm.DB.DAO.RecordDAO.INDICATE_EXECUTION_SUCCESS;
import edu.csu.lpm.DB.DAO.UserAuthDAO;
import edu.csu.lpm.DB.DTO.ComponentsTableRecord;
import edu.csu.lpm.DB.exceptions.RecordDAO_Exception;
import edu.csu.lpm.DB.interfaces.ComponentsTable;
import edu.csu.lpm.DB.interfaces.DB_Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maalv
 */
public final class UserAuthDAO_impl implements UserAuthDAO {

    private Connection conn = null;
    private UserAuthDAO_impl userImpl = null;
    private final String username = "lpm-admin";
    private final String password = "lpm-admin";

    public UserAuthDAO_impl(Connection c) throws SQLException, RecordDAO_Exception {

        initConnection(c);

        createTable_Authentication_DB();
        if (checkTableExist() == false) {
            insert_default_Authentication_DB();
        }
    }

    @Override
    public int createTable_Authentication_DB() throws RecordDAO_Exception {

        Statement state = null;

        if (this.conn == null) {
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        try {
            state = this.conn.createStatement();
            state.executeUpdate(DB_Constants.create_USER_AUTH_DB_SQL);

        } catch (SQLException e) {
            throw new RecordDAO_Exception("Exception: " + e.getMessage(), e);
        }

        return INDICATE_EXECUTION_SUCCESS;
    }

    private void initConnection(Connection c) throws SQLException {

        conn = c;
    }

    @Override
    public int insert_default_Authentication_DB() throws RecordDAO_Exception {

        if (this.conn == null) {
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        PreparedStatement ps = null;

        try {
            ps = this.conn.prepareStatement(DB_Constants.INSERT_INTO_USER_AUTH_DB_SQL);

            int index = 1;

            ps.setString(index++, username);
            ps.setString(index++, password);

            ps.addBatch();
            this.conn.setAutoCommit(false);
            ps.executeBatch();
            this.conn.setAutoCommit(true);

        } catch (SQLException e) {
            throw new RecordDAO_Exception("Exception: " + e.getMessage(), e);
        }

        return INDICATE_EXECUTION_SUCCESS;
    }

    public String getUsernameFromDB() throws SQLException {

        return getFromDB(true);
    }

    public String getPasswordFromDB() throws SQLException {

        return getFromDB(false);

    }

    private String getFromDB(boolean isUserName) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = null;

        ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_USER_AUTH);

        this.conn.setAutoCommit(false);
        rs = ps.executeQuery();
        this.conn.setAutoCommit(true);

        while (rs.next()) {
            if (isUserName == false) {
                result = rs.getString(UserAuthDAO.PASSWORD);
            } else {
                result = rs.getString(UserAuthDAO.USERNAME);
            }
        }
        rs.close();
        rs = null;

        if (result != null) {
            return result;
        }
        return "ERROR";

    }

    @Override
    public int updateNewPasswordInDB(String newPassword) throws SQLException {
        if (this.conn == null) {
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        PreparedStatement ps = null;

        try {
            ps = this.conn.prepareStatement(DB_Constants.UPDATE_PASSWORD_IN_USER_AUTH);

            int index = 1;

            ps.setString(index++, newPassword);
            ps.setString(index++, username);

            ps.addBatch();
            this.conn.setAutoCommit(false);
            ps.executeBatch();
            this.conn.setAutoCommit(true);

        } catch (SQLException e) {
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        return INDICATE_EXECUTION_SUCCESS;
    }

    private boolean checkTableExist() {
        try {
            String result = getFromDB(true);
            if (!result.equalsIgnoreCase("error")) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserAuthDAO_impl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
