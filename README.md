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

2. [Soot](https://github.com/soot-oss/soot)
1. [Java to AST Visualization](https://github.com/Program-Analysis/Java-to-AST-with-Visualization)
