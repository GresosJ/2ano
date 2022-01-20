/**
 * @file reviewRepository.h
 * @author Grupo 43
 * @brief Repositório que permite obter informação relativo às reviews
 * @version 0.1
 * @date 2021-04-29
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef REVIEW_REPOSITORY
#define REVIEW_REPOSITORY
#include "review.h"
#include "userRepository.h"
#include "businessRepository.h"

/**
 * @brief Repositório de review
 */
typedef struct review_repository *ReviewRepository;

/**
 * @brief  Inicializa a struct e faz load da informação do ficheiro
 * 
 * @param file_path Caminho para o ficheiro a ser lido
 * @param userRepository Apontador para o repositório de utilizadores
 * @return UserRepository Repositório com a informação do ficheiro (caso este exista)
 */
ReviewRepository init_rev_repo(char *file_path, UserRepository userRepository, BusinessRepository businessRepository);

/**
 * @brief Procura no repositório por uma review a partir do seu id
 * 
 * @param rv Apontador para a HashTable com as reviews
 * @param id String com o ID que queremos encontrar 
 * @return Review com o id argumento
 */
Review get_review_by_id(ReviewRepository rv, char *id);

/**
 * @brief Retorna a hastable das Reviews
 * 
 * @param rv Apontador para a HashTable com as Reviews
 * @return Hastable com todos as Reviews
 */
HashTable *get_review_table(ReviewRepository rv);

/**
 * @brief Calcula uma lista de reviews de um determinado business
 * 
 * @param rr Review repository
 * @param bus_id Business id a pesquisar
 * @return Review* Lista de reviews
 */
Review *get_reviews_by_bus_id(ReviewRepository rr, char *bus_id);

/**
 * @brief Calcula as stars de um business a partir média das reviews
 * 
 * @param rr Review Repository
 * @param bus_id Business ID a ser calculado
 * @return double Stars do business
 */
float get_stars_bus_id(ReviewRepository rr, char *bus_id);

/**
 * @brief Retorna um GArray com todas as reviews que tenham aquela palavra 
 * 
 * @param rv Review Repository
 * @param word String com a palavra que vamos procurar
 * 
 * @return GArray com Reviews
 */
GArray *get_revs_w_word(ReviewRepository rv, char *word);

/**
 * @brief Destrói um repositório de Review
 * 
 * @param rr Review Repository a destruir  
 */
void free_review_repo(ReviewRepository);

#endif