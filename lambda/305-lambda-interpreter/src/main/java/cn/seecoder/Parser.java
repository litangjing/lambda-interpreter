package cn.seecoder;

import java.util.ArrayList;

public class Parser {
    Lexer lexer;

    public Parser(Lexer l){
        lexer = l;
    }

    public AST parse(){
        ArrayList<String> ctx=new ArrayList<String> ();
        AST ast = term(ctx);
//       System.out.println(lexer.match(TokenType.EOF));
        return ast;
    }

    private AST term(ArrayList<String> ctx){
        //Term ::= Application| LAMBDA LCID DOT Term
        // write your code here
        if(lexer.skip(TokenType.LAMBDA)){
            if(lexer.next(TokenType.LCID)){
                String param=lexer.tokenvalue;
                lexer.match(TokenType.LCID);
                if(lexer.skip(TokenType.DOT)){
                    ctx.add(0,param);
                    AST myterm=term(ctx);
                    int value=ctx.indexOf(param);
                    ctx.remove(param);
                    return new Abstraction(new Identifier(param,value),myterm);
                }
            }
        }
        return application(ctx);
    }

    private AST application(ArrayList<String> ctx){
        //Application ::= Application Atom| Atom
        // write your code here
        Application tem=new Application(atom(ctx),atom(ctx));
        while (true){
            if(tem.rhs==null){
                return tem.lhs;
            }
            else {
                tem.lhs = new Application(tem.lhs, tem.rhs);
                tem.rhs = atom(ctx);
            }
        }
    }

    private AST atom(ArrayList<String> ctx){
        //Atom ::= LPAREN Term RPAREN| LCID
        // write your code here
        if(lexer.skip(TokenType.LPAREN)){
                AST myterm=term(ctx);
                if (lexer.skip(TokenType.RPAREN)) {
                    return myterm;
                }
            }
        else if(lexer.next(TokenType.LCID)){
            String param=lexer.tokenvalue;
            int value=ctx.indexOf(param);
            lexer.match(TokenType.LCID);
            return new Identifier(param,value);
        }
        return null;
    }
}
