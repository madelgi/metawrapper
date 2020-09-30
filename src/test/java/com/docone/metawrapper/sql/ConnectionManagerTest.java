package com.docone.metawrapper.sql;

import org.junit.Test;

import java.sql.Connection;


public class ConnectionManagerTest {

    @Test
    public void testGetConnection() throws Exception {
         Connection con = ConnectionManager.getConnection();
         System.out.println(con.getMetaData().getURL());
         System.out.println(con.getMetaData().getUserName());
    }
}
