package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Creates a statement that can be executed if a condition is true
 *
 * @author Max Blennemann
 * @version 3/23/22
 */
public class If implements Statement
{
    private final Expression condition;
    private final Statement b;

    /**
     * Creates a statement that can be executed if a condition is true.
     *
     * @param condition the condition to check
     * @param block     the block to run if the condition is true
     */
    public If(Expression condition, Statement block)
    {
        this.condition = condition;
        b = block;
    }

    /**
     * Executes the statement
     *
     * @param env the environment in which the statement is executed
     */
    @Override
    public void exec(Environment env)
    {
        if ((((int) ((Number) condition.evaluate(env)).evaluate(env)) == 1)
                || (boolean) condition.evaluate(env))
        {
            b.exec(env);
        }
    }

    /**
     * Returns a String representation of the object
     *
     * @return a String representation of the object
     */
    @Override
    public String toString()
    {
        return "IF(" + condition + " THEN: " + b + ")";
    }

    /**
     * Returns the required assembly code to run the Statement.
     *
     * @param e the emitter to use
     */
    @Override
    public void compile(Emitter e)
    {
        condition.compile(e);
        String a = e.label();
        e.emit("beq $t0, $0, " + a);
        b.compile(e);
        e.emit(a + ":");
    }
}
