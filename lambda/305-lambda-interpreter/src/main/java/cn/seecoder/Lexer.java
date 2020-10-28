package cn.seecoder;


public class Lexer{

    public String source;
    public int index;
    public TokenType token;
    public String tokenvalue;

    public Lexer(String s){
        index = 0;
        source = s;
        nextToken();

    }
    //get next token
    public TokenType nextToken(){
        //write your code here
        String pattern1="\\s";
        String pattern2="[a-z]";
        String pattern3="[a-zA-Z]";

        char c;
        do{
            c=nextChar();
        }while ((c+"").matches(pattern1));

        switch (c){
            case '(': token=TokenType.LPAREN;tokenvalue="(";break;
            case ')': token=TokenType.RPAREN;tokenvalue=")";break;
            case '\\':token=TokenType.LAMBDA;tokenvalue="\\";break;
            case  '.': token=TokenType.DOT;tokenvalue=".";break;
            case  '\0':token=TokenType.EOF;tokenvalue="\0";break;
            default:
                if((c+"").matches(pattern2)){
                    String str="";
                    do{
                        str+=c;
                        c=nextChar();
                    }while ((c+"").matches(pattern3));
                    index--;
                    token=TokenType.LCID;
                    tokenvalue=str;
                    break;
                }
                else {
                    System.exit(0);
                }
        }
        System.out.println(token);
        return token;
    }

    // get next char
    private char nextChar(){
        //write your code here
        if(this.index>=source.length())
            return '\0';
        return source.charAt(index++);
    }


    //check token == t
    public boolean next(TokenType t){
        //write your code here
        if(token==t){
            return true;
        }
        return false;
    }

    //assert matching the token type, and move next token
    public void match(TokenType t){
        //write your code here
        if(token==t){
           nextToken();
        }
        else
            System.exit(0);
    }

    //skip token  and move next token
    public boolean skip(TokenType t){
        //write your code here
        if(token==t){
            nextToken();
            return true;
        }
        return false;
    }


}
