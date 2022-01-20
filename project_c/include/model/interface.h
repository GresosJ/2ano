/**
 * @file interface.h
 * @author Grupo 43
 * @brief 
 * @version 0.1
 * @date 2021-05-05
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef INTERFACE_H
#define INTERFACE_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "cores.h"
#include "sgr.h"
#include "table_view.h"
#include "envRepository.h"

/**
 * @brief Compara e exeecucada uma comando
 * 
 * @param ev Repositorio onde ir buscar informacao
 * @param command Comando a ser executado
 * @return TABLE Retorna uma variavel
*/
TABLE exec_command(EnvRepository ev, char *command);

/**
 * @brief Verifica se este comando vai ser preciso altera o Repository
 * 
 * @param er Repositorio onde vai guarda informacao
 * @param commands comandos a ser executados
 */
void run_commands(EnvRepository er, char **commands);


/**
 * @brief Interpertador de comandos
 */
void run_bash();

int get_command_index(char *func_name);

int verify_input(char **input);




#endif
