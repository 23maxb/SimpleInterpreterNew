package ast;

import emitter.Emitter;
import environment.Environment;

import java.util.Arrays;

/**
 * Represents an abstract syntax tree in a pascal program.
 *
 * @author Max Blennemann
 * @author 3/25/22
 */
public class ProcedureDeclaration implements Statement
{
    public Statement statement;
    public String name;
    public Variable[] parameters;

    /**
     * Declares a procedure.
     *
     * @param statement  the statement(s) to run if the program is called
     * @param name       the name of the method
     * @param parameters the parameters that are required for this method to run
     */
    public ProcedureDeclaration(Statement statement, String name,
                                Variable[] parameters)
    {
        this.statement = statement;
        this.name = name;
        this.parameters = parameters;
    }

    /**
     * Creates a procedure declaration object that creates a method that can be referenced later
     *
     * @param name       Method name
     * @param statement  the statement to run usually a block
     * @param parameters the parameters that are required for this method to run
     */
    public ProcedureDeclaration(String name, Statement statement,
                                Variable[] parameters)
    {
        this(statement, name, parameters);
    }

    /**
     * Gets the statement
     *
     * @return the statement to be run when this procedure is called
     */
    public Statement getStatement()
    {
        return statement;
    }

    /**
     * Sets a new procedure to be run instead
     *
     * @param statement the new statement to be run
     */
    public void setStatement(Statement statement)
    {
        this.statement = statement;
    }

    /**
     * Gets the name of the procedure.
     *
     * @return the name of the procedure
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the procedure
     *
     * @param name the new name of the procedure
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the parameter with the given name
     *
     * @return the parameters
     */
    public Variable[] getParameters()
    {
        return parameters;
    }

    /**
     * Sets the parameters of the procedure.
     *
     * @param parameters the new parameters
     */
    public void setParameters(Variable[] parameters)
    {
        this.parameters = parameters;
    }

    /**
     * Returns a string representation of the procedure declaration.
     *
     * @return a string representation of the procedure declaration.
     */
    @Override
    public String toString()
    {
        return "Method: (" + name + "," + statement + ")";
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

    /**
     * Executes the procedure.
     *
     * @param currentEnvironment the current environment
     */
    public void exec(Environment currentEnvironment)
    {
        currentEnvironment.addProcedure(this);
    }

    /**
     * Returns the parameters as a string.
     *
     * @param key the parameter to check
     * @return true if the parameter is in the list
     * otherwise false
     */
    public boolean hasParameter(String key)
    {
        return Arrays.stream(parameters).anyMatch(parameter -> parameter.getName().equals(key));
    }
}
