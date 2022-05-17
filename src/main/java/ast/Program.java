package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The Program class is the root of the AST. It contains a list of
 * declarations and a list of statements.
 *
 * @author Max Blennemann
 * @version 3/31/22
 */
public class Program implements Statement
{
    private final Statement toRun;
    private final Environment globalEnvironment;


    /**
     * Constructor for a Program
     *
     * @param s the program to run
     * @param e the environment to use
     */
    public Program(Statement s, Environment e)
    {
        toRun = s;
        globalEnvironment = e;
    }

    /**
     * Executes the program
     *
     * @param e the environment to use
     */
    @Override
    public void exec(Environment e)
    {
        e.setGlobalEnvironment(globalEnvironment);
        toRun.exec(e.global());
    }

    /**
     * Returns the string representation of the program
     *
     * @return the string representation of the program
     */
    @Override
    public String toString()
    {
        return toRun.toString();
    }

    /**
     * Returns the required assembly code to run the Statement.
     *
     * @param e the emitter to use
     */
    @Override
    public void compile(Emitter e)
    {
        String name = e.getFileName();
        if (name.contains(".asm"))
            name = name.substring(0, e.getFileName().length() - 4);
        e.emit("# Program Generated from AST lab by Max Blennemann from " + name);
        //declares all variables
        e.emit(".data");
        for (String varName : globalEnvironment.getAllVars().keySet())
        {
            e.emit("var" + varName + ":");
            e.emit(".word 0");
        }
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:");
        toRun.compile(e);
    }
}