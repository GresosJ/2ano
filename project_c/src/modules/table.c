#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include "table.h"

typedef ROW THeader;
typedef GArray *TBody;
typedef GArray *TColumns;

typedef struct row
{
    TColumns columns;
    guint size;
} RNode;
struct table
{
    THeader theader;
    TBody tbody;
} TNode;

unsigned int get_num_row_cols(ROW r)
{
    if (r)
        return r->size;
    return -1;
}

void set_num_row_cols(ROW r, guint num_cols)
{
    if (r)
        r->size = num_cols;
}

ROW get_theader(TABLE t)
{
    if (t != NULL && t->theader != NULL)
        return t->theader;

    return NULL;
}

void set_theader(TABLE t, THeader new_theader)
{
    if (t != NULL)
    {
        t->theader = new_theader;
    }
}

unsigned int get_num_table_cols(TABLE t)
{
    if (t)
        return get_num_row_cols(get_theader(t));
    return -1;
}

TBody get_tbody(TABLE t)
{
    if (t != NULL)
        return t->tbody;

    return NULL;
}

TABLE set_tbody(TABLE t, TBody new_body)
{
    t->tbody = new_body;
    return t;
}

char *get_column(ROW row, guint j)
{
    if (j < get_num_row_cols(row))
        return g_array_index(row->columns, gchar *, j);

    return NULL;
}

/**
 * @brief Procura o índice da coluna com o filtro
 * 
 * @param row THeader a pesquisar
 * @param column_name Nome da coluna
 * @return int -1 se não existir; índice da coluna
 */
int find_column_index(THeader row, char *column_name)
{
    int res = -1;
    char *column_element;
    for (guint i = 0; res < 0 && (column_element = get_column(row, i)); i++)
    {
        if (g_ascii_strcasecmp(column_element, column_name) == 0)
            res = i;
    }
    return res;
}

char **get_all_columns(ROW row)
{
    gchar **all_cols = malloc(sizeof(gchar *) * (row->size + 1));
    guint i;
    for (i = 0; i < row->size; i++)
    {
        all_cols[i] = get_column(row, i);
    }
    all_cols[i] = NULL;
    return all_cols;
}

char *row_join_delim(ROW row, char *delim)
{
    return g_strjoinv(delim, get_all_columns(row));
}

TABLE init_table(int num_cols, ...)
{
    TABLE t = malloc(sizeof(TNode));

    va_list valist;
    va_start(valist, num_cols);

    THeader th = init_row();

    for (int i = 0; i < num_cols; i++)
    {
        add_column(th, va_arg(valist, char *));
    }

    set_theader(t, th);

    set_tbody(t, g_array_new(FALSE, TRUE, sizeof(ROW)));
    return t;
}

ROW init_row()
{
    ROW r = malloc(sizeof(RNode));
    r->columns = g_array_new(FALSE, TRUE, sizeof(gchar *));
    r->size = 0;
    return r;
}

void add_column(ROW row, char *column)
{
    char *copystr = g_strdup(column);
    row->columns = g_array_append_val(row->columns, copystr);
    set_num_row_cols(row, get_num_row_cols(row) + 1);
}

void add_columns(ROW row, int num_cols, ...)
{
    va_list valist;
    va_start(valist, num_cols);
    for (int i = 0; i < num_cols; i++)
    {
        add_column(row, va_arg(valist, char *));
    }
}

void add_columnsv(ROW row, char **columns)
{
    for (int i = 0; columns[i]; i++)
    {
        add_column(row, g_strdelimit(columns[i], "\n", '\0'));
    }
    g_strfreev(columns);
}

void add_register(TABLE t, ROW row)
{
    if (get_num_table_cols(t) == get_num_row_cols(row))
    {
        set_tbody(t, g_array_append_val(get_tbody(t), row));
    }
}

void add_error_line(TABLE t, int num_cols)
{
    ROW r = init_row();
    for (int i = 0; i < num_cols; i++)
    {
        add_column(r, "-");
    }
    add_register(t, r);
}

ROW get_register(TABLE t, unsigned int row)
{
    TBody tbody = get_tbody(t);
    if (row < tbody->len)
        return g_array_index(tbody, ROW, row);
    return NULL;
}

TABLE get_element(TABLE ts, guint row, guint column)
{
    TABLE t = init_table(1, get_column(get_theader(ts), column));
    char *elem = get_column(get_register(ts, row), column);
    if (elem)
    {
        ROW r = init_row();
        add_column(r, elem);
        add_register(t, r);
    }
    else
    {
        add_error_line(t, 1);
    }
    return t;
}

TABLE col_projection(TABLE ts, int col_start, int col_end)
{
    TABLE t = init_table(0);

    // construir header
    THeader th = init_row();
    guint num_cols = get_num_row_cols(get_theader(ts));
    col_start = MIN(MAX(col_start, 0), num_cols);
    col_end =  MIN(col_end, num_cols);
    for(int i = col_start; i <= col_end; i++)
    {
        add_column(th, get_column(get_theader(ts), i));
    }
    set_theader(t, th);

    // construir body
    TBody tb = ts->tbody;
    for (guint i = 0; i < tb->len; i++)
    {
        ROW r = init_row();
        ROW current = g_array_index(tb, ROW, i);
            for(guint j = col_start; j <= col_end; j++)
            {
                add_column(r, get_column(current, j));
            }
        add_register(t, r);
    }

    return t;

}

TABLE filter_table(TABLE t, unsigned int column_search, char *filter, OPERATOR op)
{
    TBody tbody = get_tbody(t);
    ROW row;
    gchar *elem;
    TABLE new = init_table(0);
    gboolean add = FALSE;
    set_theader(new, get_theader(t));
    int cmp_res;
    for (unsigned int i = 0; i < tbody->len; i++)
    {
        row = g_array_index(tbody, ROW, i);
        elem = get_column(row, column_search);
        if (elem)
        {
            cmp_res = g_ascii_strcasecmp(elem, filter);
            switch (op)
            {
            case LT:
                add = cmp_res < 0;
                break;
            case EQ:
                add = cmp_res == 0;
                break;
            case GT:
                add = cmp_res > 0;
                break;
            default:
                add = cmp_res == 0;
                break;
            }
        }
        if (add)
            add_register(new, row);
        add = FALSE;
    }
    return new;
}

TABLE filter_table_by_column_name(TABLE t, char *column_name, char *value, OPERATOR op)
{
    int column_i = find_column_index(get_theader(t), column_name);

    if (column_i < 0)
        return init_table(0);

    return filter_table(t, (guint)column_i, value, op);
}


char **read_line(FILE *fp, char *buffer, char *delim)
{
    if (fgets(buffer, 2048, fp))
        return g_strsplit(buffer, delim, -1);
    return NULL;
}

TABLE csv_to_table(char *file_path, char *delim)
{
    TABLE t = init_table(0);
    FILE *fp = fopen(file_path, "r");
    if (fp)
    {
        THeader th = init_row();
        char *buffer = malloc(sizeof(char) * 2048);
        char **buf_split;
        add_columnsv(th, read_line(fp, buffer, delim));
        set_theader(t, th);
        while ((buf_split = read_line(fp, buffer, delim)))
        {
            ROW row = init_row();
            add_columnsv(row, buf_split);
            add_register(t, row);
        }
    }
    return t;
}

void free_row(ROW row)
{
    GArray *columns = row->columns;
    for (guint i = 0; i < columns->len; i++)
    {
        g_free(g_array_index(columns, char *, i));
    }
    g_array_free(columns, TRUE);
}

int table_size(TABLE t)
{
    TBody tb = get_tbody(t);
    return (int)tb->len;
}

void free_table(TABLE t)
{
    TBody tb = t->tbody;
    THeader th = t->theader;
    free_row(th);
    for (guint i = 0; i < tb->len; i++)
    {
        free_row(g_array_index(tb, ROW, i));
    }
    free(t);
}