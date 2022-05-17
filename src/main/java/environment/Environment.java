package environment;

import ast.ProcedureDeclaration;

import java.util.ArrayList;
import java.util.HashMap;

public class Environment implements Cloneable
{
    public HashMap<String, Object> allVars;
    public ArrayList<ProcedureDeclaration> allProcedures;
    public Environment parent;

    public Environment(HashMap<String, Object> newVars,
                       ArrayList<ProcedureDeclaration> newProcedures)
    {
        this.allVars = newVars;
        this.setAllProcedures(newProcedures);
    }

    public Environment(HashMap<String, Object> newVars,
                       ArrayList<ProcedureDeclaration> newProcedures, ArrayList<String> variables)
    {
        this(newVars, newProcedures);
        variables.forEach(s -> allVars.put(s, 0));
    }

    public Environment(ArrayList<ProcedureDeclaration> newProcedures, ArrayList<String> variables)
    {
        this();
        setAllProcedures(newProcedures);
        variables.forEach(s -> allVars.put(s, 0));
    }


    public Environment()
    {
        this(new HashMap<>(), new ArrayList<>());
    }

    public Environment(Environment parent)
    {
        this(new HashMap<>(), new ArrayList<>());
        setParent(parent);
    }

    public Environment(HashMap<String, Object> a, ArrayList<ProcedureDeclaration> b,
                       Environment env)
    {
        this(a, b);
        setParent(env);
    }

    public Environment(ArrayList<ProcedureDeclaration> procedures)
    {
        this();
        setAllProcedures(procedures);
    }

    private void setParent(Environment parent)
    {
        this.parent = parent;
    }

    public Environment getParent()
    {
        if (parent != null)
            return parent;
        return this;
    }

    //associates the given variable name with the given value
    public void setVariable(String variable, Object value)
    {
        allVars.put(variable, value);
    }

    //returns the value associated with the given variable
//name
    public Object getVariable(String variable)
    {
        return allVars.get(variable);
    }

    public boolean hasVariable(String a)
    {
        return getAllVars().containsKey(a);
    }

    public void setAllVars(HashMap<String, Object> allVars)
    {
        this.allVars = allVars;
    }

    public ArrayList<ProcedureDeclaration> getAllProcedures()
    {
        return allProcedures;
    }

    public void setAllProcedures(ArrayList<ProcedureDeclaration> allProcedures)
    {
        this.allProcedures = allProcedures;
    }

    public void addProcedure(ProcedureDeclaration procedureDeclaration)
    {
        allProcedures.add(procedureDeclaration);
    }

    public HashMap<String, Object> getAllVars()
    {
        return allVars;
    }

    public Environment global()
    {
        if (parent == null)
            return this;
        else
            return parent.global();
    }

    @Override
    public String toString()
    {
        return "Environment (" + getAllVars().toString() + ")";
    }

    public boolean hasProcedure(String procedureCalled)
    {
        try
        {
            getProcedure(procedureCalled);
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
        return true;
    }

    @Override
    public Environment clone()
    {
        return new Environment(new HashMap<>(getAllVars()),
                (ArrayList<ProcedureDeclaration>) getAllProcedures().clone(),
                this);
    }

    public ProcedureDeclaration getProcedure(String procedureCalled)
    {
        for (int i = 0; i < getAllProcedures().size(); i++)
        {
            if (getAllProcedures().get(i).getName().equals(procedureCalled))
                return getAllProcedures().get(i);
        }
        throw new IllegalArgumentException("No procedure with name " + procedureCalled + " found.");
    }

    public void setGlobalEnvironment(Environment globalEnvironment)
    {
        this.parent = globalEnvironment;
    }
}
