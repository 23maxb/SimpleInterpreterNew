package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a statement in pascal.
 *
 * @author Max Blennemann
 * @version 3/23/22
 */
public interface Statement
{
    /**
     * Executes the statement and does whatever operation is needed.
     *
     * @param env the environment in which the statement is executed
     */
    void exec(Environment env);

    /**
     * Returns a string representation of the object
     *
     * @return a string representation of the object
     */
    @Override
    String toString();

    /**
     * Returns the required assembly code to run the Statement.
     *
     * @param e the emitter to use
     */
    void compile(Emitter e);
}
