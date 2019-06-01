package lzy.test;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppTest
{
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    @Before
    public void set() {

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void mainTestWithHelp(){
        App.main(new String[]{"--help"});
        assertTrue(StringUtils.isNotEmpty(outContent.toString()));
    }

    @Test
    public void mainTestWithShortHelpCommand(){
        App.main(new String[]{"-h"});
        assertTrue(StringUtils.isNotEmpty(outContent.toString()));
    }


    @Test
    public void mainWithNullArgs(){
        App.main(null);
        assertTrue(StringUtils.isAllEmpty(outContent.toString() ));
    }

    @Test
    public void mainTestWithOutlierArg(){
        App.main(new String[]{"0,100"});
        assertTrue(StringUtils.isNotEmpty(errContent.toString()));
    }

    @Test
    public void mainTestThatHasOutput(){
        App.main(new String[]{"5,45"});
        assertTrue(StringUtils.split(outContent.toString()," " ).length>0);
    }

    @Test
    public void mainTestWithoutOutput(){
        App.main(new String[]{"1"});
        assertTrue(StringUtils.isAllEmpty(outContent.toString() ));
    }

    @Test
    public void letterPrinterTestWithEmptyLetter()
    {
        LetterPrinterTest lp = new LetterPrinterTest("0,1,2");
        String[] digitLetters=new String[]{"","ab","c"};
        lp.setDigits(digitLetters);

        lp.print();
        assertTrue(StringUtils.isAllEmpty(outContent.toString() ));
    }

    @Test
    public void letterPrinterTest()
    {
        LetterPrinterTest lp = new LetterPrinterTest("1,2,3");
        String[] digitLetters=new String[]{"","ab","c","de"};
        lp.setDigits(digitLetters);

        lp.print();
        assertTrue(StringUtils.split(outContent.toString()," " ).length==(2*1*2));
    }


    class LetterPrinterTest extends LetterPrinter{

        public LetterPrinterTest(String args) {
            super(args);
        }


        public void setDigits(String[] digitLetters) {
            super.digitLetters = digitLetters;
        }
    }



}
