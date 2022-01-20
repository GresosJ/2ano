/**
 * @file business.h
 * @author Grupo 43
 * @brief Módulo de dados Business
 * @version 0.1
 * @date 2021-04-18
 * 
 * @copyright Copyright (c) 2021
 * 
 */

#ifndef BUSINESS_H
#define BUSINESS_H

#include "hash_table.h"
#include "glib_warningavoid.h"

#define BUSINESS_FILE "db/business.csv"

#define BUSINESS_N_FIELDS 5
#define DELIM ";"
#define DEC_DELIM ","

/**
 * @brief Apontador para a estrutura Business
 * 
 */
typedef struct business *Business;

/**
 * @brief Identificador do business
 * 
 * @param b Apontador para business a que se vai buscar a info
 * @return Cópia do identificador do business
 */
char *get_business_id(Business b);

/**
 * @brief Nome do business
 * 
 * @param b Apontador para business a que se vai buscar a info
 * @return Cópia do nome do business
 */
char *get_business_name(Business b);

/**
 * @brief Cidade do Business
 * 
 * @param b Apontador para business a que se vai buscar a info
 * @return Cópia da cidade do Business
 */
char *get_business_city(Business b);

/**
 * @brief Número de estrelas do business
 * 
 * @param b Apontador para business a que se vai buscar a info
 * @return Classificação em estrelas do Business [0..5]
 */
char *get_business_state(Business b);

/**
 * @brief Número de comentários do Business
 * 
 * @param b Apontador para business a que se vai buscar a info
 * @return Número de comentários do Business
 */
GArray *get_business_categories(Business b);

/**
 * @brief Retorna a lista de ids associados a um Business
 * 
 * @param b Business
 * @return GArray* Lista de review ids
 */
GArray *get_business_review_ids(Business b);

/**
 * @brief Calcula as stars do Business
 * 
 * @param b Business
 * @return float Stars
 */
float get_business_stars(Business b);

/**
 * @brief Atualiza: Nome do Business
 * 
 * @param b Apontador para Business a que se vai buscar a info
 * @param name Novo nome
 * @return 1 se sucesso; 0 se erro;
 */
int set_business_name(Business b, char *name);

/**
 * @brief Atualiza: Cidade do Business
 * 
 * @param b Apontador para Business a que se vai buscar a info
 * @param city Nova cidade
 * @return 1 se sucesso; 0 se erro;
 */
int set_business_city(Business b, char *city);

/**
 * @brief Atualiza: Número de estrelas do Business
 * 
 * @param b Apontador para Business a que se vai buscar a info
 * @param stars Nova classificação
 * @return 1 se sucesso; 0 se erro;
 */
int set_business_state(Business b, char *state);

/**
 * @brief Atualiza: Número de comentários do business
 * 
 * @param b Apontador para Business a que se vai buscar a info
 * @param counter quantidade a adicionar / remover;
 * @return 1 se sucesso; 0 se erro;
 */
int set_business_categories(Business b, char *categories);

/**
 * @brief Adiciona Stars a um Business
 * 
 * @param b Apontador para Business que vai ser alterado
 * @param stars float com as Stars a serem adicionadas
 */
void add_business_stars(Business b, float stars);

/**
 * @brief Adiciona uma categoria à Business (caso não exista)
 * 
 * @param b Apontador para Business a que se vai buscar a info
 * @param category Categoria a ser adicionada
 * @return 1 se sucesso; 0 se erro; -1 caso já existe;
 */

int add_business_category(Business b, char *category);
/**
 * @brief Adiciona uma Review 
 * 
 * @param b Apontador para o Business a ser atualizado
 * @param review_id Review que vai ser adicionado
 * @param stars Stars a serem adicionadas
 * @return 1 se sucesso; 0 se erro ou já existe;
 */
int add_business_review_id(Business b, char *review_id, float stars);

/**
 * @brief Pesquisa uma categoria num array
 * 
 * @param categories Lista de categorias
 * @param category Categoria
 * @return int TRUE (1) ou FALSE (0)
 */
gboolean find_category(GArray *categories, char *category);

/**
 * @brief Vai percorrer as categorias do Business e
 * verificar se a categoria existe
 * 
 * @param b Apontador para Business a verificar
 * @param category Categoria a verificar se existe
 * @return 1 se existir, 0 caso contrário
 */
gboolean has_category(Business b, char *category);

/**
 * @brief Verifica se um Business tem uma Review
 * 
 * @param b Apontador para Business a verificar
 * @param review_id Review que via ser procurada
 * @return 1 se existir, 0 caso contrário
 */
gboolean business_has_review_id(Business b, char *review_id);

/**
 * @brief Verifica se dois Business são iguais
 *        Compara id, nome, idade, estado e categorias entre eles
 * 
 * @param b1 Business a comparar
 * @param b2 Business a comparar
 * @return int 1 se true; 0 se false;
 */
gboolean equals_business(Business b1, Business b2);

/**
 * @brief Inicializa uma struct Business vazia.
 * 
 * @return Business 
 */
Business init_business_empty();

/**
 * @brief Inicializa um Business utilizando o buffer delimitado por ';'
 * 
 * @param buffer String de valores delimitados por ';'. Necessário 5 parâmetros.
 * @return Apontador para um new Business com a info do buffer
 */
Business initBusiness(char *buffer);

/**
 * @brief Destrói o business, libertando espaço na Mem
 * 
 * @param b Business a destruir
 */
void free_business(Business b);

void showBusiness(void *business);

#endif
