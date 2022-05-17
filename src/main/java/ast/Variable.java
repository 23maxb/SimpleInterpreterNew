package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a variable in pascal
 *
 * @author Max Blennemann
 * @version 3/23/22
 */
public class Variable implements Expression
{
    public String name;

    /**
     * Creates a variable object with the name given.
     *
     * @param name the name of the variable
     */
    public Variable(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Evaluates the variable with the value and environment given
     *
     * @param e the environment to pull variable values from
     * @return the evaluation
     */
    @Override
    public Object evaluate(Environment e)
    {
        if (e.hasVariable(name))
            return e.getVariable(name);
        throw new IllegalArgumentException("The variable '" + name + "' has not been declared.");
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString()
    {
        return "Variable(" + name + ")";
    }

    /**
     * Returns the required assembly code to evaluate the expression.
     * Calling this will set $t0 to the variable value in the code.
     *
     * @param e the emitter to use
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("lw $t0, var" + name + "#push on stack");
    }
}
