# Static Analysis for CS598JBR - ML4SWE @ UIUC

## Requirements

1.  Graphviz 2.50.0 (on Windows machine, run `winget install graphviz`)
2.  JRE 1.8 (for CG and CFG), JRE 14 for AST builder

## Getting Started

1. Import project to IDEA as Java Project
2. Add `sootclasses-trunk-jar-with-dependencies.jar` to `Project Structure` library
3. Navigate to `/src/staticanalysis` of any project to analyze (except JavaAST)
4. Run `GenerateCG` or `GenerateCFG` to produce corresponding static analysis .DOT file
5. For AST generation, refer to [this](https://github.com/Program-Analysis/Java-to-AST-with-Visualization) README
6. (Optional) Generate final visualization through Graphviz / any other online DOT visualization tool
   <br>
   Graphviz example usage: `dot -Tjpeg OUTPUT_FILE.dot > OUTPUT_IMG.dot`

## External Source

1. [Soot](https://github.com/soot-oss/soot)
2. [Java to AST Visualization](https://github.com/Program-Analysis/Java-to-AST-with-Visualization)

## Issues

Our CG and CFG works as expected on the smaller sample script. We're encountering Soot-related issue when trying to analyze `Mockito` with the following error:

```
java.lang.RuntimeException: This operation requires resolving level SIGNATURES but java.main.org.mockito.AdditionalAnswers is at resolving level HIERARCHY
If you are extending Soot, try to add the following call before calling soot.Main.main(..):
Scene.v().addBasicClass(java.main.org.mockito.AdditionalAnswers,SIGNATURES);
Otherwise, try whole-program mode (-w).
```

## Example Visualization

### Call-Graph Visualization

![CG Image](/examples/cg-img.jpeg "CG")

### Control-Flow-Graph Visualization

![CFG Image](/examples/cfg-img.jpeg "CFG")

### Abstract Syntax Tree Visualization

![AST Image](/examples/mockito-ast.jpeg "AST")
