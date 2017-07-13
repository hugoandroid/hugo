package test.hugo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        System.out.println( Calendar.getInstance().get(Calendar.YEAR));
        child01 child01 = new child01();
        child02 child02 = new child02();
        child01.a = 14;
        child02.a = 27;
        System.out.print(child01.a+"  "+child02.a);

    }
    static class Parent{
        static int a;
    }
    static class child01 extends Parent{
    }
    static class child02 extends Parent{
    }
}