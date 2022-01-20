#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "db.h"
#include "business.h"
#include "user.h"
#include "review.h"

/* -------- PRIVATE -------- */

#define BUFFER_SIZE 5200

/* -------- PUBLIC -------- */

HashTable *loadBusiness(char *fileName, BusinessRepository br)
{
    if (fileName == NULL)
        fileName = BUSINESS_FILE;
    FILE *fp = fopen(fileName, "r");
    HashTable *ht;
    _initHashTable(&ht, HASH_TABLE_INITSIZE);
    if (fp)
    {
        Business b;
        char *buffer = malloc(sizeof(char) * BUFFER_SIZE);

        if (fileName && ht && buffer)
        {
            while (fgets(buffer, BUFFER_SIZE, fp))
            {
                b = initBusiness(buffer);
                _update(&ht, get_business_id(b), b, key_cmp_str, hash_key_str);
                insert_bus_by_city(br, b);
            }
            fclose(fp);
        }
    }
    else
    {
        perror("Erro ao abrir ficheiro!");
    }
    return ht;
}

HashTable *loadUsers(char *fileName)
{
    if (fileName == NULL)
        fileName = USERS_FILE;

    FILE *fp = fopen(fileName, "r");
    HashTable *ht;
    _initHashTable(&ht, HASH_TABLE_INITSIZE);
    if (fp)
    {
        User u;

        char *buffer = malloc(sizeof(char) * BUFFER_SIZE);

        if (fileName && ht && buffer)
        {
            while (fgets(buffer, BUFFER_SIZE, fp))
            {
                u = init_user(buffer);
                _update(&ht, get_user_ID(u), u, key_cmp_str, hash_key_str);
            }
            fclose(fp);
        }
    }
    else
    {
        perror("Erro ao abrir ficheiro!");
    }

    return ht;
}

HashTable *loadReviews(char *filename, UserRepository userRepository, BusinessRepository businessRepository)
{
    HashTable *ht;
    Review r;
    char *buffer = malloc(sizeof(char) * BUFFER_SIZE);
    _initHashTable(&ht, HASH_TABLE_INITSIZE);
    FILE *fp = fopen(filename, "r");

    if (fp)
    {
        while (fgets(buffer, BUFFER_SIZE, fp))
        {
            r = init_review(buffer);

            if (get_user_by_id(userRepository, get_review_id(r)) && get_business_by_id(businessRepository, get_review_bus_id(r)))
            {
                add_review_to_user(userRepository, get_review_user_id(r), get_review_id(r));
                add_review_to_business(businessRepository, get_review_bus_id(r), get_review_id(r), get_review_stars(r));
                _update(&ht, get_review_id(r), r, key_cmp_str, hash_key_str);
            }
        }

        fclose(fp);
    }
    else
    {
        perror("Erro ao abrir ficheiro!");
    }

    return ht;
}
