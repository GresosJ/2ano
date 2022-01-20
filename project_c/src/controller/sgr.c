#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "sgr.h"
#include "glib_warningavoid.h"

/* --------- PRIVATE --------- */
typedef struct sgr
{
    BusinessRepository business;
    ReviewRepository review;
    UserRepository user;
} SGRNode;

typedef struct city
{
    char *name;
    int counter;
} CNode;

/* --------- PRIVATE AUX --------- */

/**
 * @brief Ordena dois Businesses de forma descrescente 
 * @details Utilizada para fazer sort de um GArray
 * 
 * @param v1 Business a ser comparado 
 * @param v2 Business a ser comparado 
 * @return int Negativo se v1 for maior que v2; Positivo caso contrário; 0 se a diferença for 0
 */
int bus_stars_order_desc(Business *v1, Business *v2)
{
    float d1 = get_business_stars(*v1);
    float d2 = get_business_stars(*v2);
    return (int)d2 - d1;
}

/**
 * @brief Ordena dois Reviews de forma descrescente 
 * @details Utilizada para fazer sort de um GArray
 * 
 * @param v1 Review a ser comparado 
 * @param v2 Review a ser comparado 
 * @return int Negativo se r1 for maior que r2; Positivo caso contrário; 0 se a diferença for 0
 */
int cmp_rev_stars(Review *r1, Review *r2)
{
    float d1 = get_review_stars(*r1);
    float d2 = get_review_stars(*r2);
    int res = (int)d1 - d2;
    return res;
}

/**
 * @brief Verifica se o User fez um review no state daquele Business
 * 
 * @param bus Business cujo state esta a ser avaliado
 * @param states_visited States que o User ja fez ua Review
 * @return int O se n tiver visitado; 1 caso contrário
 */
int has_visited(Business bus, GArray *states_visited)
{
    int res = 0;

    char *state = get_business_state(bus);
    guint size = states_visited->len;
    for (unsigned int i = 0; !res && i < size; i++)
    {
        res = (int)g_str_equal(state, g_array_index(states_visited, char *, i));
    }

    return res;
}
/**
 * @brief Verifica se um user fez reviews em estados diferentes
 * 
 * @param reviews_id String com todas as reviews do User
 * @param sgr Sistema de Gestão de Recursos
 * @return int 1 se o utilizador é internacional, 0 caso contrário
 */
int is_international(void *reviews_id, void *s)
{
    int found = 0;
    SGR sgr = (SGR)s;
    GArray *rev_ids = (GArray *)reviews_id;
    GArray *states_visited = g_array_new(TRUE, TRUE, sizeof(char *));
    char *id;
    guint num_rev_ids = rev_ids->len;
    Review r;
    Business b;

    // caso só tenha um review ou 0, não é internacional
    if (num_rev_ids <= 1)
        return found;

    for (guint i = 0; i < num_rev_ids && found == 0; i++)
    {
        id = g_array_index(rev_ids, char *, i);
        r = get_review_by_id(get_review_repository(sgr), id);
        b = get_business_by_id(get_business_repository(sgr), get_review_bus_id(r));
        if ((found = has_visited(b, states_visited)) == 0)
        {
            char *state = get_business_state(b);
            g_array_append_val(states_visited, state);
        }
    }
    return found;
}

/**
 * @brief Adiciona à TABLE os valores de um GArray
 * 
 * @param t TABLE na qual vamos inserir
 * @param bus GArray com as informações a serem inseridas 
 */
void add_garray_to_table(TABLE t, GArray *bus)
{
    ROW r;
    Business b;
    if (bus)
    {
        for (unsigned int i = 0; i < bus->len; i++)
        {
            r = init_row();
            b = g_array_index(bus, Business, i);
            add_column(r, get_business_city(b));
            add_column(r, get_business_id(b));
            add_column(r, get_business_name(b));

            char *starsToString = malloc(sizeof(char) * 4);
            gcvt(get_business_stars(b), 2, starsToString);

            add_column(r, starsToString);

            add_register(t, r);
        }
    }
}

void free_sgr(SGR sgr)
{
    free_business_repo(sgr->business);
    free_review_repo(sgr->review);
    free_user_repo(sgr->user);
}

/* -------- PUBLIC --------- */

SGR init_sgr()
{
    SGR new = malloc(sizeof(SGRNode));
    new->business = NULL;
    new->review = NULL;
    new->user = NULL;
    return new;
}

BusinessRepository get_business_repository(SGR sgr)
{
    if (sgr)
        return sgr->business;
    return NULL;
}

void set_business_repository(SGR sgr, BusinessRepository br)
{
    // TODO
    // ! Mem free
    sgr->business = br;
}

UserRepository get_user_repository(SGR sgr)
{
    if (sgr)
        return sgr->user;
    return NULL;
}

void set_user_repository(SGR sgr, UserRepository usr)
{
    sgr->user = usr;
}

ReviewRepository get_review_repository(SGR sgr)
{
    if (sgr)
        return sgr->review;
    return NULL;
}

void set_review_repository(SGR sgr, ReviewRepository rv)
{
    sgr->review = rv;
}

/* --------- Queries --------- */

//Query 1
SGR loadSGR(char *busPath, char *userPath, char *revPath)
{
    SGR sgr = init_sgr();
    sgr->business = init_bus_repo(busPath);
    sgr->user = init_user_repo(busPath);
    sgr->review = init_rev_repo(revPath, sgr->user, sgr->business);
    return sgr;
}

//Query 2
TABLE business_started_by_letter(SGR sgr, char letter)
{
    TABLE t = init_table(2, "ID", "Business Name");

    if (sgr && sgr->business)
    {
        Business *bus_match = get_business_start_by_letter(sgr->business, letter);
        for (int i = 0; bus_match[i]; i++)
        {
            ROW r = init_row();
            add_column(r, get_business_id(bus_match[i]));
            add_column(r, get_business_name(bus_match[i]));
            add_register(t, r);
        }
    }
    else
    {
        add_error_line(t, 2);
    }
    return t;
}

//Query 3
TABLE business_info(SGR sgr, char *business_id)
{
    TABLE t = init_table(2, "ID", "Business Name");
    BusinessRepository br = get_business_repository(sgr);
    if (sgr && br)
    {
        Business bus = get_business_by_id(br, business_id);
        if (bus)
        {
            ROW r = init_row();
            add_columns(r, 2, get_business_id(bus), get_business_name(bus));
            add_register(t, r);
        }
    }
    else
    {
        add_error_line(t, 2);
    }

    return t;
}

//Query 4
TABLE businesses_reviewed(SGR sgr, char *user_id)
{
    TABLE t = init_table(2, "ID", "Business Name");
    UserRepository ur = get_user_repository(sgr);
    BusinessRepository br = get_business_repository(sgr);
    ReviewRepository rr = get_review_repository(sgr);
    if (ur && br && rr)
    {
        User u = get_user_by_id(ur, user_id);
        if (u)
        {
            Business b;
            Review r;
            char *rev_id;
            GArray *reviews = get_user_reviews(u);
            guint size = reviews->len;
            for (guint i = 0; i < size && (rev_id = g_array_index(reviews, char *, i)); i++)
            {
                r = get_review_by_id(rr, rev_id);
                b = get_business_by_id(br, get_review_bus_id(r));
                if (b)
                {
                    ROW row = init_row();
                    add_columns(row, 2, get_business_id(b), get_business_name(b));
                    add_register(t, row);
                }
            }
        }
    }
    else
    {
        add_error_line(t, 2);
    }
    return t;
}

//Query 5
TABLE business_with_stars_and_city(SGR sgr, float stars, char *city)
{
    TABLE t = init_table(4, "City", "ID", "Business Name", "Stars");
    BusinessRepository br = get_business_repository(sgr);
    float bus_stars;
    if (br)
    {
        GArray *bus_by_city_garr = get_business_by_city(br, city);
        Business *bus_by_city = (Business *)bus_by_city_garr->data;
        for (int i = 0; bus_by_city[i]; i++)
        {
            bus_stars = get_business_stars(bus_by_city[i]);
            if (bus_stars >= stars)
            {
                ROW row = init_row();
                char *starsToString = malloc(sizeof(char) * 6);
                gcvt(bus_stars, 2, starsToString);
                add_columns(row, 4, get_business_city(bus_by_city[i]), get_business_id(bus_by_city[i]), get_business_name(bus_by_city[i]), starsToString);
                add_register(t, row);
            }
        }
    }
    else
    {
        add_error_line(t, 3);
    }
    return t;
}

//Query 6
TABLE top_businesses_by_city(SGR sgr, int top)
{
    TABLE t = init_table(4, "City", "Business ID", "Name", "Stars");
    BusinessRepository br = get_business_repository(sgr);
    if (br)
    {
        GArray *bus_group_city = get_top_bus_by_city(br, top, (GCompareFunc)bus_stars_order_desc); // lista de listas de TOP business, agrupadas por cidade;

        guint num_cities = bus_group_city->len;
        for (guint i = 0; i < num_cities; i++)
        {
            add_garray_to_table(t, g_array_index(bus_group_city, GArray *, i));
        }
    }
    else
    {
        add_error_line(t, 4);
    }

    return t;
}

//Query 7
TABLE international_users(SGR sgr)
{
    TABLE t = init_table(2, "ID", "User name");
    UserRepository ur = get_user_repository(sgr);
    if (ur)
    {
        HashTable *users = get_user_table(ur);
        GArray *international = _filter(users, (void *)get_user_reviews, sgr, is_international);
        User u;
        for (guint i = 0; i < international->len && (u = g_array_index(international, User, i)); i++)
        {
            ROW r = init_row();
            add_column(r, get_user_ID(u));
            add_column(r, get_user_name(u));
            add_register(t, r);
        }
    }
    else
    {
        add_error_line(t, 2);
    }

    return t;
}

//Query 8
TABLE top_business_with_category(SGR sgr, int top, char *category)
{
    TABLE t = init_table(2, "id", "stars");
    Business b;
    BusinessRepository br = get_business_repository(sgr);
    if (br)
    {
        GArray *bus_w_category = get_business_by_category(br, category);
        // ordenar descendente
        g_array_sort(bus_w_category, (GCompareFunc)bus_stars_order_desc);
        for (guint i = 0; top > 0 && i < bus_w_category->len; top--, i++)
        {
            ROW row = init_row();
            b = g_array_index(bus_w_category, Business, i);
            char *starsToString = malloc(sizeof(char) * 6);
            gcvt(get_business_stars(b), 2, starsToString);

            add_columns(row, 2, get_business_id(b), starsToString);
            add_register(t, row);
        }
    }
    else
    {
        add_error_line(t, 2);
    }

    return t;
}

//Query 9
TABLE reviews_with_word(SGR sgr, int top, char *word)
{
    TABLE t = init_table(1, "Review ID");
    ReviewRepository rr = get_review_repository(sgr);
    if (rr)
    {
        GArray *rvs_w_word = get_revs_w_word(rr, word);
        g_array_sort(rvs_w_word, (GCompareFunc)cmp_rev_stars);
        Review r;
        for (guint i = 0; i < (unsigned int)top && (r = g_array_index(rvs_w_word, Review, i)); i++)
        {
            ROW row = init_row();
            add_column(row, get_review_id(r));
            add_register(t, row);
        }
    }
    else
    {
        add_error_line(t, 1);
    }
    return t;
}

// extra query
void destroy_sgr(SGR sgr)
{
    free_sgr(sgr);
}

// ! DEBUG AREA
// #include "table_view.h"
// int main()
// {
//     SGR n = loadSGR("/mnt/d/business.csv", "/mnt/d/users.csv", "db/reviews.csv");
//     TABLE t;
//     // // Query 2
//     // printf("--- Query 2 ---\n");
//     // TABLE t = business_started_by_letter(n, 'C');
//     // print_table(t, 0, 5);

//     // ROW r = get_register(t, 0);
//     // char **cols = get_all_columns(r);
//     // char *joined = g_strjoinv(";", cols);
//     //     printf("%s\n", joined);

//     // // // Query 3
//     // printf("--- Query 3 ---\n");
//     // char id[50] = {"iuII9PFLuMShi-2W06ay2A\0"};
//     // t = business_info(n, id);
//     // print_table(t, 0, 10);

//     // // // Query 4
//     // printf("--- Query 4 ---\n");
//     // char uid[40] = {"EP9YZR_fiDm3NKa46WHoTg\0"};
//     // t = businesses_reviewed(n, uid);
//     // print_table(t, 0, 100);
//     // print_table_to_csv(t, ";", "teste.csv");

//     // TABLE t = csv_to_table("teste.csv", ";");
//     // print_table(t, 0, -1);
//     // t = filter_table_by_column_name(t, "business name", "F", GT);
//     // print_table(t, 0, -1);
//     // // Query 5
//     printf("--- Query 5 ---\n");
//     char city[15] = {"Atlanta\0"};
//     t = business_with_stars_and_city(n, 4, city);
//     print_table(t, 0, 100);

//     // Query 6
//     printf("--- Query 6 ---\n");
//     t = top_businesses_by_city(n, 6);
//     print_table(t, 0, -1);

//     // // Query 7
//     // printf("--- Query 7 ---\n");
//     // t = international_users(n);
//     // print_table(t, 0, 100);

//     // //Query 8
//     printf("--- Query 8 ---\n");
//     t = top_business_with_category(n, 10, "Shopping");
//     print_table(t, 0, 100);

//     // // Query 9
//     // printf("--- Query 9 --- \n");
//     // t = reviews_with_word(n, 5, "EGGMEATMUFFIN");
//     // print_table(t, 0, 100);
//     return 0;
// }
