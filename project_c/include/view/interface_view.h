#ifndef INTERFACE_VIEW_H
#define INTERFACE_VIEW_H

#include "interface.h"
#include "cores.h"

/**
 * @brief Faz print no ecra do sh>
 */
void interpertadorM();

/**
 * @brief Faz print no ecra do loading
 */
void loading();

/**
 * @brief Faz print no ecra de qual variavel foi atualizada
 * 
 * @param var que foi atualizada
 */
void var_atualizada(char *var);

/**
 * @brief Faz print no ecra de que o sgr foi atualizado
 * 
 * @param file1 que foi atualizada
 * @param file2 que foi atualizada
 * @param file3 que foi atualizada
 */
void sgr_atualizada(char *file1, char *file2, char *file3);

/**
 * @brief Faz print no ecra de que ficheiro foi atualizada
 * 
 * @param var file foi atualizada
 */
void file_atualizada(char *file1);

#endif