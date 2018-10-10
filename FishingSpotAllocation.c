#include<stdio.h>
#include<stdlib.h>
#define TRUE 1
#define FALSE 0

typedef struct gEntry {
    int gatePosition; 
    int fisher_men;
}gEntry;

int Answer;

int main(void) 
{
    int N, G, *fishingSpots, *visited;
    gEntry *arr;
    
    scanf("%d %d", &N, &G);
    gEntry = (gEntry*)malloc(G * sizeof(gEntry));
    visited = (int*)malloc(G * sizeof(int));
    fishingSpots = (int*)calloc(N + 1, sizeof(int));
    
    for(int i = 0; i < G; i++)
        scanf("%d %d", &arr[i].gatePosition, &arr[i].fisher_men);
    
    Answer = 999999; 
    for(int i = 0; i < G; ++i) 
    {
        solveAllPossibilites(i, 0, 1, visited, fishingSpots, arr, N, G);
        visited[i] = FALSE;
        resetFishSpot(i, fishingSpots);
    }
    return 0;
}

void resetFishSpot(int index, int *fishSpot) 
{ 
    int i; 
    for (i = 1; i <= N; i++) 
        if (fishSpot[i] == index + 1) 
            fishSpot[i] = FALSE; 
}

void solveAllPossibilities(int index, int sum, int count, *visited, *fishSpots, gEntry *arr, int N, int G)
{
    int npos, pos1, pos2, score, i;
    visited[index] = 1;
    if(sum > answer)
        return;
    npos = calculateDistance(index, &pos1, &pos2, &score, visited, fishSpots); 
    if (count == G) 
    { 
        if (Answer > sum) 
            Answer = sum; 
        return; 
    } 
  
    if (npos == 1) 
    { 
        for (i = 0; i < MAX; i++) 
        { 
            if (visited[i] == 0) 
            { 
                solve(i, sum, count + 1); 
                visited[i] = 0; 
                resetFishSpot(i, fishSpots); 
            } 
        } 
    } 
    else if (npos == 2) 
    { 
        fishspot[pos1] = index + 1; 
        for (i = 0; i < G; i++) 
        { 
            if (visited[i] == 0) 
            { 
                solve(i, sum, count + 1); 
                visited[i] = 0; 
                resetFishSpot(i, fishSpots); 
            } 
        } 
  
        fishspot[pos1] = 0; 
        fishspot[pos2] = index + 1; 
        for (i = 0; i < MAX; i++) 
        { 
            if (visited[i] == 0) 
            { 
                solve(i, sum, count + 1); 
                visited[i] = 0; 
                resetFishSpot(i, fishSpots);
            } 
        } 
        fishspot[pos2] = 0; 
    } 
}

int calculateDistance(int index, int *pos1, *pos2, int *score, int *visited, int *fishSpots, gEntry *arr) 
{
    int i, sum = 0, left_min = 999999, right_min = 999999,  
                                    left, right, npos = 0; 
    *pos1 = *pos2 = *score = 0; 
  
    left = right = arr[index].gatePosition; 
   
    for(int i = 1; i < arr[index].fisher_men; i++) 
    {
        if(fishSpots[arr[index].gatePosition] == FALSE) 
        {
            sum++;
            fishSpots[arr[index].gatePosition] = index + 1;
        }
        else 
        {
            while ((left > 0) && (fishSpot[left] > 0)) 
                left--; 
            
            while((right <= N) && (fishSpots[right] > 0))
                right++;
            
            if ((left > 0) && (fishspot[left] == 0)) 
                left_min = gate[index] - left + 1; 
  
            if ((right <= N) && (fishspot[right] == 0)) 
                right_min = right - gate[index] + 1;
            
            if (right_min == left_min) 
            { 
                // Place 2 fishermen, if avaiable 
                if ((fishermen[index] - i - 1) > 1) 
                { 
                    fishspot[left] = fishspot[right] = index + 1; 
                    sum += (2 * left_min); 
                    i++; 
  
                    // If all fishermen are alloted spots 
                    if (i == fishermen[index]) 
                    { 
                        npos = 1; 
                        *score = sum; 
                        return npos; 
                    } 
                } 
                else
                { 
                    sum += left_min; 
                    fishspot[left] = index + 1; 
                } 
            }
            else if (left_min < right_min) 
            { 
                sum += left_min; 
                fishspot[left] = index + 1; 
            } 
            else if (right_min < left_min) 
            { 
                sum += right_min; 
                fishspot[right] = index + 1; 
            } 
        }
    }
    left_min = right_min = 99999; 
  
    // Allot spot to last fishermen 
    while ((left > 0) && (fishspot[left] > 0)) 
        left--; 
  
    if ((left > 0) && (fishspot[left] == 0)) 
        left_min = gate[index] - left + 1; 
  
    while ((right <= N) && (fishspot[right] > 0)) 
        right++; 
  
    if ((right <= N) && (fishspot[right] == 0)) 
        right_min = right - gate[index] + 1; 
  
    if ((sum + left_min) == (sum + right_min)) 
    { 
        npos = 2; 
        *pos1 = left; 
        *pos2 = right; 
        *score = sum + left_min; 
    } 
    else if ((sum + left_min) > (sum + right_min)) 
    { 
        npos = 1; 
        *score = sum + right_min; 
        fishspot[right] = index + 1; 
    } 
    else if ((sum + left_min) < (sum + right_min)) 
    { 
        npos = 1; 
        *score = sum + left_min; 
        fishspot[left] = index + 1; 
    } 
  
    return npos; 
}
