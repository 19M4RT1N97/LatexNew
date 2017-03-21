/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import latex.testgenerierung.MainFrame;
import latex.testgenerierung.Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Martin
 */
public class UnitTests {
    
    
    public UnitTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Util.readFile("C:\\Users\\Martin\\Desktop\\Testsammlung.tex");
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void themenreadtest(){
        
        LinkedList<String> l = new LinkedList<>();
        l.add("Rekursion ");
        l.add("Map ");
        assertEquals("",l, Util.themenRead());
    }
    @Test
    public void themencounttest(){
        DefaultTableModel dm = new DefaultTableModel(new String[]{"Thema"}, 0);
        dm.addRow(new String[]{"Rekursion"});
        dm.addRow(new String[]{"Map"});
       assertEquals("",2, Util.getThemencount(dm));
    }
}
