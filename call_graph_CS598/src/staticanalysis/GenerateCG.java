package staticanalysis;

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
		String path = System.getProperty("user.dir")+"/src";

		// Setting the classpath programatically
		Options.v().set_prepend_classpath(true);
		Options.v().set_soot_classpath(path);

		// The second argument is the path to the main class of a project you want to analyze
		// (in this case, testers/ExampleCode.java)
		args = new String[]{"-w", "testers.ExampleCode"};

		PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTrans", new SceneTransformer() {

			@Override
			protected void internalTransform(String phaseName, Map options) {
				CHATransformer.v().transform();

				// This line generates the call graph of the project you're going to analyze
				CallGraph cg = Scene.v().getCallGraph();
				// Now that you have the call graph, you can start doing whatever type of
				// analysis needed for your problem. Below, we are going to traverse the
				// generated call graph and print caller/callee relationships (BFS)

				cg_out.println("digraph {");
				SootMethod src = Scene.v().getMainClass().getMethodByName("main");
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
