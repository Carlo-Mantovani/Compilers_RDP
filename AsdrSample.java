import java.io.*;

public class AsdrSample {

   private static final int BASE_TOKEN_NUM = 257;

   public static final int IDENT = 257;
   public static final int NUM = 258;
   public static final int IF = 259;
   public static final int ELSE = 260;
   public static final int PUBLIC = 261;
   public static final int PRIVATE = 262;
   public static final int CLASS = 263;
   public static final int EQUALS = 264;
   public static final int INT = 265;
   public static final int STATIC = 266;
   public static final int VOID = 267;
   public static final int MAIN = 268;
   public static final int WHILE = 269;
   public static final int TRUE = 270;
   public static final int FALSE = 271;
   public static final int THIS = 272;
   public static final int NEW = 273;
   public static final int BOOLEAN = 274;
   public static final int EXTENDS = 275;
   public static final int RETURN = 276;
   public static final int PRINT = 277;
   public static final int STRING = 278;
   public static final int COMMENT = 279;
   public static final int CMDO = 280;

   public static final String tokenList[] = { "IDENT",
         "NUM",
         "WHILE",
         "IF",
         "FI",
         "ELSE" };

   /* referencia ao objeto Scanner gerado pelo JFLEX */
   private Yylex lexer;

   public ParserVal yylval;

   private static int laToken;
   private boolean debug;

   /* construtor da classe */
   public AsdrSample(Reader r) {
      lexer = new Yylex(r, this);
   }

   /*
    * Program ::= MainClass ( ClassDeclaration )* <EOF>
    * MainClass ::= "class" Identifier "{" "public" "static" "void" "main" "("
    * "String" "[" "]" Identifier ")" "{" Statement "}" "}"
    * ClassDeclaration ::= "class" Identifier ( "extends" Identifier )? "{"
    * (VarDeclaration )* ( MethodDeclaration )* "}"
    * VarDeclaration ::= Type Identifier ";"
    * MethodDeclaration ::= "public" Type Identifier "(" ( Type Identifier (
    * ","Type Identifier )* )? ")" "{" ( VarDeclaration )* ( Statement )* "return"
    * 42 ";" "}"
    * Type ::= "int" TypeRest
    * | "boolean"
    * | Identifier
    * TypeRest ::= "[" "]"
    * | [empty]
    * Statement ::= CMDO
    * CMDO ::= Skip
    * Expression ::= Skip
    */

   private void Program() {
      if (laToken == CLASS) {// class token
         if (debug)
            System.out.println("Program --> MainClass ( ClassDeclaration )* <EOF>");
         MainClass();
         while (laToken == CLASS) {// class token
            if (debug)
               System.out.println("Program --> MainClass ( ClassDeclaration )* <EOF>");
            ClassDeclaration();
         }
         // verifica(Yylex.YYEOF);
      } else {
         yyerror("Program - Esperado token: class");
      }
   }

   private void MainClass() {
      if (laToken == CLASS) {// class token
         if (debug)
            System.out.println(
                  "MainClass --> class Identifier { public static void main ( String [ ] Identifier ) { Statement } }");
         verifica(CLASS);
         verifica(IDENT);
         verifica('{');
         verifica(PUBLIC);
         verifica(STATIC);
         verifica(VOID);
         verifica(MAIN);
         verifica('(');
         verifica(STRING);
         verifica('[');
         verifica(']');
         verifica(IDENT);
         verifica(')');
         verifica('{');
         Statement();
         verifica('}');
         verifica('}');
      } else {
         yyerror("MainClass - Esperado token: class");
      }
   }

   private void Statement() {
      verifica(CMDO);
      
   }
   private void ClassDeclaration() {
      if (laToken == CLASS) {// class tokenBASE_TOKEN_NUM
         if (debug)
            System.out.println(
                  "ClassDeclaration --> class Identifier ( extends Identifier )? { ( VarDeclaration )* ( MethodDeclaration )* }");
         verifica(CLASS);
         verifica(IDENT);
         if (laToken == EXTENDS) {// extends token
            if (debug)
               System.out.println(
                     "ClassDeclaration --> class Identifier ( extends Identifier )? { ( VarDeclaration )* ( MethodDeclaration )* }");
            verifica(EXTENDS);
            verifica(IDENT);
         }
         verifica('{');
         while (laToken == INT || laToken == BOOLEAN) {// int or boolean token
            if (debug)
               System.out.println(
                     "ClassDeclaration --> class Identifier ( extends Identifier )? { ( VarDeclaration )* ( MethodDeclaration )* }");
            VarDeclaration();
         }
         while (laToken == PUBLIC) {// public token
            if (debug)
               System.out.println(
                     "ClassDeclaration --> class Identifier ( extends Identifier )? { ( VarDeclaration )* ( MethodDeclaration )* }");
            MethodDeclaration();
         }
         verifica('}');
      } else {
         yyerror("ClassDeclaration - Esperado token: class");
      }
   }

   private void VarDeclaration() {
      if (laToken == INT || laToken == BOOLEAN) {// int or boolean token
         if (debug)
            System.out.println("VarDeclaration --> Type Identifier ;");
         Type();
         verifica(IDENT);
         verifica(';');
      } else {
         yyerror("VarDeclaration - Esperado token: int ou boolean");
      }
   }

   private void MethodDeclaration() {
      if (laToken == PUBLIC) {// public token
         if (debug)
            System.out.println(
                  "MethodDeclaration --> public Type Identifier ( ( Type Identifier ( , Type Identifier )* )? ) { ( VarDeclaration )* ( Statement )* return Expression ; }");
         verifica(PUBLIC);
         Type();
         verifica(IDENT);
         verifica('(');
         if (laToken == INT || laToken == BOOLEAN) {// int or boolean token
            if (debug)
               System.out.println(
                     "MethodDeclaration --> public Type Identifier ( ( Type Identifier ( , Type Identifier )* )? ) { ( VarDeclaration )* ( Statement )* return Expression ; }");
            Type();
            verifica(IDENT);
            while (laToken == ',') {// , token
               if (debug)
                  System.out.println(
                        "MethodDeclaration --> public Type Identifier ( ( Type Identifier ( , Type Identifier )* )? ) { ( VarDeclaration )* ( Statement )* return Expression ; }");
               verifica(',');
               Type();
               verifica(IDENT);
            }
         }
         verifica(')');
         verifica('{');
         while (laToken == INT || laToken == BOOLEAN) {// int or boolean token
            if (debug)
               System.out.println(
                     "MethodDeclaration --> public Type Identifier ( ( Type Identifier ( , Type Identifier )* )? ) { ( VarDeclaration )* ( Statement )* return Expression ; }");
            VarDeclaration();
         }
         while (laToken == IDENT || laToken == PRINT || laToken == WHILE || laToken == IF) {// ident, print, while or if
                                                                                            // token
            if (debug)
               System.out.println(
                     "MethodDeclaration --> public Type Identifier ( ( Type Identifier ( , Type Identifier )* )? ) { ( VarDeclaration )* ( Statement )* return Expression ; }");
            Statement();
         }
         
         verifica(CMDO);
         // verifica(RETURN);
         // verifica(NUM);
         // verifica(';');
         verifica('}');
      } else {
         yyerror("MethodDeclaration - Esperado token: public");
      }
   }

   private void Expression() {
      // 
   }

   private void Type() {
      if (laToken == INT) {// int token
         if (debug)
            System.out.println("Type --> int");
         TypeRest();
      } else if (laToken == BOOLEAN) {// boolean token
         if (debug)
            System.out.println("Type --> boolean");
         verifica(BOOLEAN);
      } else if (laToken == IDENT) {// ident token
         if (debug)
            System.out.println("Type --> Identifier");
         verifica(IDENT);
      }

      else {
         yyerror("Type - Esperado token: int, boolean ou ident");
      }
      laToken = this.yylex();
   }

   private void TypeRest() {
      if (laToken == '[') {// [ token
         if (debug)
            System.out.println("TypeRest --> [ ]");
         verifica('[');
         verifica(']');
      } else if (laToken != '[') {// [ token
         if (debug)
            System.out.println("TypeRest --> [ empty ]");
         // verifica(']');
      } else {
         yyerror("TypeRest - Esperado token: [");
      }
   }

   private void verifica(int expected) {
      if (laToken == expected) {
         laToken = this.yylex();

      } else {
         String expStr, laStr;

         expStr = ((expected < BASE_TOKEN_NUM)
               ? "" + (char) expected
               : tokenList[expected - BASE_TOKEN_NUM]);

         laStr = ((laToken < BASE_TOKEN_NUM)
               ? Character.toString((char) laToken)
               : tokenList[laToken - BASE_TOKEN_NUM]);

         yyerror("esperado token: " + expStr +
               " na entrada: " + laStr);
      }
   }

   /* metodo de acesso ao Scanner gerado pelo JFLEX */
   private int yylex() {
      int retVal = -1;
      try {
         yylval = new ParserVal(0); // zera o valor do token
         retVal = lexer.yylex(); // le a entrada do arquivo e retorna um token
      } catch (IOException e) {
         System.err.println("IO Error:" + e);
      }
      return retVal; // retorna o token para o Parser
   }

   /* metodo de manipulacao de erros de sintaxe */
   public void yyerror(String error) {
      System.err.println("Erro: " + error);
      System.err.println("Entrada rejeitada");
      System.out.println("\n\nFalhou!!!");
      System.exit(1);

   }

   public void setDebug(boolean trace) {
      debug = true;
   }

   /**
    * Runs the scanner on input files.
    *
    * This main method is the debugging routine for the scanner.
    * It prints debugging information about each returned token to
    * System.out until the end of file is reached, or an error occured.
    *
    * @param args the command line, contains the filenames to run
    *             the scanner on.
    */
   public static void main(String[] args) {
      AsdrSample parser = null;
      try {
         if (args.length == 0)
            parser = new AsdrSample(new InputStreamReader(System.in));
         else
            parser = new AsdrSample(new java.io.FileReader(args[0]));

         parser.setDebug(false);
         laToken = parser.yylex();

         parser.Program();

         if (laToken == Yylex.YYEOF)
            System.out.println("\n\nSucesso!");
         else
            System.out.println("\n\nFalhou - esperado EOF.");

      } catch (java.io.FileNotFoundException e) {
         System.out.println("File not found : \"" + args[0] + "\"");
      }
      // catch (java.io.IOException e) {
      // System.out.println("IO error scanning file \""+args[0]+"\"");
      // System.out.println(e);
      // }
      // catch (Exception e) {
      // System.out.println("Unexpected exception:");
      // e.printStackTrace();
      // }

   }

}
