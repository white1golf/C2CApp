// import ANTLR runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.util.*;

class C2CVisitor extends CBaseVisitor<Integer>
{
	/*
		Skeleton code is using this StringBuilder to contain every String value as long String. It doesn't look efficient to use Stack data structure
		Because it just contain StringBuilder as first element and whenever it need to add new string it just get first element's reference and add String value to it
		by append method of StringBuilder class. However, at this moment I don't change it.
	 */
	Stack<StringBuilder> out = new Stack<StringBuilder>();
	C2CVisitor(){
		out.push(new StringBuilder(""));
	}
	@Override public Integer visitDirectiveDefinition(@NotNull CParser.DirectiveDefinitionContext ctx) { 
		if(ctx.getParent() instanceof CParser.IncListContext)
			for (int i=0; i< ctx.getChildCount(); i++)
				out.peek().append(ctx.getChild(i).getText()+"\n");	// Print IncList
		return visitChildren(ctx); 
	}
	@Override public Integer visitFunction(@NotNull CParser.FunctionContext ctx) { 
		for (int i=0; i < ctx.getChildCount(); i++)
			if (ctx.getChild(i) instanceof TerminalNode)
				out.peek().append(ctx.getChild(i).getText()+" ");		// Print Function without CompoundStmt
		return visitChildren(ctx); 
	}
	@Override public Integer visitTerminal(TerminalNode node) {
		if(node.getParent().getParent().getParent() instanceof CParser.ProgramContext==false)
			out.peek().append(node.getText()+'\n'); 			// Print TerminalNode
		return null;
	}
	
}

public class C2CApp {
	public static void main(String[] args) throws IOException {
		// Input file		
		if (args.length != 1){
			System.err.println("Input C file");
			System.exit(1);
		}
		String filename = args[0];
		System.setIn(new FileInputStream(filename)); 
		InputStream input = System.in;

		// Get lexer
		CLexer lexer = new CLexer(new ANTLRInputStream(input));
		// Get a list of matched tokens
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		// Pass tokens to parser
		CParser parser = new CParser(tokens);
		// ParseTree
		ParseTree tree = parser.program();
		// our visitor
		C2CVisitor visitor = new C2CVisitor();
		// visit CParser
		visitor.visit(tree);
	
		// Output file
		FileOutputStream output = new FileOutputStream(new File("output_"+args[0]));
		System.out.println("Output file name:  output_"+args[0]);
		output.write(visitor.out.peek().toString().getBytes());
		output.flush();
		output.close();
	}
} 
