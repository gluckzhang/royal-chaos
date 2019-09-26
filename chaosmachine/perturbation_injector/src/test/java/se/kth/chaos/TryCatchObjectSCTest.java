package se.kth.chaos;

import com.ea.agentloader.AgentLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kth.chaos.testfiles.TryCatchTestObject;

public class TryCatchObjectSCTest {
	
    @Test
    public void scMultipleTryCatchTest() {
        AgentLoader.loadAgentClass(ChaosMachineAgent.class.getName(), "mode:scircuit");
        // this time, we do short-circuit testing, exceptions will be injected into the beginning of every try block
        // hence "_1st line in xxx tc" should not appear in the return value
        TryCatchTestObject tcTest = new TryCatchTestObject();
        Assert.assertEquals(tcTest.multipleTryCatch(), "_mpe in 1st tc_mpe in 2nd tc");
    }

    public static void main(String[] args) {
    	AgentLoader.loadAgentClass(ChaosMachineAgent.class.getName(), "mode:scircuit");

        TryCatchTestObject tcTest = new TryCatchTestObject();
        System.out.println(tcTest.multipleTryCatch());
    }
}
