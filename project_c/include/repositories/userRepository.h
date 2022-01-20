/**
 * @file userRepository.h
 * @author Grupo 43
 * @brief Repositório que permite obter informação relativo aos users
 * @version 0.1
 * @date 2021-04-29
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef USER_REPOSITORY
#define USER_REPOSITORY
#include "user.h"

/**
 * @brief Repositório de User
 */ 
typedef struct user_repository *UserRepository;

/**
 * @brief Inicializa a struct e faz load da informação do ficheiro
 * 
 * @param file_path Caminho para o ficheiro a ser lido
 * @return UserRepository Repositório com a informação do ficheiro (caso este exista)
 */ 
UserRepository init_user_repo(char *file_path);

/**
 * @brief Procura no repositório por um user a partir do seu id
 * 
 * @param usr Apontador para a HashTable com os Users
 * @param id String com o ID que queremos encontrar 
 * @return User com o id argumento
 */ 
User get_user_by_id(UserRepository usr, char *id);

/**
 * @brief Retorna a hastable dos Users
 * 
 * @param usr Apontador para a HashTable com os Users
 * @return Hastable com todos os Users 
 */ 
HashTable *get_user_table(UserRepository usr);

/**
 * @brief Adiciona o review ID a um user a partir do repositório
 * 
 * @param usr Apontador para a HashTable com os Users
 * @param user_id String com o ID do User
 * @param review_id String com o ID da review do User
 */
void add_review_to_user(UserRepository usr, char *user_id, char *review_id);

/**
 * @brief Destrói um repositório de User
 * 
 * @param ur User Respository a destruir
 */
void free_user_repo(UserRepository ur);

#endif
