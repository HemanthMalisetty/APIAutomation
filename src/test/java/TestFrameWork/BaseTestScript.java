package TestFrameWork;


import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

public class BaseTestScript {

    PrintStream stdout;

    @BeforeGroups(groups = {"local","test"})
    //@BeforeClass(alwaysRun = true)
    public void setup() throws FileNotFoundException {
        stdout = System.out;
        PrintStream out = new PrintStream(new FileOutputStream("console-output.txt"));
        System.setOut(out);
    }

    @BeforeMethod(groups = {"local","test"})
    public void handleTestMethodName(Method method)
    {
        System.out.println();
        System.out.println("TESTCASE NAME : " + method.getName());
        System.out.println();
    }


    @AfterGroups(groups = {"local","test"})
    public void teardown(){
        System.setOut(stdout);
    }


}
