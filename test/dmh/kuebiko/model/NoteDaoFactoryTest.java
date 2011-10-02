/**
 * Kuebiko - NoteManagerFactory.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.model;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * Test class for NoteDaoFactory.
 * @see NoteDaoFactory
 * 
 * @author davehuffman
 */
public class NoteDaoFactoryTest {
    public static class InstantiationTest {
        private final String className;
        private final Map<String, String> params;
        private final boolean shouldSucceed;
        private final boolean ignoreParams; 
        
        private InstantiationTest(String className, boolean shouldSucceed) {
            this(className, null, shouldSucceed);
        }
        
        private InstantiationTest(String className, 
                Map<String, String> params, boolean shouldSucceed) {
            this.className = className; 
            this.params = params;
            this.shouldSucceed = shouldSucceed;
            this.ignoreParams = (params == null);
        }
        
        @Test
        public void getTest() {
            boolean succeed = true;
            Object dao = null;
            try {
                dao = ignoreParams? NoteDaoFactory.get(className) 
                        : NoteDaoFactory.get(className, params);
            } catch (Exception e) {
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
    
    
    
    @Factory
    public Object[] testFactory() {
        final Map<String, String> emptyParamMap = Maps.newHashMap();
        return new Object[] { 
            new InstantiationTest(InMemoryNoteDao.class.getName(), true),
            new InstantiationTest(InMemoryNoteDao.class.getName(), emptyParamMap, false),

            new InstantiationTest(FileSystemNoteDao.class.getName(), false),
            new InstantiationTest(FileSystemNoteDao.class.getName(), emptyParamMap, false),
//            new InstantiationTest(FileSystemNoteDao.class.getName(), 
//                    ImmutableMap.of(DaoParameter.DIRECTORY.toString(), "foobar"), false),
            
            new InstantiationTest("not a real dao", false)
        };
    }
    
}
