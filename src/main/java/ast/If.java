package ast;

import emitter.Emitter;
import environment.Environment;

import java.util.ArrayList;

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
    private final Statement e;

    /**
     * Creates a statement that can be executed if a condition is true.
     *
     * @param condition the condition to check
     * @param block     the block to run if the condition is true
     * @param e
     */
    public If(Expression condition, Statement block, Statement e)
    {
        this.condition = condition;
        b = block;
        this.e = e;
    }

    /**
     * Creates a statement that can be executed if a condition is true.
     *
     * @param condition the condition to check
     * @param block     the block to run if the condition is true
     * @param e
     */
    public If(Expression condition, Statement block)
    {
        this(condition, block, new Block(new ArrayList<>()));
    }

    /**
     * Executes the statement
     *
     * @param env the environment in which the statement is executed
     */
    @Override
    public void exec(Environment env)
    {
        if (!((condition.evaluate(env) instanceof Boolean) && !((Boolean) condition.evaluate(env)))
                && (((condition.evaluate(env) instanceof Boolean)
                && ((Boolean) condition.evaluate(env)))
                || (((int) ((Number) condition.evaluate(env)).evaluate(env)) == 1)
                || (boolean) condition.evaluate(env)))
            b.exec(env);
        else
            e.exec(env);
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
