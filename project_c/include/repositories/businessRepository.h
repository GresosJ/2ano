/**
 * @file businessRepository.h
 * @author Grupo 43
 * @brief Repositório que permite obter informação relativo aos business
 * @version 0.1
 * @date 2021-04-27
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef BUSINESS_REPOSITORY_H
#define BUSINESS_REPOSITORY_H

#include "business.h"
#include "glib_warningavoid.h"

/**
 * @brief Repositório de business
 * 
 */
typedef struct business_repository *BusinessRepository;

/**
 * @brief Inicializa a struct e faz load da informação do ficheiro
 * 
 * @param file_path Caminho para o ficheiro a ser lido
 * @return BusinessRepository Repositório com a informação do ficheiro (caso este exista)
 */
BusinessRepository init_bus_repo(char *file_path);

/**
 * @brief Get the business by id object
 * 
 * @param sgr Sistema de gestão de reviews
 * @param id ID do business a procurar
 * @return Business que encontrou / null se não existe
 */
Business get_business_by_id(BusinessRepository br, char *id);

/**
 * @brief Get the business start by letter
 * 
 * @param sgr Sistema de gestão de reviews
 * @param letter Letra pela qual o business começa
 * @return Business* Array de business encontrado
 */
Business *get_business_start_by_letter(BusinessRepository br, char letter);

/**
 * @brief Vai buscar todos os business de uma cidade
 *        A pesquisa da cidade não é case sensitive!
 * 
 * @param br Business Repository
 * @param city Cidade a pesquisar
 * @return GArray* Lista de business
 */
GArray *get_business_by_city(BusinessRepository br, char *city);

/**
 * @brief Adiciona review id à business
 * 
 * @param rr BR
 * @param bus_id Business id a ser atualizado
 * @param review_id Review ID a adicionar
 * @param stars Estrelas do review
 */
void add_review_to_business(BusinessRepository rr, char *bus_id, char *review_id, float stars);

/**
 * @brief Retorna os Businesses associados a um categoria
 * 
 * @param br Business Repsitory
 * @param category Categoria a ser procurda
 * @return GArray* com todos os businesses
 */
GArray *get_business_by_category(BusinessRepository br, char *category);

/**
 * @brief Retorna uma árvore balanceada com todas as cidades
 * 
 * @param br Business Repository
 * @return GTree* Árvore de cidades
 */
GTree *get_all_cities(BusinessRepository br);

/**
 * @brief Insere uma business na árvore em que a chave é a cidade
 * 
 * @param br 
 * @param b Business a ser adicionada
 */
void insert_bus_by_city(BusinessRepository br, Business b);

/**
 * @brief Calcula os TOP business de uma cidade os ordenar, utilizando
 *        a função sort_func recebida como argumento
 * 
 * @param br BR
 * @param top Número máximo de business de cada cidade que vai retornar
 * @param sort_func Função para ordenar os business
 * @return GArray* Lista de listas de Business. Estão agrupados por cidade.
 */
GArray *get_top_bus_by_city(BusinessRepository br, int top, GCompareFunc sort_func);

/**
 * @brief Destrói um repositório de business
 * 
 * @param br Business Repository a destruir
 */
void free_business_repo(BusinessRepository br);
#endif