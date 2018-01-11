package Shared.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorTest {


    @Test
    public void testDialog(){
        Error e = new Error(new Exception());
    }
}