/* scanner for SS language */

%{
/* need this for the call to atof() below */
#include <math.h>
%}

DIGIT    [0-9]
ID       [a-zA-Z][a-z0-9A-Z_]*
OPERATOR     [+\-*\/%!<>=.]|==|[+][+]|--|>=|<=|!=
PUNCTUATION  [\(\)\[\]\{\}\;]
KEYWORD  if|else|for|while|int|double|void|string|class|boolean|function|return|and|or

%%

{DIGIT}+    {
            printf( "An integer: %s (%d)\n", yytext, atoi( yytext ) );
            }

{DIGIT}+"."{DIGIT}*        {
            printf( "A double: %s (%g)\n", yytext, atof( yytext ) );
            }

True|False  printf( "A boolean: %s\n", yytext );     

"([^"]|\\")*"  printf( "A string: %s\n", yytext );    

{KEYWORD}   printf( "A keyword: %s\n", yytext );

{ID}        printf( "An identifier: %s\n", yytext );

{OPERATOR}   printf( "An operator: %s\n", yytext );

{PUNCTUATION}   printf( "A punctuation: %s\n", yytext );

"%"[^}\n]*"%"     /* eat up one-line comments */

[ \t\n]+          /* eat up whitespace */ 

.           printf( "Unrecognized character: %s\n", yytext );

%%

int main( argc, argv )
int argc;
char **argv;
    {
    ++argv, --argc;  /* skip over program name */
    if ( argc > 0 )
            yyin = fopen( argv[0], "r" );
    else
            yyin = stdin;

    yylex();
    }
