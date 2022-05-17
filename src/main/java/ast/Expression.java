package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The Expression abstract class
 *
 * @author Max Blennemann
 * @version 2/23/22
 */
public interface Expression
{
    /**
     * Evaluates the Expression
     *
     * @param e the environment to pull variable values from
     * @return the evaluated value
     */
    Object evaluate(Environment e);

    /**
     * Returns a string representation of the object
     *
     * @return a string representation of the object
     */
    @Override
    String toString();

    /**
     * Returns the required assembly code to evaluate the expression.
     *
     * @param e the emitter to use
     */
    void compile(Emitter e);

}
