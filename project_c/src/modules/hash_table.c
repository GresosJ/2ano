#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "hash_table.h"

/* ---------- PRIVATE ---------- */
#define FREE -1
#define DELETED -2

typedef struct bucket
{
    int probeC; // -1: Free; -2: Deleted
    void *key;
    void *value;
} Bucket;

struct hashtable
{
    unsigned int num_elem;
    int size;
    Bucket *table;
};

void showBucket(Bucket b, void(__show_value__)(void *))
{
    if (b.probeC >= 0)
    {
        __show_value__(b.value);
    }
}

/* ---------- PUBLIC ---------- */

void _showHashTable(HashTable *ht, void(__show_value__)(void *))
{
    int size = ht->size;
    printf("Tamanho: %d\n", ht->size);
    for (int i = 0; i < size; i++)
    {
        showBucket(ht->table[i], __show_value__);
    }
}

int _initHashTable(HashTable **ht, int size)
{
    (*ht) = malloc(sizeof(HashTable));
    if ((*ht))
    {
        (*ht)->num_elem = 0;
        (*ht)->size = size;
        (*ht)->table = malloc(size * sizeof(Bucket));
        for (int i = 0; i < size; i++)
        {
            (*ht)->table[i].probeC = FREE;
            (*ht)->table[i].key = NULL;
            (*ht)->table[i].value = NULL;
        }
        return 0;
    }
    return 1;
}

int _clone(HashTable **ht_source, HashTable **ht_target, int(__cmp__)(void *, void *), int(__hash__)(void *, int))
{
    for (int i = 0; i < (*ht_source)->size; i++)
    {
        if ((*ht_source)->table[i].probeC >= 0)
            _update(ht_target, (*ht_source)->table[i].key, (*ht_source)->table[i].value, __cmp__, __hash__);
    }
    free(*ht_source);
    (*ht_source) = (*ht_target);
    return 0;
}

int reHash(HashTable **ht, int(__cmp__)(void *, void *), int(__hash__)(void *, int))
{
    HashTable *new_ht;
    int size = (*ht)->size;
    if (_initHashTable(&new_ht, size * 2) == 0)
    {
        return _clone(ht, &new_ht, __cmp__, __hash__);
    }
    return 1;
}

int _lookup(HashTable *ht, void *key, void **value, int(__cmp__)(void *, void *), int(__hash__)(void *, int))
{
    int i = __hash__(key, ht->size), probe = 0;
    while ((ht->table[i].probeC >= 0) &&
           (__cmp__(ht->table[i].key, key) != 0) &&
           (probe < ht->size))
    {
        i = (i << 1) % ht->size;
        probe++;
    }
    if (probe == ht->size)
    {
        return 2; // overload - not exists
    }

    if (ht->table[i].probeC >= 0)
    {
        *value = ht->table[i].value;
        return 0;
    }

    return 1;
}

void *_findValue(HashTable *ht, void *(__get_value__)(void *), void *cmp_value, int(__cmp__)(void *, void *))
{
    int i = 0;
    unsigned int j = 0;
    void *found = NULL;
    while (!found && i < ht->size && j < ht->num_elem)
    {
        if (ht->table[i].probeC != FREE)
        {
            j++;
            if (__cmp__(__get_value__(ht->table[i].value), cmp_value))
                found = ht->table[i].value;
        }
        i++;
    }
    return found;
}

GArray *_filter(HashTable *ht, void *(__get_value__)(void *), void *cmp_value, int(__cmp__)(void *, void *))
{
    int i = 0;
    unsigned int j = 0;
    GArray *list = g_array_new(TRUE, TRUE, sizeof(void *));

    while (i < ht->size && j < ht->num_elem)
    {
        if (ht->table[i].probeC != FREE)
        {
            j++;
            if (__cmp__(__get_value__(ht->table[i].value), cmp_value))
            {
                g_array_append_val(list, ht->table[i].value);
            }
        }
        i++;
    }
    return list;
}

GTree *_hash_to_tree(HashTable *ht, void *(__get_key__)(void *), void *(__get_value__)(void *), int(__cmp_key__)(const void *, const void *))
{
    GTree *new_tree = g_tree_new(__cmp_key__);
    int i;
    guint j;
    i = j = 0;
    while (i < ht->size && j < ht->num_elem)
    {
        if (ht->table[i].probeC != FREE)
        {
            j++;
            void *value = ht->table[i].value;
            g_tree_insert(new_tree, __get_key__(value), __get_value__(value));
        }
        i++;
    }
    return new_tree;
}

int _update(HashTable **ht, void *key, void *value, int(__cmp__)(void *, void *), int(__hash__)(void *, int))
{
    int i = __hash__(key, (*ht)->size), probe = 0;

    while (((*ht)->table[i].probeC >= 0) &&
           (__cmp__((*ht)->table[i].key, key) != 0) &&
           (probe < (*ht)->size))
    {
        i = (i << 1) % (*ht)->size;
        probe++;
    }
    if ((probe / (*ht)->size) > 0.7)
    {
        reHash(ht, __cmp__, __hash__);
        return _update(ht, key, value, __cmp__, __hash__);
    }

    if ((*ht)->table[i].probeC != FREE)
    {
        (*ht)->table[i].value = value;
        return 1; // exists
    }
    (*ht)->num_elem = (*ht)->num_elem + 1;
    (*ht)->table[i].key = key;
    (*ht)->table[i].value = value;
    (*ht)->table[i].probeC = probe;
    return 0; // newkey
}

int _length(HashTable *ht)
{
    return ht->num_elem;
}

int _remove(HashTable **ht, void *key, int(__cmp__)(void *, void *), int(__hash__)(void *, int), void(__free_key__)(void *), void(__free_value__)(void *))
{
    int i = __hash__(key, (*ht)->size), probe = 0;

    while (((*ht)->table[i].probeC >= 0) &&
           (__cmp__((*ht)->table[i].key, key) != 0) &&
           (probe < (*ht)->size))
    {
        i = (i << 1) % (*ht)->size;
        probe++;
    }

    if (probe >= ((*ht)->size))
    {
        return 2;
    }

    if ((*ht)->table[i].probeC != FREE)
    {
        __free_value__((*ht)->table[i].value);
        __free_key__((*ht)->table[i].key);
        (*ht)->num_elem = (*ht)->num_elem - 1;
        (*ht)->table[i].probeC = DELETED;
        return 0; // exists and was deleted
    }

    return 1;
}

void _free_hashtable(HashTable **ht, void(__free_key__)(void *), void *(__free_value__)(void *))
{
    int i = 0;
    while (i < (*ht)->size)
    {
        if ((*ht)->table[i].probeC != FREE)
        {
            __free_value__((*ht)->table[i].value);
            __free_key__((*ht)->table[i].key);
        }
        i++;
    }
    free((*ht));
}

/* ----- Funções de APOIO ----- */

int key_cmp_str(void *_char1, void *_char2)
{
    char *char1 = (char *)_char1;
    char *char2 = (char *)_char2;
    return strcmp(char1, char2);
}

int hash_key_str(void *_key, int size)
{
    char *key = (char *)_key;
    int r = 0;
    for (; *key; key++)
        r = (r * 3 + (*key)) % size;
    return r;
}