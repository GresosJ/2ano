/**
 * @file sgr.h
 * @author Grupo 43
 * @brief Módulo que permite a gestão dos dados:
 *        - Users
 *        - Businesses
 *        - Reviews
 * @version 0.1
 * @date 2021-04-29
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef SGR_H
#define SGR_H

#include "user.h"
#include "review.h"
#include "business.h"
#include "hash_table.h"
#include "table.h"
#include "businessRepository.h"
#include "userRepository.h"
#include "reviewRepository.h"
#include "db.h"
#include "user.h"

typedef struct sgr* SGR;
typedef struct city City;

/**
 * @brief Inicia o Sistema de Gestão de Reviews 
 * @details Os repositórios são todos iniciados a Null
 * 
 * @return SGR Novo SGR iniciado a NULL
 */
SGR init_sgr();

/**
 * @brief Get the business repository object
 * 
 * @param sgr Sistema de Gestão de Reviews
 * @return BusinessRepository Repositório de Business
 */
BusinessRepository get_business_repository(SGR sgr);

/**
 * @brief Set the business repository object
 * 
 * @param sgr Sistema de Gestão de Reviews
 * @param br Repositório de Business que vai atualizar
 */
void set_business_repository(SGR sgr, BusinessRepository br);

/**
 * @brief Retorna o repositório do User
 * 
 * @param sgr Sistema de Gestão de Reviews 
 * @return UserRepository Repositório de Users
 */ 
UserRepository get_user_repository(SGR sgr);

/**
 * @brief Altera o repositório do User
 * 
 * @param sgr Sistema de Gestão de Reviews
 * @param usr Repositório de User que vai atualizar
 */
void set_user_repository(SGR sgr, UserRepository usr);

/**
 * @brief Retorna o repositório do Review
 * 
 * @param sgr Sistema de Gestão de Reviews 
 * @return ReviewRepository Repositório de Review
 */ 
ReviewRepository get_review_repository(SGR sgr);

/**
 * @brief Altera o repositório do Review
 * 
 * @param sgr Sistema de Gestão de Reviews
 * @param usr Repositório de Review que vai atualizar
 */
void set_review_repository(SGR sgr, ReviewRepository usr);

/**
 * @brief Query 1
 * @details Carrega o SGR com os dados dos ficheiros
 * 
 * @param busPath Caminho para ficheiro CSV de business
 * @param userPath Caminho para ficheiro CSV de users
 * @param revPath Caminho para ficheiro CSV de reviews
 * @return SGR Sistema de Gestão de Reviews
 */
SGR loadSGR(char *busPath, char *userPath, char *revPath);

/**
 * @brief Query 2
 * @details Determina os IDs e os nomes dos Businesses cujos nomes começam por uma letra
 * @details A procura não é case sensitive
 * 
 * @param sgr Sistema de Gestão de Recursos
 * @param letter Letra a ser procurada
 * @return TABLE com os IDs e Nomes dos Businesses
 */
TABLE business_started_by_letter(SGR sgr, char letter);

/**
 * @brief Query 3
 * @details Determina toda a informação de um Business a partir do seu ID
 * 
 * @param sgr Sistema de Gestão de Recursos
 * @param business_id ID do Business a demonstrar
 * @return TABLE com a informação do Business a partir do ID
 */
TABLE business_info(SGR sgr, char *business_id);

/**
 * @brief Query 4
 * @details Determina os negócios que um User fez Review, a partir do seu ID
 * 
 * @param sgr Sistema de Gestão de Recursos
 * @param user_id ID do user a ser procurado
 * @return TABLE com a informação associada a cada negócio que o User fez Review
 */
TABLE businesses_reviewed(SGR sgr, char *user_id);

/**
 * @brief Query 5
 * @details Determina os negócios com n ou mais stars de uma dada cidade
 * 
 * @param sgr Sistema de Gestão de Recursos
 * @param stars Número mínimo de estrelas
 * @param city Cidade a ser procurada
 * @return TABLE com a informação de cada Business da cidade com n ou mais estrelas
 */
TABLE business_with_stars_and_city(SGR sgr, float stars, char *city);

/**
 * @brief Query 6
 * @details Determina os top n negócios de cada cidade
 * @details São os top negócios de acordo com o número médio de estrelas dessa cidade
 * 
 * @param sgr Sistema de Gestão de Recursos
 * @param top Número de negócios a serem procurados por cidade
 * @return TABLE com a informação de cada Business do top da sua cidade
 */
TABLE top_businesses_by_city(SGR sgr, int top);

/**
 * @brief Query 7
 * @details Determina os Users que tenham visitado mais de um estado
 * @details Verifica se o User fez Reviews em mais do que um estado
 * 
 * @param sgr Sistema de Gestão de Recursos
 * @return TABLE com todos os Users internacionais 
 */
TABLE international_users(SGR sgr);

/**
 * @brief Query 8
 * @details Determina os top n Business que pertencem a uma dada categoria
 * @details São os top BUsiness de acordo com o número médio de estrelas nessa categoria
 * 
 * @param sgr Sistema de Gestão de Recursos
 * @param top Número de Business a serem procurados por categoria
 * @param category Categoria em questão 
 * @return TABLE com as informações de cada Business com a categoria
 */
TABLE top_business_with_category(SGR sgr, int top, char *category);

/**
 * @brief Query 9
 * @details Determina as top n Reviews cujo campo Text inclui a palavra dada
 * @details São os top Review de acordo com o número médio de estrelas 
 * 
 * @param sgr Sistema de Gestão de Recursos
 * @param top Número de Reviews a serem procuradas
 * @param word Palavra a ser procurada
 * @return TABLE 
 */
TABLE reviews_with_word(SGR sgr, int top, char *word);

/**
 * @brief Query extra
 * @details Destrói um Sistema de Gestão de Reviews, libertando Mem
 * 
 * @param sgr SGR a destruir
 */
void destroy_sgr(SGR sgr);

#endif