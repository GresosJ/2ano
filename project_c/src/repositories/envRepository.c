#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "glib_warningavoid.h"
#include "hash_table.h"
#include "table.h"
#include "envRepository.h"

/* ------ PRIVATE ------ */

struct env_repository
{
    HashTable *variables;
    SGR sgr;
} ENVNode;

/* ------ PUBLIC ------ */

EnvRepository init_env_repo()
{
    EnvRepository new = malloc(sizeof(ENVNode));
    _initHashTable(&(new->variables), HASH_TABLE_INITSIZE);
    return new;
}

HashTable *get_var_table(EnvRepository er)
{
    return er->variables;
}

TABLE get_table_by_var(EnvRepository er, char *var)
{
    TABLE env = init_table(0);
    g_strstrip(var);
    if (_lookup(er->variables, g_strdup(var), (void **)&env, key_cmp_str, hash_key_str) == 0)
        return env;
    
    free(env);
    return NULL;
}

int add_variable(EnvRepository er, TABLE t, char *var)
{
    g_strstrip(var);
    return _update(&(er->variables), g_strdup(var), t, key_cmp_str, hash_key_str);
}

void set_env_sgr(EnvRepository ev, SGR sgr)
{
    if(ev->sgr != NULL)
        destroy_sgr(ev->sgr);
    ev->sgr = sgr;
}

SGR get_env_sgr(EnvRepository ev)
{
    return ev->sgr;
}

void free_env_repo(EnvRepository er)
{
    _free_hashtable(&(er->variables), free, (void *)free_table);
    free(er);
}
