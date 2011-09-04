/**
 * Kuebiko - NoteManagerFactory.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.model;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;


import dmh.kuebiko.model.NoteDao;
import dmh.kuebiko.model.NoteDaoFactory;
import dmh.kuebiko.util.BadClassException;

/**
 * Test class for NoteDaoFactory.
 * @see NoteDaoFactory
 * 
 * @author davehuffman
 */
public class NoteDaoFactoryTest {
    @Factory
    public Object[] testFactory() {
        return new Object[] { 
            new InstantiationTest("InMemoryNoteDao", true),
            new InstantiationTest("not a real dao", false)
        };
    }
    
    public static class InstantiationTest {
        private final String className;
        private final boolean shouldSucceed;

        private InstantiationTest(String className, boolean shouldSucceed) {
            this.className = className; 
            this.shouldSucceed = shouldSucceed;
        }

        @Test
        public void getTest() {
            boolean succeed = true;
            Object dao = null;
            try {
                dao = NoteDaoFactory.get(className);
            } catch (BadClassException e) {
                succeed = false;
            }
            
            assertEquals(shouldSucceed, succeed, "Should match expectation of success.");
            if (shouldSucceed) {
                assertNotNull(dao, "Factory should return an object.");
                assertTrue(dao instanceof NoteDao, 
                        "Factory should return an instance of NoteDao.");
            }
        }
    }
}
