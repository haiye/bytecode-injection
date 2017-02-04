/**
 * 
 */
package com.paypal.risk.simulation.app;

import java.util.HashMap;

import com.paypal.risk.idi.variables.AbstractBaseVariable;

/**
 * @author xchen7
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Running App::main() - BEGIN");

        AbstractBaseVariable variable = new AbstractBaseVariable();
        variable.putReturnValueInContext("Hello world!",
                new HashMap<String, Object>());

        System.out.println("Running App::main() - END");
    }

}
