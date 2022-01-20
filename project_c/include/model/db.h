/**
 * @file db.h
 * @author Grupo 43
 * @brief Módulo de dados db
 * @version 0.1
 * @date 2021-04-29
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef DB_H
#define DB_H
#include "hash_table.h"
#include "userRepository.h"
#include "businessRepository.h"


/**
 * Read the file and create a hashtable from it
 * @param fileName Name of the file to be parsed
 * @param br Repositório de business
 * @return HashTable with all business
 */
HashTable *loadBusiness(char *fileName, BusinessRepository br);

/**
 * @brief Cria uma hastable a partir do ficheiro dos Users 
 * @param fileName Nome do ficheiro
 * @return Hashtable com todos os Users
 */
HashTable *loadUsers(char *filename);

/**
 * @brief Cria um catálogo de reviews a partir do ficheiro
 *        Adiciona ao user repository as reviews dos users
 * 
 * @param filename Path para o ficheiro
 * @return HashTable* Catálogo sobre a forma de Hashtable
 */
HashTable *loadReviews(char *filename, UserRepository userRepository, BusinessRepository businessRepository);

#endif