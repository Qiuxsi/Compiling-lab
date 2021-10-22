#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define N 100
FILE *fp;
char token[N];
int k;  //token的数组下标

void CompUnit();
void FuncDef();
void FuncType();
void Ident();
void Block();
void Stmt();
int error();

int isNondigit(char c){ //包括Letter和Underline
    if((c <= 90 && c >= 65) || (c <= 122 && c >= 97) || c == '_')
        return 1;
    else 
        return 0;
}

int isDigit(char c){
    if(c <= '9' && c >= '0')
        return 1;
    else 
        return 0;
}

int getsym(){
    char c;

    memset(token, '\0', sizeof(token));
    k = 0;

    if((c = fgetc(fp)) == EOF)  //已读取到文末
        return 0;
    while(c == ' ' || c == '\t' || c == '\n') { //跳过空格、换行和Tab
        if((c = fgetc(fp)) == EOF)
            return 0;
    }

    if(isNondigit(c)) {
        while(isNondigit(c) || isDigit(c)) {
            token[k++] = c; //拼接字符串
            if((c = fgetc(fp)) == EOF)
                break;
        }
        token[k] = '\0';
        fseek(fp, -1, SEEK_CUR);   //retract
        
        /**
        if(strcmp(token, "if") == 0)
            printf("If\n");
        else if(strcmp(token, "else") == 0)
            printf("Else\n");
        else if(strcmp(token, "while") == 0)
            printf("While\n");
        else if(strcmp(token, "break") == 0)
            printf("Break\n");
        else if(strcmp(token, "continue") == 0)
            printf("Continue\n");
        else if(strcmp(token, "return") == 0)
            printf("Return\n");
        else
            printf("Ident(%s)\n", token);
        */
    }
    else if(isDigit(c)) {
        while(isDigit(c)) {
            token[k++] = c;
            if((c = fgetc(fp)) == EOF)
                break;
        }
        token[k] = '\0';
        fseek(fp, -1, SEEK_CUR);
        // printf("Number(%s)\n", token);
    }
    /*
    else if(c == '=') {
        if((c = fgetc(fp)) == EOF)
            return 0;
        else if(c == '=')
            printf("Eq\n");
        else {
            printf("Assign\n");
            fseek(fp, -1, SEEK_CUR);
        }
    }
    else if(c == ';')
        printf("Semicolon\n");
    else if(c == '(')
        printf("LPar\n");
    else if(c == ')')
        printf("RPar\n");
    else if(c == '{')
        printf("LBrace\n");
    else if(c == '}')
        printf("RBrace\n");
    else if(c == '+')
        printf("Plus\n");
    else if(c == '*')
        printf("Mult\n");
    else if(c == '/')
        printf("Div\n");
    else if(c == '<')
        printf("Lt\n");
    else if(c == '>')
        printf("Gt\n");
    */
    else
        error();
}

int main(int argc, char** argv){
    char* path = argv[1];
    if((fp = fopen(path, "r")) == NULL) {
        puts("Fail to open file!");
        exit(0);
    }

    k = 0;
    getsym();
    CompUnit();
    
    fclose(fp);
    return 0;
}

/* 语法分析 */
void CompUnit(){
    FuncDef();
}

void FuncDef(){
    FuncType();
    Ident();
    if(!strcmp(token, "(")) {
        getsym();
        if(!strcmp(token, ")")) {
            getsym();
            Block();
        }
        else
            error();
    }
    else
        error();
}

void FuncType(){
    if(!strcmp(token, "int")){
        printf("define dso_local i32")
        getsym();
    }
    else
        error();
}

void Ident(){
    if(!strcmp(token, "main"))
        getsym();
    else 
        error();
}

void Block(){
    if(!strcmp(token, "{")) {
        getsym();
        Stmt();
        if(!strcmp(token, "}"))
            getsym();
        else
            error();
    }
    else
        error();
}

void Stmt(){
    if(!strcmp(token, "return")) {
        getsym();
        /* Number */

        if(strcmp(token, ";") != 0) //这里后面可能要重构
            error();
    }
    else
        error();
}

int error(){
    exit(1);
}