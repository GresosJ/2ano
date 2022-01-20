/**
 * @file user.h
 * @author Grupo 43
 * @brief Módulo de dados User
 * @version 0.1
 * @date 2021-04-18
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef USER_H
#define USER_H

#include "hash_table.h"
#include "glib_warningavoid.h"

#define USERS_FILE "users.csv"

#define DELIM ";"
#define SEC_DELIM ","

/**
 * @brief Apontado para uma estrutura User
 * 
 */
typedef struct user* User;

/**
 * @brief Retorna um apontador para User, sem informação
 * 
 * @return User sem informção associada
 */ 
User init_user_empty();

/**
 * @brief Iniciar um user a partir de um buffer com as informações do User
 * O User é iniciado com 0 reviews
 * 
 * @param user_id String com o parâmetro delimitado por "\n" - o ID do user
 * @return Pointer para um novo User 
 */
User init_user(char *user_id);

/**
 * @brief Retorna o ID de um User
 * 
 * @param user Apontador para um User
 * @return Uma string com o ID  
 */ 
char *get_user_ID(User user);

/**
 * @brief Retorna o nome do User
 * 
 * @param user Apontador para um User
 * @return String com o nome do User
 */
char *get_user_name(User user);

/**
 * @brief Retorna os IDs dos reviews do User
 * 
 * @param user Apontador para um User
 * @return String com os IDs das reviews do User
 */
GArray *get_user_reviews(User user);

/**
 * @brief Retorna o parâmetro friends de um User
 * 
 * @param user Apontador para um User
 * @return String com os IDs dos Users
 */ 
GArray *get_user_friends(User user);

/**
 * @brief Altera o ID de um User
 * 
 * @param user Apontador para um User
 * @param user_id String com o ID do User
 * @return 1 se tiver sucesso; 0 caso contrário;
 */ 
int set_user_ID(User user, char *user_id);

/**
 * @brief Altera o nome do User
 * 
 * @param user Apontador para um User
 * @param name String om o nome do User
 * @return 1 se tiver sucesso; 0 caso contrário;
 */
int set_user_name(User user, char *name);

/**
 * @brief Determmina se o User tem reviews
 * 
 * @param user Apontador para um User
 * @return 1 se tiver reviews; outro número caso contrário;
 */
int has_user_reviews(User user);

/**
 * @brief Determina se um User tem Friends
 * 
 * @param user Apontador para o User
 * @return 1 se tiver Friends; outro número caso contrário;
 */
int has_user_friends(User user);

/**
 * @brief Adiciona um User aos reviews de um User
 * 
 * @param user Apontador para um User
 * @param review_id String com a review para adicionar 
 * @return 1 se foi adicionado; 0 se falhou e -1 se já for amigo;
 */
int add_user_review(User user, char *review_id);

/**
 * @brief Adiciona um User aos amigos de um User
 * 
 * @param user Apontador para um User
 * @param friend ID do amigo a ser adicionado ao parâmetro
 * @return 1 se foi adicionado; 0 se falhou a adição e -1 se ja for amigo;
 */ 
int add_user_friend(User user, char *friend_id);

/**
 * @brief Altera a string com os IDs do User
 * 
 * @param user Apontador para um User 
 * @param reviews String com os review IDs
 * @return 1 se tiver sucesso; 0 caso contrário;
 */
int set_user_reviews(User user, char *reviews);

/**
 * @brief Altera o parâmetro friends de um User
 * 
 * @param user Apontador para um User
 * @param friends String com os IDs dos Users
 * @return 1 se tiver sucesso; 0 caso contrário;
 */
int set_user_friends(User user, char *friends);

/**
 * @brief Verifica se é um review do User
 * 
 * @param user Apontador para um User
 * @param review_id String com o ID review 
 * @return 1 se tiver sucesso; 0 caso contrário;
 */
int user_has_review(User user, char *review_id);

/**
 * @brief Verifica se é um amigo do User
 * 
 * @param user Apontador para um User
 * @param friends String com o ID do User
 * @return 1 se é amigo; 0 caso contrário;
 */
int user_has_friend(User user, char *friend_id); 

/**
 * @brief Determina o número de amigos que o User tem
 * 
 * @param user Apontador para um User
 * @return int com o número de amigos
 */ 
int friend_number(User user);

/**
 * @brief Liberta o espaço ocupado por um User e os seus componentes
 * 
 * @param user Apontador para um User
 */
void free_user(User user);

/**
 * @brief Verifica se dois Users são iguais
 * 
 * @param user1 Apontador para o User que vai ser clonado
 * @param user2 Apontador para o User que vai ser clonado
 * @return 1 se forem iguais; 0 caso contrário;
 */ 
int equals_user(User user1, User user2);

#endif