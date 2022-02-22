import soot.*;
import soot.jimple.toolkits.callgraph.*;
import soot.options.*;

import java.io.*;
import java.util.*;

public class GenerateAST
{
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Generating AST dotfile ...");
		generateAST(args);
	}

	private static void generateAST(String[] args) throws FileNotFoundException {
		System.out.println("Generating AST ...");
	}
}
