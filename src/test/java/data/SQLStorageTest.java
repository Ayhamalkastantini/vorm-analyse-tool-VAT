package data;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SQLStorageTest {

    @Test
    public void checkConnection() {
        assertTrue(SQLStorage.checkConnection());
    }
}
