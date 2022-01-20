#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "reviewRepository.h"
#include "glib_warningavoid.h"
#include "hash_table.h"
#include "db.h"

/* ------ PRIVATE ------ */

struct review_repository
{
    HashTable *review;
} RWNode;

/* ------ AUX ------ */

int str_equals__(void *s1, void *s2)
{
    return g_str_equal((char *)s1, (char *)s2);
}

/* ------ PUBLIC ------ */

ReviewRepository init_rev_repo(char *file_path, UserRepository userRepository, BusinessRepository business_repository)
{
    ReviewRepository new_repo = malloc(sizeof(RWNode));

    new_repo->review = loadReviews(file_path, userRepository, business_repository);

    return new_repo;
}

Review get_review_by_id(ReviewRepository rv, char *id)
{
    Review r = init_review(NULL);
    if (_lookup(rv->review, id, (void **)&r, key_cmp_str, hash_key_str) == 0)
        return r;
    free(r);

    return NULL;
}

HashTable *get_review_table(ReviewRepository rv)
{
    return rv->review;
}

Review *get_reviews_by_bus_id(ReviewRepository rr, char *bus_id)
{
    GArray *rev_arr = _filter(get_review_table(rr), (void *)get_review_bus_id, (void *)bus_id, str_equals__);
    return (Review *)rev_arr->data;
}

float get_stars_bus_id(ReviewRepository rr, char *bus_id)
{
    Review *reviews = get_reviews_by_bus_id(rr, bus_id);
    int i;
    float stars = 0;
    for (i = 0; reviews[i]; i++)
    {
        stars += get_review_stars(reviews[i]);
    }

    return stars / (i - 1);
}

GArray *get_revs_w_word(ReviewRepository rv, char *word)
{
    if(rv)
    {
        HashTable *reviews = get_review_table(rv);
        return _filter(reviews, (void *)get_review_text, word, text_has_word);
    }
    return NULL;   
}

void free_review_repo(ReviewRepository rr)
{
    _free_hashtable(&(rr->review), free, (void *)free_review);
    free(rr);
}