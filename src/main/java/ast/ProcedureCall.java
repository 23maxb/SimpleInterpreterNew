package ast;

import emitter.Emitter;
import environment.Environment;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

/**
 * A procedure call. Represents a procedure call in the AST.
 *
 * @author Max Blennemann
 * @version 4/13/22
 */
public class ProcedureCall implements Statement, Expression
{

    private final String procedureCalled;
    private final Expression[] arguments;

    /**
     * Creates a new procedure call.
     *
     * @param procedureCalled The name of the procedure called
     * @param arguments       The arguments of the procedure call
     */
    public ProcedureCall(String procedureCalled, Expression[] arguments)
    {
        this.procedureCalled = procedureCalled;
        this.arguments = arguments;
    }

    /**
     * Gets the procedure called.
     *
     * @return the procedure called
     */
    public String getProcedureCalled()
    {
        return procedureCalled;
    }

    /**
     * Executes the procedure call.
     *
     * @param env the environment in which the statement is executed
     * @precondition e != null
     * @postcondition the code has been executed and the variables have been updated
     */
    @Override
    public void exec(@NotNull Environment env)
    {
        Object[] args =
                Arrays.stream(arguments).map(expression -> expression.evaluate(env)).toArray();
        Environment newEnvironment;
        if (env.hasProcedure(procedureCalled))
        {
            newEnvironment = env.global().clone();
            ProcedureDeclaration pro = env.getProcedure(procedureCalled);
            for (int i = 0; i < pro.getParameters().length; i++)
            {
                newEnvironment.getAllVars().put(pro.getParameters()[i].getName(), args[i]);
            }
            pro.getStatement().exec(newEnvironment);
        }
        else
            throw new IllegalArgumentException("Procedure '" + procedureCalled + "' not found");
        for (Map.Entry<String, Object> entry : newEnvironment.getAllVars().entrySet())
            if (env.hasVariable(entry.getKey())
                    && !env.getProcedure(procedureCalled).hasParameter(entry.getKey()))
            {
                env.setVariable(entry.getKey(), entry.getValue());
            }
    }

    /**
     * Evaluates the variable from the given environment.
     *
     * @param e the environment to pull variable values from
     * @return the value of the variable
     * @precondition e != null
     * @postcondition the variable has been evaluated and the code has been run
     */
    @Override
    public Object evaluate(Environment e)
    {
        exec(e);
        return e.getVariable(procedureCalled);
    }

    /**
     * Returns a string representation of the procedure call.
     *
     * @return a string representation of the procedure call.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(arguments).forEachOrdered(argument ->
        {
            sb.append(argument.toString());
            sb.append(", ");
        });
        if (arguments.length > 0)
            sb.delete(sb.length() - 2, sb.length());
        return "Procedure(" + procedureCalled + "(" + sb + ")" + ")";
    }

    /**
     * Returns the required assembly code to run the Statement.
     *
     * @param e the emitter to use
     */
    @Override
    public void compile(Emitter e)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
