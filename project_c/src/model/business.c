#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include "business.h"

#define BUFFER_SIZE 1024

/* ---------- PRIVATE ---------- */
struct business
{
    char *business_id;
    char *name;
    char *city;
    char *state;
    GArray *categories;
    GArray *review_ids;
    float all_stars;
} BNode;

/* ---------- PUBLIC ----------- */

char *get_business_id(Business b)
{
    if (b)
        return strdup(b->business_id);
    return NULL;
}

char *get_business_name(Business b)
{
    if (b)
        return strdup(b->name);
    return NULL;
}

char *get_business_city(Business b)
{
    if (b)
        return strdup(b->city);
    return NULL;
}

char *get_business_state(Business b)
{
    if (b)
        return strdup(b->state);
    return NULL;
}

GArray *get_business_categories(Business b)
{
    if (b)
        return b->categories;
    return NULL;
}

GArray *get_business_review_ids(Business b)
{
    if (b)
        return b->review_ids;
    return NULL;
}

float get_business_stars(Business b)
{
    if (b)
    {
        GArray *reviews = get_business_review_ids(b);
        float total_reviews = (float)reviews->len;

        return total_reviews > 0 ? (b->all_stars / total_reviews) : 0;
    }
    return 0;
}

int set_business_name(Business b, char *name)
{
    int res = 0;
    if (b)
    {
        b->name = g_strdup(name);
        res = 1;
    }
    return res;
}

int set_business_city(Business b, char *city)
{
    int res = 0;

    if (b)
    {
        b->city = g_strdup(city);
        g_strchomp(b->city);
        res = 1;
    }
    return res;
}

int set_business_state(Business b, char *state)
{
    int res = 0;
    if (b)
    {
        b->state = g_strdup(state);
        res = 1;
    }
    return res;
}

int set_business_categories(Business b, char *categories)
{
    int res = 0;
    if (b)
    {
        b->categories = g_array_new(TRUE, TRUE, sizeof(char *));
        char *token = strtok(categories, DEC_DELIM);
        while (token)
        {
            add_business_category(b, token);
            token = strtok(NULL, DEC_DELIM);
        }
        res = 1;
    }
    return res;
}

void set_business_review_ids(Business b)
{
    if (b)
        b->review_ids = g_array_new(TRUE, TRUE, sizeof(char *));
}

gboolean find_category(GArray *categories, char *category)
{
    int res = 0;
    if (categories)
    {
        for (guint i = 0; i < categories->len && res == 0; i++)
        {
            res = g_ascii_strcasecmp(category, g_array_index(categories, char *, i)) == 0;
        }
    }
    return res;
}

gboolean has_category(Business b, char *category)
{
    GArray *categories = get_business_categories(b);

    return find_category(categories, category);
}

gboolean business_has_review_id(Business b, char *review_id)
{
    int res = 0;

    GArray *review_ids = get_business_review_ids(b);

    if (review_ids)
    {
        for (guint i = 0; i < review_ids->len && res == 0; i++)
        {
            res = g_str_equal(review_id, g_array_index(review_ids, char *, i));
        }
    }
    return res;
}

void add_business_stars(Business b, float stars)
{
    if (b)
        b->all_stars += stars;
}

int add_business_category(Business b, char *category)
{
    int res = 0;
    if (!has_category(b, category))
    {
        GArray *categories = get_business_categories(b);
        if (categories)
        {
            char *copy = g_strdup(category);
            g_array_append_val(categories, copy);
            res = 1;
        }
    }
    return res;
}

gboolean add_business_review_id(Business b, char *review_id, float stars)
{
    int res = 0;
    if (!business_has_review_id(b, review_id))
    {
        GArray *review_ids = get_business_review_ids(b);
        char *copy = g_strdup(review_id);
        g_array_append_val(review_ids, copy);
        add_business_stars(b, stars);
        res = 1;
    }
    return res;
}

gboolean equals_business(Business b1, Business b2)
{
    if (b1 == b2)
        return 1;
    if (b1 == NULL || b2 == NULL)
        return 0;
    return strcmp(get_business_id(b1), get_business_id(b2)) == 0;
}

Business init_business_empty()
{
    Business n = malloc(sizeof(BNode));

    return n;
}

Business initBusiness(char *buffer)
{
    Business newBusiness = malloc(sizeof(BNode));
    if (buffer && newBusiness)
    {
        newBusiness->business_id = strdup(strtok(buffer, DELIM));
        newBusiness->all_stars = 0;
        set_business_name(newBusiness, strtok(NULL, DELIM));
        set_business_city(newBusiness, strtok(NULL, DELIM));
        set_business_state(newBusiness, strtok(NULL, DELIM));
        set_business_categories(newBusiness, strtok(NULL, DELIM));
        set_business_review_ids(newBusiness);
    }

    return newBusiness;
}

void showBusiness(void *business)
{
    if (business)
    {
        Business bus = (Business)business;
        printf("|  %s  +  %s + stars: %f |\n",
               get_business_id(bus),
               get_business_name(bus),
               get_business_stars(bus));
    }
}

void free_business(Business b)
{
    free(b->business_id);
    free(b->name);
    free(b->city);
    free(b->state);
    g_array_free(b->categories, TRUE);
    g_array_free(b->review_ids, TRUE);
    free(b);
}

// ! DEBUG
// int main()
// {
//     char token[100] = {"id;name;city;state;cat1,cat2,cat3\0"};
//     Business b = initBusiness(token);
//     add_business_review_id(b, "rev_id1", 2);
//     add_business_review_id(b, "rev_id2", 3);
//     showBusiness(b);
//     printf("Tem a categoria? %d\n", has_category(b, "cat1"));
// }