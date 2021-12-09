package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;



import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.unibl.etf.Calculator.CalculatorAdvanced;
import org.unibl.etf.Calculator.DivideByZeroException;
import org.unibl.etf.Calculator.NotSupportedOperationException;
import org.unibl.etf.Calculator.NumberNotInAreaException;

public class CalculatorAdvancedTest {
    CalculatorAdvanced calculatorAdvanced = new CalculatorAdvanced();
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @ParameterizedTest
    @MethodSource("testCalculateAdvanced")
    public void testCalculateAdvancedParametrized(double result,double value,char action) throws NumberNotInAreaException, NotSupportedOperationException {
        calculatorAdvanced.setCurrentValue(value);
        calculatorAdvanced.calculateAdvanced(action);
        assertThat(calculatorAdvanced.getCurrentValue(), is(result));
    }
    private static Stream<Arguments> testCalculateAdvanced(){
        return Stream.of(
                Arguments.of(6.0,3,'!'),
                Arguments.of(3628800.0,10,'!'),
                Arguments.of(1.0,1,'!'),
                Arguments.of(3.0,3,'1'),
                Arguments.of(9.0,3,'2'),
                Arguments.of(1.0,0,'!'),
                Arguments.of(1.0,1,'9'),
                Arguments.of(0.0,0,'9'),
                Arguments.of(1.0,5,'0'),
                Arguments.of(1.0,0,'0'));
    }

    @ParameterizedTest
    @MethodSource("testCalculateAdvancedExceptions")
    public void testCalculateAdvancedExceptionParametrized(Class<Exception> ex,double value,char action) throws NumberNotInAreaException, NotSupportedOperationException {
        calculatorAdvanced.setCurrentValue(value);
        Exception exception = assertThrows(ex,()->calculatorAdvanced.calculateAdvanced(action));
        assertThat(exception,is(instanceOf(ex)));
    }
    private static Stream<Arguments> testCalculateAdvancedExceptions(){
        return Stream.of(
                Arguments.of(NumberNotInAreaException.class,11,'!'),
                Arguments.of(NumberNotInAreaException.class,-1,'!'),
                Arguments.of(NotSupportedOperationException.class,11,'A'),
                Arguments.of(NotSupportedOperationException.class,11,'+'));
    }

    @ParameterizedTest
    @MethodSource("testHasCharacteristic")
    public void testHasCharacteristicParametrized(boolean result,double value,char CharValue) throws NumberNotInAreaException, NotSupportedOperationException {
        calculatorAdvanced.setCurrentValue(value);
        assertThat(calculatorAdvanced.hasCharacteristic(CharValue), is(result));
    }
    private static Stream<Arguments> testHasCharacteristic(){
        return Stream.of(
                Arguments.of(true,153,'A'),
                Arguments.of(true,1,'A'),
                Arguments.of(false,20,'A'),
                Arguments.of(false,1,'P'),
                Arguments.of(true,6,'P'),
                Arguments.of(false,10,'P'));
    }

    @ParameterizedTest
    @MethodSource("testHasCharacteristicException")
    public void testHasCharacteristicExceptionParametrized(Class<Exception> ex,double value,char CharValue) throws NumberNotInAreaException, NotSupportedOperationException {
        calculatorAdvanced.setCurrentValue(value);
        Exception exception = assertThrows(ex,()->calculatorAdvanced.hasCharacteristic(CharValue));
        assertThat(exception,is(instanceOf(ex)));
    }
    private static Stream<Arguments> testHasCharacteristicException(){
        return Stream.of(
                Arguments.of(NumberNotInAreaException.class,0,'A'),
                Arguments.of(NumberNotInAreaException.class,0,'P'),
                Arguments.of(NotSupportedOperationException.class,23,'a'),
                Arguments.of(NotSupportedOperationException.class,23,'B')
        );
    }

    @ParameterizedTest
    @MethodSource("testCalculate1")
    public void testCalculateParameterized(Double result,double value,char operator) throws DivideByZeroException, NotSupportedOperationException  {
        calculatorAdvanced.setCurrentValue(10);
        calculatorAdvanced.calculate(value, operator);
        assertThat(calculatorAdvanced.getCurrentValue(), is(result));
    }
    private static Stream<Arguments> testCalculate1(){
        return Stream.of(
                Arguments.of(20.0,10,'+'),
                Arguments.of(-10.0,20,'-'),
                Arguments.of(50.0,5,'*'),
                Arguments.of(5.0,2,'/')

        );

    }

    @ParameterizedTest
    @MethodSource("testCalculateException")
    public void testCalculateExceptionParametrized(Class<Exception> ex,double value,char operator) throws DivideByZeroException, NotSupportedOperationException {
        calculatorAdvanced.setCurrentValue(10);
        Exception exception = assertThrows(ex,()->calculatorAdvanced.calculate(value, operator));
        assertThat(exception,is(instanceOf(ex)));
    }
    private static Stream<Arguments> testCalculateException(){
        return Stream.of(
                Arguments.of(DivideByZeroException.class,0.0,'/'),
                Arguments.of(NotSupportedOperationException.class,5,'2')
        );
    }
}
