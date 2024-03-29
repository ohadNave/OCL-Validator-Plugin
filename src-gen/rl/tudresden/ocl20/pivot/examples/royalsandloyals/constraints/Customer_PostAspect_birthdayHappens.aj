package tudresden.ocl20.pivot.examples.royalsandloyals.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect Customer_PostAspect_birthdayHappens {

    /**
     * <p>Pointcut for all calls on {@link tudresden.ocl20.pivot.examples.royalsandloyals.Customer#birthdayHappens()}.</p>
     */
    protected pointcut birthdayHappensCaller(tudresden.ocl20.pivot.examples.royalsandloyals.Customer aClass):
    	call(* tudresden.ocl20.pivot.examples.royalsandloyals.Customer.birthdayHappens())
    	&& target(aClass);
    
    /**
     * <p>Checks a postcondition for the operation {@link Customer#birthdayHappens()} defined by the constraint
     * <code>context Customer::birthdayHappens() : 
     *       post: age = age@pre + 1</code></p>
     */
    void around(tudresden.ocl20.pivot.examples.royalsandloyals.Customer aClass): birthdayHappensCaller(aClass) {
        /* Disable this constraint for subclasses of Customer. */
        if (aClass.getClass().getCanonicalName().equals("tudresden.ocl20.pivot.examples.royalsandloyals.Customer")) {
    
        Integer atPreValue1;
        
        if ((Object) aClass.age == null) {
            atPreValue1 = null;
        } else {
            atPreValue1 = new Integer(aClass.age);
        }
    
        proceed(aClass);
    
        if (!((Object) aClass.age).equals((atPreValue1 + new Integer(1)))) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'undefined' (post: age = age@pre + 1) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}