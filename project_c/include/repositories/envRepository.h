/**
 * @file envRepository.h
 * @author Grupo 43
 * @brief Módulo que controla o as variáveis utilizadas no interpretador
 * @details Caracteriza-se como o Environment Repository
 * @version 0.1
 * @date 2021-05-04
 * 
 * @copyright Copyright (c) 2021
 * 
 */

#ifndef ENV_REPOSITORY
#define ENV_REPOSITORY
#include "sgr.h"

/**
 * @brief Repositório de variáveis de ambiente
 * 
 */
typedef struct env_repository *EnvRepository;

/**
 * @brief Inicializa um repositório de variáveis de ambiente 
 * 
 * @return EnvRepository Novo repositório de variáveis vazio
 */
EnvRepository init_env_repo();

/**
 * @brief Retorna a hashtable com a informação das variáveis do interpretador
 * 
 * @param er Environment Repository 
 * @return HashTable* 
 */
HashTable *get_var_table(EnvRepository er);

/**
 * @brief Retorna uma variável de ambiente a partir da variável associado como key
 * 
 * @param er Environment Repository 
 * @param var Variável associada à variável ambiente
 * @return EnvVariables Estrutura que guarda o tipo e valor da variável de ambiente
 */
TABLE get_table_by_var(EnvRepository er, char *var);

/**
 * @brief Adiciona uma variável ao repositório 
 * 
 * @param er Environment Repository 
 * @param t Tabela a ser inserida
 * @param var Variável associada à tabela
 * @return int 2 if overload and resize failed; 1 if exists; 0 if new key;
 */
int add_variable(EnvRepository er, TABLE t, char *var);

/**
 * @brief Define o SGR a utilizar durante a execução
 * 
 * @param ev Variáveis de ambiente
 * @param sgr SGR a ser definido
 */
void set_env_sgr(EnvRepository ev, SGR sgr);

/**
 * @brief Retorna o SGR a ser utilizado durante a execução
 * 
 * @param ev Variáveis de ambiente
 * @return SGR SGR
 */
SGR get_env_sgr(EnvRepository ev);

/**
 * @brief Destrói um repositório de ambiente 
 * 
 * @param er Environment Repository
 */
void free_env_repo(EnvRepository er);

#endif