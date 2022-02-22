package staticanalysis;

import soot.*;
import soot.jimple.toolkits.callgraph.*;
import soot.options.*;
import soot.toolkits.graph.*;

import java.io.*;
import java.util.*;

public class GenerateCFG
{	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Generating CFG dotfile ...");
		generateCFG(args);
	}

	private static void generateCFG (String[] args) throws FileNotFoundException {
		PrintStream cfg_out = new PrintStream(new File("cfg-dotfile.txt"));
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
				SootMethod src = Scene.v().getMainClass().getMethodByName(
						"main");
				UnitGraph cfg_graph = new ClassicCompleteUnitGraph(src.getActiveBody());
				// Now that you have the call graph, you can start doing whatever type of
				// analysis needed for your problem. Below, we are going to traverse the
				// generated call graph and print caller/callee relationships (BFS)

				cfg_out.println("digraph {");
				List<Unit> nodes = new ArrayList<>();
				// TODO: CREATE START NODE THAT CONNECTS TO ALL ENTRYPOINTS
				//  (I.E. getHeads() )
				nodes.add(cfg_graph.getHeads().get(0));
				Set<Unit> isVisited = new HashSet<>();

				System.out.println("Heads are: " + cfg_graph.getHeads());
				System.out.println("Tails are: " +cfg_graph.getTails());

				System.out.println("first predec: "+ cfg_graph.getSuccsOf(cfg_graph.getHeads().get(0)));
				while(!nodes.isEmpty()){
					Unit parent = nodes.get(0);
					List<Unit> succesorNodes = cfg_graph.getSuccsOf(parent);
					for (Unit succesorNode : succesorNodes) {
						if (!isVisited.contains(succesorNode)) {
							isVisited.add(succesorNode);
							nodes.add(succesorNode);
							cfg_out.println("\"" + parent + "\" -> \"" + succesorNode + "\"");
						}
					}
					nodes.remove(0);
				}
				cfg_out.println("}");
			}

		}));

		Main.main(args);
	}
}
