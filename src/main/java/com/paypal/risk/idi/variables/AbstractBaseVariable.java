/**
 * 
 */
package com.paypal.risk.idi.variables;

import java.util.HashMap;
import java.util.Map;

/**
 * This is an example copied from variable repository.
 * 
 * @author xchen7
 *
 */
public class AbstractBaseVariable {

    protected String name = null;
    protected Map<String, Object> variableContextMap;

    public AbstractBaseVariable() {
        this(null);
    }

    public AbstractBaseVariable(String name) {
        if (name == null)
            this.name = getClass().getSimpleName();
        else
            this.name = name;

        // TODO: migrate to AbstractSubcontextVariable
        variableContextMap = new HashMap<String, Object>(10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void putReturnValueInContext(Object value,
            Map<String, Object> context) {

        /*
         * Should never see below print as it is deleted.
         */
        System.out.println(
                "Running AbstractBaseVariable::putReturnValueInContext()");

        context.put(getName(), value);
    }

}
