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

import org.unibl.etf.Calculator.Calculator;
import org.unibl.etf.Calculator.DivideByZeroException;
import org.unibl.etf.Calculator.NotSupportedOperationException;
import org.unibl.etf.Calculator.NumberNotInAreaException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class CalculatorTest {
    Calculator calculator = new Calculator();
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        calculator.setCurrentValue(0);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testGetCurrentValue() {
        assertThat(calculator.getCurrentValue(), is(equalTo(0.0)));
    }


    @Test
    void testSetCurrentValue() {
        calculator.setCurrentValue(10);
        assertThat(calculator.getCurrentValue(), is(equalTo(10.0)));
    }

    @Test
    void testCalculator() {
        Calculator calculatorNew = new Calculator();
        assertThat(calculatorNew.getCurrentValue(), is(0.0));
    }

    @ParameterizedTest
    @MethodSource("testCalculate")
    public void testCalculateParameterized(Double result,double value,char operator) throws DivideByZeroException, NotSupportedOperationException  {
        calculator.setCurrentValue(10);
        calculator.calculate(value, operator);
        assertThat(calculator.getCurrentValue(), is(result));
    }
    private static Stream<Arguments> testCalculate(){
        return Stream.of(
                Arguments.of(20.0,10,'+'),
                Arguments.of(-10.0,20,'-'),
                Arguments.of(50.0,5,'*'),
                Arguments.of(5.0,2,'/')

        );

    }

    @ParameterizedTest
    @MethodSource("testCalculateException")
    public void testCalculateExceptionParametrized(Class<Exception> pom,double value,char operator) throws DivideByZeroException, NotSupportedOperationException {
        calculator.setCurrentValue(10);
        Exception exception = assertThrows(pom,()->calculator.calculate(value, operator));
        assertThat(exception,is(instanceOf(pom)));
    }
    private static Stream<Arguments> testCalculateException(){
        return Stream.of(
                Arguments.of(DivideByZeroException.class,0.0,'/'),
                Arguments.of(NotSupportedOperationException.class,5,'2')
        );
    }


}

}
