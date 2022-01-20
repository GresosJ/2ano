/**
 * @file review.h
 * @author Grupo 43
 * @brief Módulo de dados Review
 * @version 0.1
 * @date 2021-04-24
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef REVIEWS_H
#define REVIEWS_H
#include "hash_table.h"

#define REVIEWS_FILE "reviews.csv"

#define REVIEW_N_FIELDS 7
#define DELIM ";"

/**
 * @brief Apontador para um estrutura Review
 * 
 */
typedef struct reviews *Review;

/**
 * @brief Retorna o id do review
 * 
 * @param r Review
 * @return char* Apontador para id
 */
char *get_review_id(Review r);

/**
 * @brief Retorna o ID do user que fez a review
 * 
 * @param r Review
 * @return char* Apontador para user_id que fez a review
 */
char *get_review_user_id(Review r);

/**
 * @brief Retorna o ID do business que foi feita review
 * 
 * @param r Review
 * @return char* Business id
 */
char *get_review_bus_id(Review r);

/**
 * @brief Retorna quantos stars foi atribuído pela review
 * 
 * @param r Review
 * @return double Stars
 */
float get_review_stars(Review r);

/**
 * @brief Retorna quão útil é o business associado à review
 * 
 * @param r Review
 * @return int Utilidade do business associado à review
 */
int get_review_useful(Review r);

/**
 * @brief Retorna o quão engraçado é o business associado à review
 * 
 * @param r Review
 * @return int Quão engraçado é o business
 */
int get_review_funny(Review r);

/**
 * @brief Retorna quão fixe é o business associado à review
 * 
 * @param r Review
 * @return int 
 */
int get_review_cool(Review r);

/**
 * @brief Retorna a data da review
 * 
 * @param r Review
 * @return char* Data com estrutura (YYYY-MM-DD HH:MM:SS)
 */
char *get_review_date(Review r);

/**
 * @brief Retorna o text do review
 * 
 * @param r Apontador para uma Revie w
 * @return char* Text do Review
 */
char *get_review_text(Review r);

/**
 * @brief Define o ID do review
 * 
 * @param r Review
 * @param review_id ID
 * @return int 1 se sucess; 0 caso contrário
 */
int set_review_id(Review r, char *review_id);

/**
 * @brief Define o User ID que fez o review
 * 
 * @param r Review
 * @param user_id User ID
 * @return int 
 */
int set_review_user_id(Review r, char *user_id);

/*Seta ID do Business*/
/**
 * @brief 
 * 
 * @param r 
 * @param business_id 
 * @return int 
 */
int set_review_bus_id(Review r, char *business_id);

/*Seta quantos Stars tem o bussiness*/
/**
 * @brief 
 * 
 * @param r 
 * @param stars 
 */
void set_review_stars(Review r, double stars);

/*Seta quantos votos Usefel tem o business*/
/**
 * @brief 
 * 
 * @param r 
 * @param useful 
 */
void set_review_useful(Review r, int useful);

/*Seta quantos votos Funny tem o business*/
/**
 * @brief 
 * 
 * @param r 
 * @param funny 
 */
void set_review_funny(Review r, int funny);

/*Seta quantos votos Cool tem o business*/
/**
 * @brief 
 * 
 * @param r 
 * @param cool 
 */
void set_review_cool(Review r, int cool);

/**
 * @brief Define a data da review
 * 
 * @param r Review
 * @param date Data a ser definida
 */
void set_review_date(Review r, char *date);

/**
 * @brief Define o texto do review
 * 
 * @param r Review
 * @param text Texto a ser definido
 */
void set_review_text(Review r, char *text);

// ----------- FUNÇÕES AUXILIARES ------------ /
/**
 * @brief Adiciona uma interação com a review (cool, funny, useful)
 * 
 * @param r Review a ser modificada
 * @param __get_value__ Função para obter o valor a ser modificado (getter de review)
 * @param __set_value__ Função para atualizar o valor da Review (setter de review)
 */
void add_review(Review r, int(__get_value__)(Review), void(__set_value__)(Review, int));

/**
 * @brief Verifica se o text de uma Review tem uma determinada word
 * 
 * @param text String com o texto de uma review 
 * @param word Word a procurar
 * @return int 0 se falso; 1 se verdadeiro
 */
int text_has_word(void *text, void *word);

/**
 * @brief Inicializa uma nova review a partir de um buffer
 * 
 * @param buffer Buffer com info
 * @return Review Apontador para review
 */
Review init_review(char *buffer);

/**
 * @brief Verifica se duas Reviews são iguais
 * 
 * @param r1 Review a ser comparada 
 * @param r2 Review a ser comparada
 * @return int 0 se falso; 1 caso contrário
 */
int equals_reviews(Review r1, Review r2);

/**
 * @brief Destrói o Review, libertando o espaço na Mem
 * 
 * @param r Review a destruir 
 */
void free_review(Review r);

#endif
