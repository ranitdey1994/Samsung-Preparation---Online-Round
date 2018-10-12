import java.util.Scanner;
/**
 * Date 10/11/2018
 * @author Ranit Dey
 *
 * A Research team want to establish a research center in a region where they found some rare-   
 * elements. They want to make it closest to all the rare-elements as close as possible so that   
 * can reduce overall cost of research over there. It is given that all the rare-element’s 
 * location is connected by roads. It is also given that Research Center can only be build on 
 * road.Team decided to assign this task to a coder. If you feel you have that much potential 
 * Here is the Task :- Find the shortest of the longest distance of research center from given 
 * locations of rare-elements. Locations are given in the matrix cell form where 1 represents  
 * roads and 0 no road. Number of rare-element and their location was also given(number<=5) and
 * order of square matrix was less than equal to (20).
 
 * Reference
 * http://www.geeksforgeeks.org/samsung-delhi-interview-experience-set-27-campus/
 */
class QEntry {
    
    private int x;
    private int y;
    private int dist;
    /**
     * Creates a new QEntry object
     * @param x : abscissa
     * @param y : ordinate
     * @param dist : updated distance value
     */
    public QEntry(int x, int y, int dist) {
        this.x = x;
        this.y = y;
        this.dist = dist;
    }
    /* returns the abscissa for the point */
    public int getAbscissa() {
        return x;
    }
    /* returns the ordinate for the point */
    public int getOrdinate() {
        return y;
    }
    /* returns the distance for the point */
    public int getDist() {
        return dist;
    }
}

class MyQueue {
    
    int front;
    int rear;
    int capacity;
    QEntry arr[];
    
    /**
     * Creates a new queue object
     * @param c : max capacity of queue
     */
    public MyQueue(int c) {
        front = -1;
        rear = -1;
        capacity = c;
        arr = new QEntry[capacity];
    }
    /**
     * A function to insert an element into the queue
     * @param in : entry for the queue
     */
    public void insert(QEntry in) {
        if((front == 0 && rear == (capacity - 1)) 
           || (rear == (front - 1) % (capacity - 1))) {
            System.out.println("Queue is full");
            return;
        }
        else if(front == -1) {
            front = rear = 0;
            arr[rear] = in;
        }
        else if(rear == (capacity - 1) && front != 0) {
            rear = 0;
            arr[rear] = in;
        }
        else {
            rear++;
            arr[rear] = in;
        }
    }
    
    /* A function to delete an element from the queue */
    public QEntry delete() {
        if(front == -1) {
            System.out.println("Queue is empty");
            return null;
        }
        QEntry qItem = arr[front];
        if(front == rear) {
            front = rear = -1;
        }
        else if(front == (capacity - 1)) {
            front = 0;
        }
        else
            front++;
        return qItem;
    }
   
    /* A function which checks if queue is empty or not */
    public boolean isEmpty() {
        return (front == -1 && rear == -1);
    }
}


class Point {
    public int x;
    public int y;
    /**
     * Creates a new point for representing rare-
     * elements location.
     * @param x : abscissa of the location.
     * @param y : ordinate of the location.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class ResearchCenterMain {
    
    public static final int MAX_CAPACITY = (1 << 11);    // maximum capacity of the queue.
    
    public static int dx[] = {0, -1, 0, 1}; 
    public static int dy[] = {1, 0, -1, 0};
    
    public static boolean isSafe(int x,int y, int n, boolean mat[][], boolean visited[][]) {
        return (x >= 0 && x < n && y >= 0 && y < n && mat[x][y] && !visited[x][y]);
    }
    
    /**
     * A utility function to reset the ans matrix
     * @param ans : stores all the updated distance values  
     * @param n   : no. of roads which are given as i/p 
     */
    public static void setAns(int ans[][], int n) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                ans[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    /**
     * A utility function to perform BFS to find distance to all research-centers.
     * @param i, j : starting location  
     * @param n   : no. of roads which are given as i/p 
     * @param mat : matrix to represent connectivity of the roads
     * @param ans : contains all the updated distance values
     */
    public static void BFS(int i, int j, boolean mat[][], int[][] ans, boolean visited[][], int n) {
        MyQueue queue = new MyQueue(MAX_CAPACITY);
        QEntry in = new QEntry(i, j, 0);
        visited[i][j] = true;
        queue.insert(in);
        while(!queue.isEmpty()) {
            QEntry out = queue.delete();
            int x = out.getAbscissa();
            int y = out.getOrdinate();
            int dist = out.getDist();
            ans[x][y] = dist;
            for(int id = 0; id < 4; id++) {
                int nx = x + dx[id];
                int ny = y + dy[id];
                if(isSafe(nx, ny, n, mat, visited)) {
                    visited[nx][ny] = true;
                    in = new QEntry(nx, ny, dist + 1);
                    queue.insert(in);
                }
            }
        }
    }
    /**
     * A utility function to display the given matrix.
     * @param ans : contains all the updated distance values  
     * @param n   : no. of roads which are given as i/p
     */
    public static void printMatrix(int ans[][], int n) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print(ans[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();    /* n : no. of roads which are given as i/p */
        boolean mat[][], visited[][];
        mat = new boolean[n][n];    /* mat : '1' represents a road '0' represents no road */
        visited = new boolean[n][n];
        /* filling up the matrix entries */
        for(int i = 0; i< n; i++) {
            for(int j = 0; j < n; j++) {
                if(sc.nextInt() == 0)
                    mat[i][j] = false;
                else 
                    mat[i][j] = true;
            }
        }
        /* no. of rare-elements and their locations are taken */
        int q = sc.nextInt();
        Point pArr[] = new Point[q];
        for(int i = 0; i < q; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            pArr[i] = new Point(x, y);
        }
        
        int fAns, max;
        fAns = Integer.MAX_VALUE;
        max = -1;
        /* max  : maximum distance of a research-center from a particular location */
        /* fAns : minimum distance of a research-center out of all farthest distances */
        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; j++) {
                visited = new boolean[n][n];
                int ans[][] = new int[n][n];
                
                setAns(ans, n);
                boolean flag = false;
                
                max = -1;
                if(mat[i][j]) {
                    BFS(i, j, mat, ans, visited, n);
                    for(int k = 0; k < q; ++k) {
                        if(ans[pArr[k].x][pArr[k].y] == Integer.MAX_VALUE) {
                            flag = true;
                            break;
                        }
                    }
                    if(!flag) {
                        for(int k = 0; k < q; k++) {
                            max = Math.max(max, ans[pArr[k].x][pArr[k].y]);
                        }
                    }
                    
                    fAns = Math.min(fAns, max);
                }     
            }
        }
        System.out.println(fAns);
        sc.close();
    }
}
