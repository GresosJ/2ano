/**
 * @file hash_table.h
 * @author Grupo 43
 * @brief Módulo de dados da HashTable
 * @version 0.1
 * @date 2021-04-29
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef HASH_TABLE
#define HASH_TABLE

#include "glib_warningavoid.h"

#define HASH_TABLE_INITSIZE 1024

typedef struct hashtable HashTable;

/**
 * @brief Init HashTable por SIZE
 * 
 * @param size size to be allocated
 * @returns 0 if success; 1 if couldnt malloc;
 */
int _initHashTable(HashTable **, int size);

/**
 * @brief Faz o clone de uma HashTable 
 * 
 * @param ht_source Hashtable a ser clonada
 * @param ht_target Hashtable final
 * @param __cmp__ function to compare keys; must return 0 if compare is true
 * @param __hash__ hashing function
 * @return int 
 */
int _clone(HashTable **ht_source, HashTable **ht_target, int(__cmp__)(void *, void *), int(__hash__)(void *, int));

/**
 * @brief Procura um pair pela sua chave
 * 
 * @param key Key to be searched
 * @param value pointer to return the value type
 * @param __cmp__ function to compare keys; must return 0 if compare is true
 * @param __hash__ hashing function
 * @returns 0 if found, 2 if overload;
 */
int _lookup(HashTable *, void *key, void **value, int(__cmp__)(void *, void *), int(__hash__)(void *, int));

/**
 * @brief Procura na HashTable para encontrar uma needle
 * 
 * @param ht Hashtable
 * @param __get_value__ Function to get value from bucket->value
 * @param cmp_value Value to search
 * @param __cmp__ function to compare keys; must return > 0 if compare is true
 * @return int 1 if found; 0 otherwise;
 */
void *_findValue(HashTable *ht, void *(__get_value__)(void *), void *cmp_value, int(__cmp__)(void *, void *));

/**
 * @brief Encontra todos os valores onde a comparação retorna TRUE
 * 
 * @param ht Hashtable
 * @param __get_value__ Function to get value from bucket->value
 * @param cmp_value Value to search
 * @param __cmp__ function to compare keys; must return > 0 if compare is true
 * @return GArray * com os elementos filtrados
 */
GArray *_filter(HashTable *ht, void *(__get_value__)(void *), void *cmp_value, int(__cmp__)(void *, void *));

/**
 * @brief Converte a informação contida numa HashTable numa GTree
 * 
 * @param ht Hashtable
 * @param __get_key__ Function to get value from bucket->value
 * @param __get_value__ Function to get value from bucket->value
 * @param __cmp_key__ Key to Tree
 * @return GTree* com os elementos inseridos e ordenados
 */
GTree *_hash_to_tree(HashTable *ht, void *(__get_key__)(void *), void *(__get_value__)(void *), int(__cmp_key__)(const void *, const void *));

/**
 * @brief Update the hashTable using __hash__ to get the start index and __cmp__ to cmp keys
 * Pointers passed as arguments will be stored. Should be cloned/ malloc before!
 * 
 * @param key *key pointer
 * @param value *value pointer (void *)
 * @param __cmp__ function to compare keys; must return 0 if compare is true
 * @param __hash__ hashing function
 * 
 * @returns 2 if overload and resize failed; 1 if exists; 0 if new key;
 */
int _update(HashTable **, void *key, void *value, int(__cmp__)(void *, void *), int(__hash__)(void *, int));

/**
 * @brief Retorna O tamanho da hashtable 
 * 
 * @return int com a length da hashtable
 */
int _length(HashTable *);

/**
 * @brief Remove os pointers para a key e o value (free!)
 * 
 * @param key key to be searched
 * @param __cmp__ function to compare keys
 * @param __hash__ hashing function
 * 
 * @returns 2 if key not exists; 0 if exists and deleted; 1 otherwise;
 */
int _remove(HashTable **, void *key, int(__cmp__)(void *, void *), int(__hash__)(void *, int), void(__free_key__)(void *), void(__free_value__)(void *));

/* ----- Funções de apoio ----- */
/**
 * Função de comparação a ser utilizada na tabela de hash
 *  i) Faz-se cast de _char1 e _char2 para char*
 *  ii) Utiliza-se a função de strcmp entre estas
 *  iii) devolve o resultado
 * 
 * @param _char1 String 1 a comparar
 * @param _char2 String 2 a comparar
 * 
 * @return Resultado de strcmp(_char1, _char2)
 */
int key_cmp_str(void *_char1, void *_char2);

/**
 * Efetua o hashing da chave de acesso à tabela
 * 
 * @param _key Chave a ser gerado hash
 * @param size Valor máximo do hash gerado
 * 
 * @return [0..size-1]
 */
int hash_key_str(void *_key, int size);

/**
 * @brief Faz free de toda a tabela, incluíndo o espaço alocado para os elementos e para as chaves
 * 
 * @param ht Tabela a ser destruída
 * @param __free_key__ Função que faz free da chave
 * @param __free_value__ Função que faz free do valor
 */
void _free_hashtable(HashTable **ht, void(__free_key__)(void *), void *(__free_value__)(void *));

// ! TO REMOVE
/**
 * @brief Print da hashtable linha a linha
 * Na primeira linha é imprimido o tamanho atual da tabela
 * 
 * @param __show_key__ Function to print the key
 * @param __show_value__ Function to print the value type
 */
void _showHashTable(HashTable *, void(__show_value)(void *));
#endif