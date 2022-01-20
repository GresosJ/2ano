#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "businessRepository.h"
#include "hash_table.h"
#include "db.h"

/* ------ PRIVATE ------ */

struct business_repository
{
    HashTable *business;
    GTree *group_by_city;
} BRNode;

gboolean get_tree_values(gpointer key, gpointer value, GArray *data)
{
    g_array_append_val(data, value);
    return 0;
}

/* ------  AUX   ------ */

int has_prefix(void *s1, void *s2)
{
    return g_str_has_prefix((char *)s1, (char *)s2);
}

int str_equals(void *s1, void *s2)
{
    return g_str_equal((char *)s1, (char *)s2);
}

int str_equals_sensitive(void *s1, void *s2)
{
    return g_ascii_strcasecmp((char *)s1, (char *)s2) == 0;
}

gint cmp_str(const void *v1, const void *v2)
{
    return g_ascii_strcasecmp((char *)v1, (char *)v2);
}

/* ------ PUBLIC ------ */

BusinessRepository init_bus_repo(char *file_path)
{
    BusinessRepository new_repo = malloc(sizeof(BRNode));

    new_repo->group_by_city = g_tree_new((GCompareFunc)g_ascii_strcasecmp);

    new_repo->business = loadBusiness(file_path, new_repo);
    return new_repo;
}

Business get_business_by_id(BusinessRepository br, char *id)
{
    Business b = initBusiness(NULL);
    if (_lookup(br->business, id, (void **)&b, key_cmp_str, hash_key_str) == 0)
        return b;
    free(b);
    return NULL;
}

Business *get_business_start_by_letter(BusinessRepository br, char letter)
{
    char *letr = malloc(sizeof(char) * 2);
    letr[0] = letter;
    letr[1] = '\0';
    GArray *found = _filter(br->business, (void *)get_business_name, letr, has_prefix);
    return (Business *)found->data;
}

GArray *get_business_by_city(BusinessRepository br, char *city)
{
    return _filter(br->business, (void *)get_business_city, city, str_equals_sensitive);
}

void add_review_to_business(BusinessRepository br, char *bus_id, char *review_id, float stars)
{
    Business b = get_business_by_id(br, bus_id);
    
    if (b)
    {
        add_business_review_id(b, review_id, stars);
    }
}

HashTable *get_bus_table(BusinessRepository br)
{
    return br->business;
}

int find_cat_compatible(void* garray, void* char_) {
    return find_category((GArray*) garray, (char*) char_);
}

GArray *get_business_by_category(BusinessRepository br, char *category)
{
    return _filter(br->business, (void *)get_business_categories, (void *)category, find_cat_compatible);
}

GTree *get_all_cities(BusinessRepository br)
{
    return _hash_to_tree(br->business, (void *)get_business_city, (void *)get_business_city, cmp_str);
}

void insert_bus_by_city(BusinessRepository br, Business b)
{
    char *city = get_business_city(b);
    GTree *by_city = br->group_by_city;
    GArray *businesses;
    if ((businesses = (GArray *)g_tree_lookup(by_city, city)) == NULL)
    {
        // caso ainda não exista a cidade
        businesses = g_array_new(TRUE, TRUE, sizeof(Business));
        g_array_append_val(businesses, b);
        g_tree_insert(by_city, city, businesses);
    }
    else
    {
        // caso a cidade já exista
        g_array_append_val(businesses, b);
    }
}

GArray *get_top_bus_by_city(BusinessRepository br, int top, GCompareFunc(sort_func))
{
    GArray *grouped_cities = g_array_new(TRUE, FALSE, sizeof(GArray *));
    g_tree_foreach(br->group_by_city, (GTraverseFunc)get_tree_values, grouped_cities);
    guint len = grouped_cities->len;
    GArray *each_group;
    for (guint i = 0; i < len; i++)
    {
        each_group = g_array_index(grouped_cities, GArray *, i);
        g_array_sort(each_group, sort_func);
        if (each_group->len > (guint)top)
        {
            int num_elems = each_group->len - top;
            g_array_remove_range(each_group, (guint)(top - 1), (guint)(num_elems >= 0 ? num_elems : 0));
        }
    }
    return grouped_cities;
}

gboolean free_gtree_elem(char **key, GArray **value, gpointer data)
{
    free(*key);
    g_array_free(*value, TRUE);
    return 0;
}

void free_business_repo(BusinessRepository br)
{
    _free_hashtable(&(br->business), free, (void *)free_business);
    //g_tree_foreach(br->group_by_city, (GTraverseFunc)free_gtree_elem, NULL);
    free(br);
}