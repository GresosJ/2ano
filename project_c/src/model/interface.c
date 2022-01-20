#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "argp.h"

#include "glib_warningavoid.h"
#include "hash_table.h"
#include "interface.h"
#include "interface_view.h"
#include "table.h"

/* -------- PRIVATE -------- */

#define INPUT_SIZE 2048
#define NUM_COMMANDS 16

/**
 * @brief Pega numa str passa para tipo char
 * 
 * @param str String
 * @return char
 */
char str_to_chr(char *str)
{
    char res;
    res = str[0];

    return res;
}

/**
 * @brief Pega numa str passa para tipo OPERATOR
 * 
 * @param str String
 * @return operator
 */
OPERATOR str_to_op(char *op)
{
    if (strcmp("LT", op) == 0)
        return LT;
    else if (strcmp("EQ", op) == 0)
        return EQ;
    else if (strcmp("GT", op) == 0)
        return GT;
    return FALSE;
}

static char *opts[NUM_COMMANDS] = {
    "load_sgr",
    "business_started_by_letter",
    "business_info",
    "businesses_reviewed",
    "business_with_stars_and_city",
    "top_businesses_by_city",
    "international_users",
    "top_businesses_with_category",
    "reviews_with_word",
    "show",
    "toCSV",
    "fromCSV",
    "filter",
    "proj",
    "[",
    "quit"};

/* -------- PUBLIC -------- */

void run_commands(EnvRepository er, char **commands)
{
    for (int i = 0; commands[i]; i++)
    {

        if (strlen(commands[i]) > 1)
        {
            loading();
            char **assign = g_strsplit(commands[i], "=", -1);
            if (g_strv_length(assign) > 1)
            {
                TABLE t = exec_command(er, assign[1]);
                add_variable(er, t, assign[0]);
                var_atualizada(assign[0]);
            }
            else
            {
                exec_command(er, assign[0]);
            }
        }
    }
}

void run_bash()
{
    EnvRepository er = init_env_repo();
    char input[INPUT_SIZE];
    interpertadorM();
    while (fgets(input, INPUT_SIZE, stdin))
    {
        interpertadorM();
        run_commands(er, g_strsplit(input, ";", -1));
    }
}

/**
 * @brief Descubre o indeci da funcao
 * 
 * @param func_name Nome da funcao a ser procurada
 * @return o indece dessa funcao
 */
int get_command_index(char *func_name)
{
    int res = -1;
    for (int i = 0; res == -1 && i < NUM_COMMANDS; i++)
    {
        if (strcmp(opts[i], func_name) == 0)
            res = i;
    }
    return res;
}

/**
 * @brief 
 * 
 * @param input Recebe o comando a ser executado sob a forma de array
 *              input[0] == nome da função
 *              input[>=1] == argumentos
 * @return int 1 -> válido
 *             0 -> número de argumentos inferior ao suposto
 *             -1 -> nome da função não existe
 */
int verify_input(char **input)
{
    int i, res = 0;
    if ((i = get_command_index(input[0])) >= 0)
    {
        res = 1;
    }

    return res;
}

TABLE exec_command(EnvRepository er, char *command)
{
    TABLE t = NULL;
    char **comm = g_strsplit_set(command, "(,)", -1);

    for (int i = 0; comm[i]; i++)
    {
        g_strstrip(comm[i]);
    }

    if (verify_input(comm))
    {
        switch (get_command_index(comm[0]))
        {
        case 0:
            set_env_sgr(er, loadSGR(comm[1], comm[2], comm[3]));
            sgr_atualizada(comm[1], comm[2], comm[3]);
            break;
        case 1:
            t = business_started_by_letter(get_env_sgr(er), str_to_chr(comm[2]));
            break;
        case 2:
            t = business_info(get_env_sgr(er), comm[2]);
            break;
        case 3:
            t = businesses_reviewed(get_env_sgr(er), comm[2]);
            break;
        case 4:
            t = business_with_stars_and_city(get_env_sgr(er), atof(comm[2]), comm[3]);
            break;
        case 5:
            t = top_businesses_by_city(get_env_sgr(er), atoi(comm[2]));
            break;
        case 6:
            t = international_users(get_env_sgr(er));
            break;
        case 7:
            t = top_business_with_category(get_env_sgr(er), atoi(comm[2]), comm[3]);
            break;
        case 8:
            t = reviews_with_word(get_env_sgr(er), atoi(comm[2]), comm[3]);
            break;
        case 9: // show
            t = get_table_by_var(er, comm[1]);
            if (t)
                pagination(t);
            break;
        case 10: // toCSV
            t = get_table_by_var(er, comm[1]);
            print_table_to_csv(t, comm[2], comm[3]);
            if (t)
                file_atualizada(comm[3]);
            break;
        case 11: // fromCSV
            t = csv_to_table(comm[2], comm[3]);
            break;
        case 12: //filter
            t = get_table_by_var(er, comm[1]);
            t = filter_table_by_column_name(t, comm[2], comm[3], str_to_op(comm[4]));
            break;
        case 13: //proj
            t = get_table_by_var(er, comm[1]);
            t = col_projection(t, atoi(comm[2]), atoi(comm[3]));
            break;
        case 15:
            free_env_repo(er);
            exit(0);
            break;

        default:
            t = (TABLE)init_table(1, "ERRO");
            add_error_line(t, 1);
            break;
        }
    }
    else if (strstr(comm[0], "["))
    {
        comm = g_strsplit_set(comm[0], "[]", -1);
        t = get_element(get_table_by_var(er, comm[0]), (guint)atoi(comm[1]), (guint)atoi(comm[3]));
    }
    else
    {
        //!ERRO
    }
    return t;
}