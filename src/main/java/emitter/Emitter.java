package emitter;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Emitter class. This class is responsible for emitting the code to a file.
 *
 * @author Max Blennemann
 * @version 4/27/22
 */
public class Emitter
{
    private final PrintWriter out;
    private final String fileName;

    //creates an emitter for writing to a new file with given name
    public Emitter(String outputFileName)
    {
        fileName = outputFileName;
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    //prints one line of code to file (with non-labels indented)
    public void emit(String code)
    {
        if (!code.endsWith(":"))
            code = "\t" + code;
        out.println(code);
    }

    public String getFileName()
    {
        return fileName;
    }

    //closes the file.  should be called after all calls to emit.
    public void close()
    {
        out.close();
        System.out.println("Successfully finished writing to: " + fileName);
    }

    private int labelCount = 0;

    /**
     * Creates a new label every time.
     *
     * @return a new label
     */
    public String label()
    {
        return "label" + labelCount++;
    }
    //todo add emitter stuff
}


