/**
 * @file table_view.h
 * @author Marco
 * @brief 
 * @version 0.1
 * @date 2021-04-23
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef TABLE_VIEW_H
#define TABLE_VIEW_H

#include "table.h"

/**
 * @brief Imprime uma linha da tabela
 * 
 * @param row Linha
 */
void print_row(ROW row);

/**
 * @brief Imprime a tabela com offset e limite
 * 
 * @param t Tabela
 * @param offset A partir de que linha será apresentado resultados
 * @param limit Número máximo de resultados a apresentar ou -1 para apresentar os valores todos
 */
void print_table(TABLE t, unsigned int offset, int limit);

/**
 * @brief Efetua a paginação de uma tabela
 * 
 * @param t Tabela
 */
void pagination(TABLE t);

/**
 * @brief Imprime uma tabela para um ficheiro, utilizando o delimitador indicado
 * 
 * @param t Tabela a imprimir
 * @param delim Delimitador
 * @param file_path Caminho para o ficheiro
 * @return int 1 se sucesso; 0 caso contrário
 */
int print_table_to_csv(TABLE t, char *delim, char *file_path);

#endif