#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "userRepository.h"
#include "glib_warningavoid.h"
#include "hash_table.h"
#include "db.h"

/* ------- PRIVATE ------- */

struct user_repository
{
    HashTable *user;
}USRNode;

/* ------- PUBLIC ------- */

UserRepository init_user_repo(char *file_path)
{
    UserRepository new_repo = malloc(sizeof(USRNode));

    new_repo->user = loadUsers(file_path);

    return new_repo;
}

User get_user_by_id(UserRepository usr, char *id)
{
    User u = init_user(NULL);
    if(_lookup(usr->user, id, (void **)&u, key_cmp_str, hash_key_str) == 0)
        return u;
    free(u);
    return NULL;
}

HashTable *get_user_table(UserRepository usr)
{
    return usr->user;
}

void add_review_to_user(UserRepository usr, char *user_id, char *review_id)
{
    User u = get_user_by_id(usr, user_id);
    
    if(u)
    {
        add_user_review(u, review_id); 
    }
}

void free_user_repo(UserRepository ur)
{
    _free_hashtable(&(ur->user), free, (void *)free_user);
    free(ur);
}