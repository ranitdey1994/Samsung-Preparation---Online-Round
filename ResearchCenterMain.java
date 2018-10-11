import java.util.Scanner;

class QEntry {
    
    private int x;
    private int y;
    private int dist;
    
    public QEntry(int x, int y, int dist) {
        this.x = x;
        this.y = y;
        this.dist = dist;
    }
    public int getAbscissa() {
        return x;
    }
    public int getOrdinate() {
        return y;
    }
    public int getDist() {
        return dist;
    }
}

class MyQueue {
    
    int front;
    int rear;
    int capacity;
    QEntry arr[];
    
    public MyQueue(int c) {
        front = -1;
        rear = -1;
        capacity = c;
        arr = new QEntry[capacity];
    }
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
    public boolean isEmpty() {
        return (front == -1 && rear == -1);
    }
}
class Point {
    public int x;
    public int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class ResearchCenterMain {
    
    public static final int MAX_CAPACITY = (1 << 11);
    
    public static int dx[] = {0, -1, 0, 1}; 
    public static int dy[] = {1, 0, -1, 0};
    
    public static boolean isSafe(int x,int y, int n, boolean mat[][], boolean visited[][]) {
        return (x >= 0 && x < n && y >= 0 && y < n && mat[x][y] && !visited[x][y]);
    }
    
    public static void setAns(int ans[][], int n) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                ans[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    
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
        int n = sc.nextInt();
        boolean mat[][], visited[][];
        mat = new boolean[n][n];
        visited = new boolean[n][n];
        for(int i = 0; i< n; i++) {
            for(int j = 0; j < n; j++) {
                if(sc.nextInt() == 0)
                    mat[i][j] = false;
                else 
                    mat[i][j] = true;
            }
        }
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