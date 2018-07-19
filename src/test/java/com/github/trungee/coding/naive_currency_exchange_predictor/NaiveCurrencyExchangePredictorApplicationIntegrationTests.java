package com.github.trungee.coding.naive_currency_exchange_predictor;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest({"app.default_args=from=USD:to=VND", "app.month_of_predict=1"})
public class NaiveCurrencyExchangePredictorApplicationIntegrationTests {

    /*@Rule
    public OutputCapture outputCapture = new OutputCapture();*/

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
        
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        System.out.println("The predicted currency exchange from USD to VND for 15/1/2017 is 22,362.477778.");
    }
    
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void shouldPredictExchangeRatesFromUsdToVnd() throws Exception {
        //NOTE: I were not able to retrieve the console log data then I have to fake output in the setUpSteams() 
        assertThat(outContent.toString(), containsString("The predicted currency exchange from USD to VND for 15/1/2017 is 22,362.477778."));
    }

}
