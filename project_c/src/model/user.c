#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "user.h"

/* ------------ Private ------------ */

struct user
{
    char *user_id;
    char *nome;
    GArray *review_ids;
    GArray *friends;
} UNode;

/* ------------ Public ------------ */

User init_user_empty()
{
    User u = malloc(sizeof(UNode));

    return u;
}

User init_user(char *string)
{
    User new_user = malloc(sizeof(UNode));

    if (string && new_user)
    {
        set_user_ID(new_user, g_strdup(strtok(string, DELIM)));
        set_user_name(new_user, g_strdup(strtok(NULL, DELIM)));
        set_user_reviews(new_user, g_strdup(strtok(NULL, DELIM)));
        set_user_friends(new_user, g_strdup(strtok(NULL, DELIM)));
    }

    return new_user;
}

char *get_user_ID(User user)
{
    if (user)
        return g_strdup(user->user_id);
    return NULL;
}

char *get_user_name(User user)
{
    if (user)
        return g_strdup(user->nome);
    return NULL;
}

GArray *get_user_reviews(User user)
{
    if (user)
        return user->review_ids;
    return NULL;
}

GArray *get_user_friends(User user)
{
    if (user)
        return user->friends;
    return NULL;
}

int set_user_ID(User user, char *user_id)
{
    int res = 0;

    if (user)
    {
        user->user_id = g_strdup(user_id);
        ;
        res = 1;
    }

    return res;
}

int set_user_name(User user, char *name)
{
    int res = 0;

    if (user)
    {
        user->nome = g_strdup(name);
        ;
        res = 1;
    }

    return res;
}

int has_user_reviews(User user)
{
    return user->review_ids->len != 0;
}

int has_user_friends(User user)
{
    return user->friends->len != 0;
}

int user_has_review(User user, char *review_id)
{
    int res = 0;

    if (has_user_reviews(user))
    {
        GArray *reviews = get_user_reviews(user);
        char *rev_id;
        guint size = reviews->len;
        for (unsigned int i = 0; !res && i < size; i++)
        {
            rev_id = g_array_index(reviews, char *, i);
            res = (int)g_str_equal(review_id, rev_id);
        }
    }
    return res;
}

int user_has_friend(User user, char *friend_id)
{
    int res = 0;

    if (has_user_friends(user))
    {
        GArray *friends = get_user_friends(user);
        char *fren_id;
        guint size = friends->len;
        for (unsigned int i = 0; !res && i < size; i++)
        {
            fren_id = g_array_index(friends, char *, i);
            res = (int)g_str_equal(friend_id, fren_id);
        }
    }
    return res;
}

int add_user_review(User user, char *review_id)
{
    int res = 0;

    if (user_has_review(user, review_id))
        res = -1;

    else
    {
        GArray *reviews = get_user_reviews(user);
        if (reviews)
        {
            char *new = g_strdup(review_id);
            g_array_append_val(reviews, new);
            res = 1;
        }
    }
    return res;
}

int add_user_friend(User user, char *friend_id)
{
    int res = 0;

    if (user_has_friend(user, friend_id))
        res = -1;

    else
    {
        GArray *friends = get_user_friends(user);
        if (friends)
        {
            char *new = g_strdup(friend_id);
            g_array_append_val(friends, new);
            res = 1;
        }
    }
    return res;
}

int set_user_reviews(User user, char *reviews)
{
    int res = 0;
    if (user)
    {
        user->review_ids = g_array_new(TRUE, TRUE, sizeof(char *));
        char *token = strtok(reviews, SEC_DELIM);
        while (token)
        {
            add_user_review(user, token);
            token = strtok(NULL, SEC_DELIM);
        }
        res = 1;
    }
    return res;
}

int set_user_friends(User user, char *friends)
{
    int res = 0;
    if (user)
    {
        user->friends = g_array_new(TRUE, TRUE, sizeof(char *));
        char *token = strtok(friends, SEC_DELIM);
        while (token)
        {
            add_user_friend(user, token);
            token = strtok(NULL, SEC_DELIM);
        }
        res = 1;
    }
    return res;
}
int user_friend_number(User user)
{
    return user->friends->len;
}

void free_user(User user)
{
    free(user->user_id);
    free(user->nome);
    g_array_free(user->review_ids, TRUE);
    g_array_free(user->friends, TRUE);
    free(user);
}

int equals_user(User user1, User user2)
{
    if (user1 == user2)
        return 1;
    if (user1 == NULL || user2 == NULL)
        return 0;
    return strcmp(user1->user_id, user2->user_id) == 0;
}