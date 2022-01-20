#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "review.h"
#include "glib_warningavoid.h"
#define BUFFER_SIZE 1024

/* ---------- PRIVATE ---------- */

struct reviews
{
    char *review_id;
    char *user_id;
    char *business_id;
    float stars;
    int useful;
    int funny;
    int cool;
    char *date;
    GArray *text;
} NReview;

/* ---------- PUBLIC ----------- */

char *get_review_id(Review r)
{
    if (r)
        return g_strdup(r->review_id);
    return NULL;
}

char *get_review_user_id(Review r)
{
    if (r)
        return g_strdup(r->user_id);
    return NULL;
}

char *get_review_bus_id(Review r)
{
    if (r)
        return g_strdup(r->business_id);
    return NULL;
}

float get_review_stars(Review r)
{
    if (r)
        return r->stars;
    return 0.0;
}

int get_review_useful(Review r)
{
    if (r)
        return r->useful;
    return 0;
}

int get_review_funny(Review r)
{
    if (r)
        return r->funny;
    return 0;
}

int get_review_cool(Review r)
{
    if (r)
        return r->cool;
    return 0;
}

char *get_review_date(Review r)
{
    if (r)
        return g_strdup(r->date);
    return NULL;
}

char *get_review_text(Review r)
{
    if (r)
        return g_strdup(r->text->data);
    return NULL;
}

int set_review_id(Review r, char *review_id)
{
    int res = 0;

    if (r)
    {
        r->review_id = g_strdup(review_id);
        res = 1;
    }
    return res;
}

int set_review_user_id(Review r, char *user_id)
{
    int res = 0;
    if (r)
    {
        r->user_id = g_strdup(user_id);
        res = 1;
    }
    return res;
}

int set_review_bus_id(Review r, char *business_id)
{
    int res = 0;

    if (r)
    {
        r->business_id = g_strdup(business_id);
        res = 1;
    }
    return res;
}

void set_review_stars(Review r, double stars)
{
    if (r)
        r->stars = stars;
}

void set_review_useful(Review r, int useful)
{
    if (r)
        r->useful = useful;
}

void set_review_funny(Review r, int funny)
{
    if (r)
        r->funny = funny;
}

void set_review_cool(Review r, int cool)
{
    if (r)
        r->cool = cool;
}

void set_review_date(Review r, char *date)
{
    if (r)
    {
        r->date = strdup(date);
    }
}

void set_review_text(Review r, char *text)
{
    if (r)
    {
        r->text = g_array_new(TRUE, TRUE, sizeof(char));
        g_array_append_vals(r->text, (void *)text, strlen(text));
    }
}

/*---- FUNÇÕES AUXILIARES----*/

void add_review(Review r, int(__get_value__)(Review), void(__set_value__)(Review, int))
{
    __set_value__(r, __get_value__(r) + 1);
}

int text_has_word(void *text, void *word)
{
    return strstr((char *)text, (char *)word) != NULL;
}

Review init_review(char *buffer)
{
    Review newReview = malloc(sizeof(NReview));

    if (buffer && newReview)
    {
        set_review_id(newReview, strtok(buffer, DELIM));
        set_review_user_id(newReview, strtok(NULL, DELIM));
        set_review_bus_id(newReview, strtok(NULL, DELIM));
        set_review_stars(newReview, atof(strtok(NULL, DELIM)));
        set_review_useful(newReview, atoi(strtok(NULL, DELIM)));
        set_review_funny(newReview, atoi(strtok(NULL, DELIM)));
        set_review_cool(newReview, atoi(strtok(NULL, DELIM)));
        set_review_date(newReview, strtok(NULL, DELIM));
        set_review_text(newReview, strtok(NULL, DELIM));
    }

    return newReview;
}

int equals_reviews(Review r1, Review r2)
{
    if (r1 == r2)
        return 1;

    if (r1 == NULL || r2 == NULL)
        return 0;

    return strcmp(get_review_id(r1), get_review_id(r2)) == 0;
}

void free_review(Review r)
{
    free(r->review_id);
    free(r->user_id);
    free(r->business_id);
    free(r->date);
    g_array_free(r->text, TRUE);
    free(r);
}