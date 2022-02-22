import soot.*;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Targets;
import soot.options.Options;

import java.io.*;
import java.util.*;

public class GenerateCG
{
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Generating Call Graph dotfile ...");
		generateCG(args);
	}

	private static void generateCG(String[] args) throws FileNotFoundException {
		PrintStream cg_out = new PrintStream(new File("cg-dotfile.txt"));
		// Soot classpath
		String classPath = System.getProperty("user.dir")+"/build/classes";

		// Setting the classpath programatically
		Options.v().set_prepend_classpath(true);
		Options.v().set_soot_classpath(classPath);
        Scene.v().loadNecessaryClasses();

		// The second argument is the path to the main class of a project you want to analyze
		// (in this case, testers/ExampleCode.java)
		args = new String[]{"-w", "-process-dir", classPath};

//        Main.main(args);
//
//        CallGraph cg = Scene.v().getCallGraph();

		PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTrans", new SceneTransformer() {

			@Override
			protected void internalTransform(String phaseName, Map options) {
                System.out.println("Before transform");
				CHATransformer.v().transform();

				// This line generates the call graph of the project you're going to analyze

                System.out.println("Before callgraph");
				CallGraph cg = Scene.v().getCallGraph();
				// Now that you have the call graph, you can start doing whatever type of
				// analysis needed for your problem. Below, we are going to traverse the
				// generated call graph and print caller/callee relationships (BFS)
                System.out.println("AFTER callgraph");

				cg_out.println("digraph {");
				SootMethod src = Scene.v().getMainClass().getMethodByName(
				    "mock");
				List<SootMethod> nodes = new ArrayList<>();
				nodes.add(src);
				while(!nodes.isEmpty()){
					SootMethod parent = nodes.get(0);
					Iterator<MethodOrMethodContext> targets = new Targets(cg.edgesOutOf(parent));
					while (targets.hasNext()) {
						SootMethod child = (SootMethod)targets.next();
						if(!child.getSignature().contains("init"))
							nodes.add(child);
						cg_out.println("\"" + parent.getDeclaringClass()+
								"."+parent.getName()+ "\"" +
								" -> " + "\"" + child.getDeclaringClass()+"."+child.getName()+ "\""+ ";");
					}
					nodes.remove(0);
				}
				cg_out.println("}");
			}

		}));

		Main.main(args);
	}
}
