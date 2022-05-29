package by.lev.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestInputFunction {

    @Test
    @DisplayName("Testing checkTheCorrectnessOfTheLoginInput with empty string")
    public void Test1_checkTheCorrectnessOfTheLoginInput(){
        String input = "";
        boolean result = InputFunction.checkTheCorrectnessOfTheLoginInput(input);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Testing checkTheCorrectnessOfTheLoginInput with two simbols")
    public void Test2_checkTheCorrectnessOfTheLoginInput(){
        String input = "ab";
        boolean result = InputFunction.checkTheCorrectnessOfTheLoginInput(input);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Testing checkTheCorrectnessOfTheLoginInput with 29 simbols")
    public void Test3_checkTheCorrectnessOfTheLoginInput(){
        String input = "ciklopentanpergidrofenontren1";
        boolean result = InputFunction.checkTheCorrectnessOfTheLoginInput(input);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Testing checkTheCorrectnessOfTheLoginInput with escape")
    public void Test4_checkTheCorrectnessOfTheLoginInput(){
        String input = "klick klick";
        boolean result = InputFunction.checkTheCorrectnessOfTheLoginInput(input);
        Assertions.assertFalse(result);
    }

}
