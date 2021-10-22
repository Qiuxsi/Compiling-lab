#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<math.h>
#define N 100
FILE *inputfp, *outputfp;
char token[N];
int k;  // token的数组下标

void CompUnit();
void FuncDef();
void FuncType();
void Ident();
void Block();
void Stmt();
void Number();
int error();

int isNondigit(char c){ // 包括Letter和Underline
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

int isDelimiter(char c){
    if(c == '(' || c == ')' || c == '{' || c == '}' || c == ';')
        return 1;
    else
        return 0;
}

int getsym(){
    char c;

    memset(token, '\0', sizeof(token));
    k = 0;

    if((c = fgetc(inputfp)) == EOF)  // 已读取到文末
        return 0;

    while(c == ' ' || c == '\t' || c == '\n' || c == '/'){   // 消除注释、空格、换行和Tab

        if(c == '/'){
            if((c = fgetc(inputfp)) == EOF)
                exit(0);

            if(c == '/'){
                while(c != '\n' && c != '\0' && c != '\r')
                    c = fgetc(inputfp);
                c = fgetc(inputfp);
            }
            else if(c == '*'){
                while(1){
                    if((c = fgetc(inputfp)) == EOF)
                        exit(1);
                    if(c == '*'){
                        if((c = fgetc(inputfp)) == '/'){
                            c = fgetc(inputfp);
                            break;
                        }
                    }
                }
            }
            else
                exit(0);
        }
        else{
            if((c = fgetc(inputfp)) == EOF)
                e(0);
        }
    }

    if(isNondigit(c)){
        while(isNondigit(c) || isDigit(c)){
            token[k++] = c; // 拼接字符串
            if((c = fgetc(inputfp)) == EOF)
                break;
        }
        token[k] = '\0';
        fseek(inputfp, -1, SEEK_CUR);   // retract
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
    else if(isDigit(c)){
        while(isDigit(c)) {
            token[k++] = c;
            if((c = fgetc(inputfp)) == EOF)
                break;
        }
        token[k] = '\0';
        fseek(inputfp, -1, SEEK_CUR);
    }
    else if(isDelimiter(c)){
        token[0] = c;
        token[1] = '\0';

    }
    else
        error();
}

int main(int argc, char** argv){
    char *inputpath = argv[1], *outputpath = argv[2];
    if((inputfp = fopen(inputpath, "r")) == NULL){
        puts("Fail to open input file!");
        exit(0);
    }
    if((outputfp = fopen(outputpath, "w")) == NULL){
        puts("Fail to open output file!");
    }

    k = 0;
    getsym();
    CompUnit();
    
    fclose(inputfp);
    fclose(outputfp);
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
        fprintf(outputfp, "(");
        getsym();
        if(!strcmp(token, ")")) {
            fprintf(outputfp, ")");
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
        fprintf(outputfp, "define dso_local i32 ");
        getsym();
    }
    else
        error();
}

void Ident(){
    if(!strcmp(token, "main")){
        fprintf(outputfp, "@main");
        getsym();
    }
    else
        error();
}

void Block(){
    if(!strcmp(token, "{")){
        fprintf(outputfp, "{\n");
        getsym();
        Stmt();
        if(!strcmp(token, "}")){
            fprintf(outputfp, "}");
            getsym();
        }
        else
            error();
    }
    else
        error();
}

void Stmt(){
    if(!strcmp(token, "return")){
        fprintf(outputfp, "    ret i32 ");
        getsym();
        Number();
        if(!strcmp(token, ";"))
            getsym();
        else
            error();
    }
    else
        error();
}

void Number(){  // Number部分先不写成递归下降
    if(!strcmp(token, "0")){    // 十六进制或八进制的'0'
        getsym();
        int n = 0, len = strlen(token);
        if(token[0] == 'x' || token[0] == 'X'){ // 十六进制
            if(len == 1)
                error();
            else{
                int i;
                for(i = 1; i < len; i++){
                    if((token[i] >= '0' && token[i] <= '9'))
                        n += (token[i] - '0') * pow(16, (len - 1 - i));
                    else if((token[i] >= 'a' && token[i] <= 'f'))
                        n += (token[i] - 'a' + 10) * pow(16, (len - 1 - i));
                    else if((token[i] >= 'A' && token[i] <= 'F'))
                        n += (token[i] - 'A' + 10) * pow(16, (len - 1 - i));
                    else
                        error();
                }
                fprintf(outputfp, "%d\n" ,n);
                getsym();
            }
        }
        else if(!strcmp(token, ";")){   // 八进制中'0'的特殊情况
            fprintf(outputfp, "0\n");
            return;
        }
    }
    else if(token[0] == '0'){   // 八进制
        int i, n = 0, len = strlen(token);
        for(i = 1; i < len; i++){
            if(token[i] >= '0' && token[i] <= '7')
                n += (token[i] - '0') * pow(8, (len - 1 - i));
            else 
                error();
        }
        fprintf(outputfp, "%d\n", n);
        getsym();
    }
    else if(token[0] >= '1' && token[1] <= '9'){
        fprintf(outputfp, "%s\n", token);
        getsym();
    }
    else
        error();
}

int error(){
    exit(1);
}