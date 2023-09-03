%%

%{
  private AsdrSample yyparser;

  public Yylex(java.io.Reader r, AsdrSample yyparser) {
    this(r);
    this.yyparser = yyparser;
  }

public static int IDENT = 257;
public static int NUM = 258;

public static int IF 			= 259; 
public static int ELSE 		= 260;
public static int PUBLIC 	= 261;
public static int PRIVATE = 262;
public static int CLASS		= 263;
public static int EQUALS	= 264;
public static int INT = 265;
public static int STATIC = 266;
public static int VOID = 267;
public static int MAIN = 268;
public static int WHILE = 269;
public static int TRUE = 270;
public static int FALSE = 271;
public static int THIS = 272;
public static int NEW = 273;
public static int BOOLEAN = 274;
public static int EXTENDS = 275;
public static int RETURN = 276;
public static int PRINT = 277;
public static int STRING = 278;
public static int COMMENT = 279;
public static int CMDO = 280;


%} 
%integer
%line
%char

DIGIT= [0-9]
LETTER=		[a-zA-Z]
WHITESPACE= [ \t]
SYMBOL = [^\>]*
LineTerminator = \r|\n|\r\n



%%
"//" [^\r\n]* { }
"/*" .* "*/" { }


if				{return IF;}
else			{return ELSE;} 
public		{return PUBLIC;}
private		{return PRIVATE;}
class			{return CLASS;}
int       {return INT;}
static    {return STATIC;}
void      {return VOID;}
main      {return MAIN;}
while     {return WHILE;}
true      {return TRUE;}
false     {return FALSE;}
this      {return THIS;}
new       {return NEW;}
boolean   {return BOOLEAN;}
extends   {return EXTENDS;}
return    {return RETURN;}
System.out.println  {return PRINT;}
String    {return STRING;}
cmdo {return CMDO;}


{LETTER}({LETTER}|{DIGIT}|_)* {return IDENT;}
{DIGIT}+                    {return NUM;}

":" |
"{" |
"}" |
"[" |
"]" |
"(" |
")" |
"." |
";" |
"_" |
"-" |
"=" |
"*" |
"<" |
"!" |
"?" |
"&&" |
"+" |
"," {return yytext().charAt(0);}

{WHITESPACE}+ { }
{LineTerminator} {}
. {System.out.println(yyline+1 + ": caracter invalido: "+yytext());}