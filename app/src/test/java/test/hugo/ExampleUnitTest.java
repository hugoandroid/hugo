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
        main1();
        System.out.println( Calendar.getInstance().get(Calendar.YEAR));
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

    public void main1() {
        int a[] = {1,2,3,4,4,4,3,1,2,3 };
        int c[] = new int[5];
        for(int i=0;i<c.length;i++){
            for(int j=0;j<a.length;j++){
                if(a[j] == i){
                    c[i]++;
                }
            }
        }
        int index = 0;
        for(int j=0;j<c.length;j++){
            for(int i=index;i<index + c[j];i++){
                a[i] = j;
            }
            index +=c[j];
        }
        System.out.println(Arrays.toString(a));
    }
}