package ast;

import emitter.Emitter;
import environment.Environment;

import java.util.Scanner;

/**
 * The Program class is the root of the AST. It contains a list of
 * declarations and a list of statements.
 *
 * @author Max Blennemann
 * @version 5/17/22
 */
public class Read implements Statement
{
    public Read(String variableName)
    {
        this.variableName = variableName;
    }

    private final String variableName;

    /**
     * Executes the statement and does whatever operation is needed.
     *
     * @param env the environment in which the statement is executed
     */
    @Override
    public void exec(Environment env)
    {
        new Assignment(variableName,
                new Number(Integer.parseInt(new Scanner(System.in).nextLine()))).exec(env);
    }

    /**
     * Returns a string representation of the object
     *
     * @return a string representation of the object
     */
    @Override
    public String toString()
    {
        return "Assignment(" + variableName + " to " + "value given by user" + ")";
    }

    /**
     * Returns the required assembly code to run the Statement.
     *
     * @param e the emitter to use
     */
    @Override
    public void compile(Emitter e)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
