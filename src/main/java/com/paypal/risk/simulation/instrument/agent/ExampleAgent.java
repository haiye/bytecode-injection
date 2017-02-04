/**
 * 
 */
package com.paypal.risk.simulation.instrument.agent;

import java.lang.instrument.Instrumentation;

import com.paypal.risk.simulation.instrument.transformer.ExampleTransformer;

/**
 * This class is the entrance of java agent, make sure it has premain() method
 * signature and it is configured in manifest (see pom.xml).
 * 
 * @author xchen7
 *
 */
public class ExampleAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Running ExampleAgent::premain() - BEGIN");
        inst.addTransformer(new ExampleTransformer());
        System.out.println("Running ExampleAgent::premain() - END");
    }

}
