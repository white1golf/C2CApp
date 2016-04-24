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
	//Stack<StringBuilder> out = new Stack<StringBuilder>();
	StringBuilder sb = new StringBuilder();
	C2CVisitor(){
		//out.push(new StringBuilder(""));
	}
	//완료
	@Override public Integer visitDirectiveDefinition(@NotNull CParser.DirectiveDefinitionContext ctx) {
		if(ctx.getParent() instanceof CParser.IncListContext)
			for (int i=0; i< ctx.getChildCount(); i++)
				sb.append(ctx.getChild(i).getText()+"\n");// Print IncList
				//Put one more \n at the end of directiveList
			sb.append("\n");
		//test what's gonna chane
		return super.visitDirectiveDefinition(ctx);//visitChildren(ctx);
	}

	@Override //각 변수 선언전에 줄 바꿈 입력.
	public Integer visitDeclaration(CParser.DeclarationContext ctx) {
		sb.append("\n");
		return super.visitDeclaration(ctx);
	}

	@Override//추가.
	public Integer visitIdentifier(CParser.IdentifierContext ctx) {
		//변수 선언 시 변수 타입 이후 띄어쓰기.
		sb.append(" ");
		return super.visitIdentifier(ctx);
	}

	@Override public Integer visitFunction(@NotNull CParser.FunctionContext ctx) {
		for (int i=0; i < ctx.getChildCount(); i++)
			if (ctx.getChild(i) instanceof TerminalNode) {
				sb.append(ctx.getChild(i).getText() + " ");        // Print Function without CompoundStmt
				System.out.println(ctx.getChild(i).getText());
			}
		//test what's gonna change
		ctx.depth();
		return visitChildren(ctx);
	}

	@Override //추가
	public Integer visitCompoundStmt(CParser.CompoundStmtContext ctx) {
		//sb.append("\n");
		//ctx.
		return super.visitCompoundStmt(ctx);
	}

	@Override //추가
	public Integer visitStmtList(CParser.StmtListContext ctx) {
		//sb.append("\n");
		return super.visitStmtList(ctx);
	}

	@Override //
	public Integer visitStmt(CParser.StmtContext ctx) {
		//stmtList 다음에 바로 묶여 있는 녀석들은 일단 보류해보자
		//if(!(ctx.getParent() instanceof CParser.StmtListContext))
			sb.append("\n");
		return super.visitStmt(ctx);
	}


	@Override public Integer visitTerminal(TerminalNode node) {
		if(node.getText().compareTo("}") == 0)
			sb.append("\n");
		//Check if the depth of node is 3. it means that this terminal is as same level as #include or function statment.
		//However it can be possible to have global variable such as int a = 1; at this depth level so it need to be fixed.
		if(!(node.getParent().getParent().getParent() instanceof CParser.ProgramContext))
			sb.append(node.getText()/*+" "*/); 			// Print TerminalNode
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
		output.write(visitor.sb.toString().getBytes());
		output.flush();
		output.close();
	}
} 
