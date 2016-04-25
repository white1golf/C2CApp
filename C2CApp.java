// import ANTLR runtime libraries
import com.sun.prism.image.CompoundTexture;
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

	StringBuilder sb  = new StringBuilder();

	public int indentDepthFinder(ParseTree ctx){

			int indentDepth = 0;
			for(ParseTree p = ctx; p != null;) {
				p = p.getParent();
				//일반적인 compound의 경우 그리고 두번째 가능성은 switch문 내부의 caseList
				if(p instanceof CParser.CompoundStmtContext || p instanceof CParser.CaseListContext) ++indentDepth;
			}
			//더 특별한 케이스는 case 문 내부의 stmt와 break로 이 경우 하나 더 들여써야하므로 +1을 더함
			if(ctx.getText().equals("break;") || ctx.getParent().getParent() instanceof CParser.CaseListContext) ++indentDepth;

			// compooundStmtContext의 자식으로 있는 { 나 } 의 경우 indent에서 제외됨
			// 두번째 조건은 switch 문의 } 괄호는 -1 처리를 피해주기 위함.
			if(ctx instanceof TerminalNode && ctx.getParent() instanceof CParser.CompoundStmtContext) --indentDepth;

			return indentDepth;
		}

	@Override public Integer visitDirectiveDefinition(@NotNull CParser.DirectiveDefinitionContext ctx) {
		if(ctx.getParent() instanceof CParser.IncListContext)
			for (int i=0; i< ctx.getChildCount(); i++)
				sb.append(ctx.getChild(i).getText()+"\n");// Print IncList
				//Put one more \n at the end of directiveList
			sb.append("\n");
		//test what's gonna chane
		return super.visitDirectiveDefinition(ctx);//visitChildren(ctx);
	}

	@Override
	public Integer visitDeclaration(CParser.DeclarationContext ctx) {
		sb.append("\n");
		int indentDepth = indentDepthFinder(ctx);
		if(indentDepth != 0)
			for(int i = 0; i < indentDepth; i++) sb.append("\t");

		return super.visitDeclaration(ctx);
	}

	@Override public Integer visitFunction(@NotNull CParser.FunctionContext ctx) {
		for (int i=0; i < ctx.getChildCount(); i++)
			if (ctx.getChild(i) instanceof TerminalNode) {
				//함수의 타입과 이름 사이의 스페이스바 생성
				if(((TerminalNode) ctx.getChild(i)).getSymbol() == ctx.Id().getSymbol()) sb.append(" ");
				sb.append(ctx.getChild(i).getText());        // Print Function without CompoundStmt
				System.out.println(ctx.getChild(i).getText());
			}
		//test what's gonna change
		ctx.depth();
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCompoundStmt(CParser.CompoundStmtContext ctx) {
		if(!(ctx.parent instanceof CParser.StmtContext))
			sb.append("\n");
		return super.visitCompoundStmt(ctx);
	}

	@Override //
	public Integer visitStmt(CParser.StmtContext ctx) {
		//return 문은 특별히 한줄 더 뛰움
		if(ctx.getChild(0) instanceof CParser.RetStmtContext) sb.append("\n");
		sb.append("\n");
		int indentDepth = indentDepthFinder(ctx);
		if(indentDepth != 0)
			for(int i = 0; i < indentDepth; i++) sb.append("\t");
		return super.visitStmt(ctx);

	}

	@Override public Integer visitTerminal(TerminalNode node) {

		//각 oprerator 및 = , : 앞에 스페이스 생성
		if(
				node.getText().compareTo("=")==0 ||
				node.getText().compareTo("-")==0 ||
				node.getText().compareTo("+")==0 ||
				node.getText().compareTo("*")==0 ||
				node.getText().compareTo("/")==0 ||
				node.getText().compareTo("<")==0 ||
				node.getText().compareTo(">")==0 ||
				node.getText().compareTo("<=")==0 ||
				node.getText().compareTo(">=")==0 ||
				node.getText().compareTo("==")==0 ||
				node.getText().compareTo("!=")==0)
			sb.append(" ");

		//일반적인 }의 뎁스를 여기서 반영하며 switchStmt의 }도 여기서 반영함.
		if(node.getText().compareTo("}") == 0) {
			sb.append("\n");
			int indentDepth = indentDepthFinder(node);
			if (indentDepth != 0)
				for (int i = 0; i < indentDepth; i++) sb.append("\t");
		}

		//case를 표시하는 경우
		if(node.getText().compareTo("case") ==0){
			sb.append("\n");
			int indentDepth = indentDepthFinder(node);
			if(indentDepth !=0 )
				for(int i = 0; i <indentDepth; i++) sb.append("\t");
		}

		//break를 표시하는 경우
		if(node.getText().compareTo("break;") ==0){
			sb.append("\n");
			int indentDepth = indentDepthFinder(node);
			if(indentDepth !=0 )
				for(int i = 0; i <indentDepth; i++) sb.append("\t");
		}

		//Check if the depth of node is 3. it means that this terminal is as same level as #include or function statment.
		//However it can be possible to have global variable such as int a = 1; at this depth level so it need to be fixed.
		if(!(node.getParent().getParent().getParent() instanceof CParser.ProgramContext))
			sb.append(node.getText()); 			// Print TerminalNode

		//case 후 띄어쓰기 표시
		if(node.getText().compareTo("case") ==0) sb.append(" ");

		// 'return' 이라는 키워드 뒤에 띄어쓰기 한번
		if(node.getText().compareTo("return") == 0) sb.append(" ");

		//각 operator 및 = ,:  끝에 스페이스생성
		if(
				node.getText().compareTo("=")==0 ||
				node.getText().compareTo("-")==0 ||
				node.getText().compareTo("+")==0 ||
				node.getText().compareTo("*")==0 ||
				node.getText().compareTo("/")==0 ||
				node.getText().compareTo("<")==0 ||
				node.getText().compareTo(">")==0 ||
				node.getText().compareTo("<=")==0 ||
				node.getText().compareTo(">=")==0 ||
				node.getText().compareTo("==")==0 ||
				node.getText().compareTo("!=")==0 ||
				node.getText().compareTo(":")==0 )
			sb.append(" ");


		//for문 등에 들어가는 인자값을 나열할 때 , 다음에는 항상 스페이스
		if(node.getText().compareTo(",") ==0) sb.append(" ");

		//Type 선언 이후에는 공백문자.
		if(
				node.getText().compareTo("int") == 0  ||
				node.getText().compareTo("float") == 0||
				node.getText().compareTo("void") == 0 ||
				node.getText().compareTo("char") == 0 )
			sb.append(" ");
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
