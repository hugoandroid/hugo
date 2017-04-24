package com.hwl.universitypie;

import org.junit.Test;

import java.util.ArrayList;
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
        System.out.println(getCount());
    }

    public int getCount() {
        ArrayList<Boolean> list = new ArrayList<>();
        for (int i = 1;i<=100;i++){
            list.add(false);
        }
        for (int i = 1;i<100;i++){ //100个人
            for (int j = 0;j<=100;j+=i) {
                if(j>=list.size()) break;
                list.set(j,!list.get(j));
            }
        }
        int count = 0;
        for (boolean b:list){
            System.out.print(b+"  ");
            if(b) count ++;
        }
        System.out.println();
        System.out.println(count);
        return count;
    }
}