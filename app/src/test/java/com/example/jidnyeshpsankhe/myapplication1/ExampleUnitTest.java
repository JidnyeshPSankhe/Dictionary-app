package com.example.jidnyeshpsankhe.myapplication1;

import org.junit.Test;

import java.util.*;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void list_Sort() {
        Testcases testcases = new Testcases();
        MainActivity mn = new MainActivity();

        assertThat(mn.sort_upvotes(testcases.actual()).get(0).getUpVotes(), is(testcases.expected().get(0).getUpVotes()));
    }
}

class Testcases {
    public ArrayList<SearchObject> actual() {
        ArrayList<SearchObject> ar = new ArrayList<>();
        ar.add(new SearchObject("Abc",6,3));
        ar.add(new SearchObject("ert",8,4));

        return ar;
    }

    public ArrayList<SearchObject> expected() {
        ArrayList<SearchObject> ar = new ArrayList<>();

        ar.add(new SearchObject("ert",8,4));
        ar.add(new SearchObject("Abc",6,3));

        return ar;
    }}
