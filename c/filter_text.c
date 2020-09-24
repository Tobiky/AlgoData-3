#include <stdio.h>
#include <ctype.h>

void main(void)
{
    int result;

    while ((result = getchar()) != EOF)
    {
        if (isalpha(result) | result == ' ' | result == '\n')
        {
            putchar(result);
        }
        else
        {
            putchar(' ');
        }
    }
}