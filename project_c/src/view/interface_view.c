#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "interface.h"

void interpertadorM()
{
    printf(COR_N_AMARELO "sh> " COR_RESET);
}

void loading()
{
    puts(COR_N_VERMELHO PISCAR "Loading..." COR_RESET RESET);
}

void var_atualizada(char *var)
{
    printf(COR_B_VERMELHO "Variavel %s atualizada\n" COR_RESET, var);
}

void sgr_atualizada(char *file1, char *file2, char *file3)
{
    printf(COR_B_VERMELHO "Ficheiros %s %s %s atualizaram o sgr\n" COR_RESET, file1, file2, file3);
}

void file_atualizada(char *file1)
{
    printf(COR_B_VERMELHO "Ficheiro %s foi atualizado\n" COR_RESET, file1);
}