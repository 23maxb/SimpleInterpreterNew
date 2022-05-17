package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents an integer as a tree node
 *
 * @author Max Blennemann
 * @version 3/23/22
 */
public class Number implements Expression
{
    private final int number;

    /**
     * Creates an integer as a number object.
     *
     * @param number the integer to use to create an integer object
     */
    public Number(int number)
    {
        this.number = number;
    }

    /**
     * Evaluates the number.
     * (Just returns the integer.)
     *
     * @precondition none
     * @postcondition none
     * @param e the enviornment to pull variable values from
     * @return the number object
     */
    @Override
    public Object evaluate(Environment e)
    {
        return number;
    }

    /**
     * Returns a String representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString()
    {
        return "Number(" + number + ")";
    }

    /**
     * Returns the required assembly code to allocate the number to $t0.
     *
     * @param e the emitter to use
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("li $t0, " + number);
    }
}
