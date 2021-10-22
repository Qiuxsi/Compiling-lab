#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define N 100
FILE *fp;
char token[N];
int k;  //token的数组下标

int CompUnit();
int FuncDef();

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
    int flag = 0;

    memset(token, '\0', sizeof(token));
    k = 0;

    if((c = fgetc(fp)) == EOF)  //已读取到文末，返回1跳出循环
        return 1;    
    while(c == ' ' || c == '\t' || c == '\n') { //跳过空格、换行和Tab
        if((c = fgetc(fp)) == EOF)
            return 1;
    }

    if(isNondigit(c)) {
        while(isNondigit(c) || isDigit(c)) {
            token[k++] = c; //拼接字符串
            if((c = fgetc(fp)) == EOF) {
                flag = 1;
                break;
            }
        }
        token[k] = '\0';
        fseek(fp, -1, SEEK_CUR);   //retract
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
    }
    else if(isDigit(c)) {
        while(isDigit(c)) {
            token[k++] = c;
            if((c = fgetc(fp)) == EOF) {
                flag = 1;
                break;
            }
        }
        token[k] = '\0';
        fseek(fp, -1, SEEK_CUR);
        printf("Number(%s)\n", token);
    }
    else if(c == '=') {
        if((c = fgetc(fp)) == EOF)
            flag = 1;
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
    else {
        printf("Err\n");
        exit(0);
    }
    
    if(flag == 1)
        return 1;
    else
        return 0;
}

int main(int argc, char** argv){
    char* path = argv[1];
    if((fp = fopen(path, "r")) == NULL) {
        puts("Fail to open file!");
        exit(0);
    }

    k = 0;
    getsym();
    if(CompUnit()) {
        fclose(fp);
        return 1;
    }
    else {
        fclose(fp);
        return 0;
    }
}

int CompUnit(){
    return FuncDef();
}

int FuncDef(){
    
}



int Ident()