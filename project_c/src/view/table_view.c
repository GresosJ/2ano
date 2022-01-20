/**
 * @file table_view.c
 * @author Marco
 * @brief 
 * @version 0.1
 * @date 2021-04-23
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#include "cores.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "table_view.h"
#include "glib_warningavoid.h"

void print_border(unsigned int cols)
{
    for (unsigned int i = 0; i < cols; i++)
    {
        printf("+--------------------");
    }
    printf("+\n");
}

void print_header(ROW row)
{
    guint cols = get_num_row_cols(row);
    print_border(cols);
    print_row(row);
    print_border(cols);
}
void print_element(char *elem)
{
    if (elem)
    {
        printf("+ %20s ", elem);
    }
}

void print_row(ROW row)
{
    guint cols = get_num_row_cols(row);
    for (unsigned int i = 0; i < cols; i++)
        print_element(get_column(row, i));
    printf("+\n");
}

void print_body(TABLE t)
{
    print_header(get_theader(t));
}

void print_table(TABLE t, unsigned int offset, int limit)
{
    ROW row;
    guint cols = get_num_table_cols(t);
    print_header(get_theader(t));
    for (; limit != 0 && (row = get_register(t, offset++)); limit--)
    {
        print_row(row);
    }
    print_border(cols);
}

void print_row_to_csv(FILE *fp, ROW row, char *delim)
{
    char *all_cols_delim = row_join_delim(row, delim);
    fprintf(fp, "%s\n", all_cols_delim);
}

int print_table_to_csv(TABLE t, char *delim, char *file_path)
{
    FILE *fp = fopen(file_path, "w");
    ROW row;
    if (fp)
    {
        print_row_to_csv(fp, get_theader(t), delim);
        for (guint i = 0; (row = get_register(t, i)); i++)
        {
            print_row_to_csv(fp, row, delim);
        }
        return 1;
    }
    perror("Erro ao abrir o ficheiro");
    return 0;
}

void pagination_footer()
{
    printf(COR_N_VERDE "q -> sair; h -> para trás; l -> para frente\n" COR_RESET);
}

void pagination(TABLE t)
{
    guint offset = 0;
    guint limit = 20;
    char input;
    guint tsize = table_size(t);
    if (tsize > limit)
    {
        print_table(t, offset = MIN(offset + limit, limit - offset), limit);
        pagination_footer();
        while ((input = getchar()) && input != 'q')
        {
            if (input == 'l')
            {
                offset = MIN(offset + limit, tsize - limit);
            }
            else if (input == 'h')
            {
                offset = MAX(offset - limit, limit);
            }
            print_table(t, offset, limit);
            pagination_footer();
        }
    }
    else
    {
        print_table(t, offset, limit);
    }
}

// ! DEBUG AREA
// int main()
// {
//     load_sgr(/mnt/d/business.csv,/mnt/d/users.csv,db/reviews.csv); x = business_started_by_letter(sgr, B);
//     TABLE t = init_table();
//     char th1[10] = "header1";
//     char th2[10] = "header2";
//     char l3[15] = "user_id_132312";
//     char l4[10] = "l2";
//     char l5[10] = "l3";
//     char l6[15] = "User_id_132312";
//     ROW th = init_row();
//     add_column(th, th1);
//     add_column(th, th2);
//     add_column(th, l5);
//     add_column(th, l5);
//     add_column(th, l5);

//     set_theader(t, th);

//     th = get_theader(t);

//     ROW r1 = init_row();
//     add_column(r1, l3);
//     add_column(r1, l4);
//     add_column(r1, l4);
//     add_column(r1, l4);
//     add_column(r1, l4);

//     //add_register(t, r1);

//     ROW r2 = init_row();
//     add_column(r2, l3);
//     add_column(r2, l5);
//     add_column(r2, l5);
//     add_column(r2, l5);
//     add_column(r2, l5);
//     //add_register(t, r2);

//     ROW g1 = get_register(t, 0);
//     ROW g2 = get_register(t, 1);

//     //TABLE t2 = filter_table(t, 0, l6, FALSE);
//     // print_header(th);
//     // print_row(r2);
//     // print_border(get_num_table_cols(t));

//     print_table(t, 0, 20);
//     //print_table(t2,0, 10);
//     return 0;
// }