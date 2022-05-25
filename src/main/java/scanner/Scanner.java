package scanner;

import java.io.*;
import java.util.regex.Pattern;

//Answers to the Questions for Lab:	Lexical	Analysis
// If the character was a new line then the compiler
// would have returned the next token after the new line ignoring the all the \n until the next
// token.
// If that token was a ( then the program would resume as normal.
// If the character instead was a parenthesis then it would have known it was the keyword if.

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1
 *
 * @author Max Blennemann
 * @version 1/28/22
 *
 * Usage:
 * This object is used to scan input to make sure that it follows regex expressions for tokens.
 */
public class Scanner
{
    private final BufferedReader in;
    private char currentChar;
    private boolean eof;
    private final boolean debug = false;

    public static void main(String[] args) throws FileNotFoundException, ScanErrorException
    {
        InputStream inputStream = new FileInputStream("C:\\Users\\maxbl\\IdeaProjects" +
                "\\SimpleInterpreter\\src\\main\\java\\parser\\simpleTest.txt");
        Scanner a = new Scanner(inputStream);
        while (a.hasNext())
            System.out.println(a.nextToken());
    }

    public void close()
    {
        try
        {
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void debug(Object a)
    {
        if (debug)
            System.out.println(a);
    }

    public Scanner(InputStream inStream) throws ScanErrorException
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that
     * scans a given input string.  It sets the end-of-file flag and then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     *
     * @param inString the string to scan
     */
    public Scanner(String inString) throws ScanErrorException
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        eat(currentChar);
    }

    /**
     * Scans an entire number and then returns it.
     *
     * @return the entire number
     * @throws ScanErrorException if the scanner runs into an error! (most commonly end of file)
     */
    private @org.jetbrains.annotations.NotNull String scanNumber() throws ScanErrorException
    {
        StringBuilder a = new StringBuilder();
        while (isDigit(currentChar))
        {
            a.append(currentChar);
            eat(currentChar);
        }
        return a.toString();
    }

    /**
     * Scans any identifiers. (variable names)
     *
     * @return the identifier.
     * @throws ScanErrorException if the scanner runs into an error! (most commonly end of file)
     */
    private String scanIdentifier() throws ScanErrorException
    {
        StringBuilder a = new StringBuilder();
        while (isDigit(currentChar) || isLetter(currentChar))
        {
            a.append(currentChar);
            eat(currentChar);
        }
        return a.toString();
    }

    /**
     * Scans any operands such as division and adding.
     *
     * @return the operand.
     * @throws ScanErrorException if the scanner runs into an error! (most commonly end of file)
     */
    private String scanOperand() throws ScanErrorException
    {
        StringBuilder a = new StringBuilder();
        if (currentChar == ')' || currentChar == '(')
        {
            char b = currentChar;
            eat(currentChar);
            return String.valueOf(b);
        }
        else
            while (isOperand(currentChar) && currentChar != '(' && currentChar != ')')
            {
                a.append(currentChar);
                eat(currentChar);
            }
        return a.toString();
    }


    /**
     * Scans semicolons.
     *
     * @return "/"
     * @throws ScanErrorException if the scanner runs into an error! (most commonly end of file)
     */
    private String scanSemiColon() throws ScanErrorException
    {
        StringBuilder a = new StringBuilder();
        while (';' == (currentChar))
        {
            a.append(currentChar);
            eat(currentChar);
        }
        return a.toString();
    }

    /**
     * Checks whether the input is an operand.
     *
     * @return true if the char given is an operand
     * otherwise false
     */
    private boolean isOperand(char a)
    {
        return (a == ((char) 45) || (a == '/') ||
                Pattern.matches("['>' '<' ':' ‘=’ ‘+’ ‘*’ ‘%’ ‘(‘ "
                                + "‘)’]",
                        String.valueOf(a)));
    }

    /**
     * Checks whether the input is a digit.
     *
     * @return true if the char given is a digit
     * otherwise false
     */
    private boolean isDigit(char a)
    {
        return (Pattern.matches("[0-9]", String.valueOf(a)));
    }

    /**
     * Checks whether the input is whitespace.
     *
     * @return true if the char given is whitespace
     * otherwise false
     */
    private boolean isWhiteSpace(char a)
    {
        return (Pattern.matches("[‘ ‘ ‘\\t’ ‘\\r’ ‘\\n’]", String.valueOf(a)));
    }

    /**
     * Checks whether the input is a letter.
     *
     * @return true if the char given is a letter
     * otherwise false
     */
    private boolean isLetter(char a)
    {
        return (Pattern.matches("[a-z]", String.valueOf(a).toLowerCase()));
    }

    /**
     * Gets the next character and sets currentChar to match it!
     *
     * @throws ScanErrorException if an IO exception is thrown, it will be caught so you can
     *                            trace it as a
     *                            ScanErrorException
     */
    private void getNextChar() throws ScanErrorException
    {
        try
        {
            int c = in.read();
            if (c == -1)
            {
                eof = true;
                return;
            }
            currentChar = (char) c;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new ScanErrorException(e.getMessage());
        }
    }

    /**
     * Throws a ScanErrorException if an illegal character was encountered.
     *
     * @throws ScanErrorException If an unexpected character was found while eating the file.
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected == currentChar || Pattern.matches(String.valueOf(expected),
                String.valueOf(currentChar)))
        {
            getNextChar();
            return;
        }
        throw new ScanErrorException("Illegal character - expected"
                + expected + "and found " + currentChar);
    }

    /**
     * Checks whether the end of the file has been reached.
     *
     * @return true if the end of the file has not been reached
     * otherwise false
     */
    public boolean hasNext()
    {
        return !eof;
    }

    /**
     * Returns the next token whether it be an operand or identifier or comment!
     *
     * @return The next token as a string
     */
    public String nextToken() throws ScanErrorException
    {
        if (eof)
        {
            return "EOF";
        }
        while (isWhiteSpace(currentChar))
            eat(currentChar);
        if (hasNext())
        {
            // Checks for single line and multiline comments!
            /*if (currentChar == '/')
            {
                eat(currentChar);
                if (currentChar == '/')
                {
                    try
                    {
                        return "//" + in.readLine();
                    }
                    catch (IOException e)
                    {
                        throw new ScanErrorException("Unexpected end of file!");
                    }
                }
                else if (currentChar == '*')
                {
                    //multiline comment detected!
                    StringBuilder a = new StringBuilder();
                    for (; ; )
                    {
                        eat(currentChar);
                        a.append(currentChar);
                        if (currentChar == '*')
                        {
                            eat(currentChar);
                            a.append(currentChar);
                            if (currentChar == '/')
                            {
                                return "/*" + a;
                            }
                        }
                    }
                }
                else
                {
                    return nextToken();
                }
            }*/
            // End of comment checking
            if (isOperand(currentChar))
            {
                debug("operand:");
                return scanOperand();
            }
            if (currentChar == (','))
            {
                eat(',');
                return ",";
            }
            if (isDigit(currentChar))
            {
                debug("Number:");
                return scanNumber();
            }
            if (isLetter(currentChar))
            {
                String a = scanIdentifier();
                debug("Identifier:");
                return a;
            }
            if (currentChar == ';')
            {
                debug("Semicolon:");
                return scanSemiColon();
            }
            if (currentChar == '.')
            {
                eof = true;
                return "EOF";
            }
        }
        throw new ScanErrorException("Unknown character: \"" + currentChar + "\".");
    }
}

