/**
 * @file table.h
 * @author Grupo 43
 * @brief Módulo de dados TABLE 
 * @version 0.1
 * @date 2021-04-29
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef TABLE_H
#define TABLE_H
#include "glib_warningavoid.h"

/*********************************************************
 * @author Grupo43
 * @date 08/04/2021
 * @details Módulo que permite a gestão de tabelas
 */

/**
 * @brief Método de construção de TABLE:
 *        TABLE t = init_table();
 *        Adicionar header:
 *          construir THeader
 *          ROW theader = init_row();
 *          adicionar as colunas do header
 *          add_column(theader, char*)
 *          atualizar theader da tabela
 *          set_theader(t, theader)
 *        Adicionar body (linhas):
 *          ROW linha_N = init_row();
 *          adicionar colunas - add_column(...)
 *          adicionar o registo: add_register(t, linha_N)
 * 
 *        ! TABLE construída por agregação, pelo que há partilha de apontadores!!
 */
typedef struct table *TABLE;
typedef struct row *ROW;
typedef enum op
{
    LT = -1,
    EQ,
    GT
} OPERATOR;
/**
 * @brief Get the num row cols
 * 
 * @param r ROW
 * @return unsigned int Número de colunas da linha
 */
unsigned int get_num_row_cols(ROW r);
/**
 * @brief Get the num table cols
 * 
 * @param t Table
 * @return unsigned int Número de colunas da tabela (através do THeader)
 */
unsigned int get_num_table_cols(TABLE t);

/**
 * @brief Inicializa uma table e constrói o HEADER com a info
 *        dos argumentos
 * 
 * @param num_cols Número de colunas
 * @param ... Lista de argumentos que vão servir de nome para as colunas
 * @return TABLE 
 */
TABLE init_table(int num_cols, ...);

/**
 * @brief Inicializa uma nova ROW
 * 
 * @return ROW Row inicializada
 */
ROW init_row();

/**
 * @brief Adiciona uma nova coluna
 * 
 * @param row Linha a ser atualizada
 * @param column Valor a ser acrescentado
 * @return ROW Linha atualizada
 */
void add_column(ROW row, char *column);

/**
 * @brief Adiciona várias colunas a uma linha
 * 
 * @param num_cols Número de colunas a adicionar
 * @param row Linha a ser atualizada
 * @param ... Elementos das colunas (tipo char*)!
 */
void add_columns(ROW row, int num_cols, ...);
/**
 * @brief Vai buscar uma coluna da linha
 * 
 * @param row Linha
 * @param j Coluna
 * @return char* Apontador (por agregação) do elemento na linha row, coluna j
 */
char *get_column(ROW row, guint j);

/**
 * @brief Vai buscar todas as colunas de uma linha e coloca num array de strings
 * 
 * @param row Linha
 * @return char** Lista de strings relativo às colunas
 */
char **get_all_columns(ROW row);

/**
 * @brief Agrega todas as colunas de uma linha em uma só string
 *        utiliza o delimitador indicado
 * 
 * @param row Linha a ser processada
 * @param delim Delimitador a utilizar
 * @return char* String com todas as colunas separadas por <delim>
 */
char *row_join_delim(ROW row, char *delim);
/**
 * @brief Define o header da table
 * 
 * @param t table a ser atualizada
 * @param new_theader Header (tipo ROW) a ser definida
 */
void set_theader(TABLE t, ROW new_theader);

/**
 * @brief Get the theader object
 * 
 * @param t Table
 * @return ROW Cabeçalho da coluna
 */
ROW get_theader(TABLE t);
/**
 * @brief Adicionar uma nova linha à tabela
 * 
 * @param t Tabela
 * @param columns GArray de colunas (do tipo gchar*)
 * @return TABLE Tabela atualizada
 */
void add_register(TABLE t, ROW row);

/**
 * @brief Vai buscar a linha à tabela
 * 
 * @param t Tabela
 * @param row Linha que quer ir buscar
 * @return ROW Linha da tabela encontrada ou NULL
 */
ROW get_register(TABLE t, unsigned int row);

/**
 * @brief Vai buscar o elemento na linha <row> e coluna <column>
 * 
 * @param ts Tabela a pesquisar
 * @param row Linha do elemento
 * @param column Coluna no elemento
 * @return char* elemento encontrado, se existir; NULL, caso contrário
 */
TABLE get_element(TABLE ts, guint row, guint column);

/**
 * @brief Vai buscar a informação que está entre duas colunas 
 * 
 * @param ts Tabela a pesquisar 
 * @param col_start 1ª coluna da nova tabela
 * @param col_end 2ª coluna da nova tabela
 * @return TABLE Nova tabela com a informação entre as duas colunas, caso exitem
 */
TABLE col_projection(TABLE ts, int col_start, int col_end);

/**
 * @brief Adiciona uma linha em branco (-)
 * 
 * @param t Table
 * @param num_cols Número de colunas
 */
void add_error_line(TABLE t, int num_cols);

/**
 * @brief Cria uma nova tabela aplicando um filtro à coluna indicada
 * 
 * @param t Tabela
 * @param column_search Coluna onde vai ser aplicado a pesquisa
 * @param filter String a procurar
 * @param op LT ; EQ ; GT
 * @return TABLE 
 */
TABLE filter_table(TABLE t, unsigned int column_search, char *filter, OPERATOR op);

/**
 * @brief Filtra uma tabela, a partir da coluna com o nome introduzido, com o valor value
 * 
 * @param t Tabela a filtrar
 * @param column_name Nome da coluna que vai ser filtrada
 * @param value Valor a procurar
 * @param op LT ; EQ ; GT
 * @return TABLE Tabela
 */
TABLE filter_table_by_column_name(TABLE t, char *column_name, char *value, OPERATOR op);
/**
 * @brief Lê um ficheiro e constrói uma table
 * 
 * @param file_path Path para o ficheiro CSV
 * @param delim Delimitador
 * @return TABLE Tabela gerada
 */
TABLE csv_to_table(char *file_path, char *delim);

/**
 * @brief Devolve o número de linhas de uma tabela
 * 
 * @param t TAbela
 * @return int  # linhas
 */
int table_size(TABLE t);

/**
 * @brief Elimina a tabela completa, incluíndo todo o conteúdo
 * 
 * @param t Tabela a ser eliminada da memória
 */
void free_table(TABLE t);

#endif