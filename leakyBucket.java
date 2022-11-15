import java.util.*;

public class leaky {
    static int min(int x, int y) {
        if (x < y)
            return x;
        else
            return y;
    }

    public static void main(String[] args) {
        int drop = 0, mini, nsec, cap, count = 0, i, process;
        int inp[] = new int[25];
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter The Bucket Size\n");
        cap = sc.nextInt();
        System.out.println("Enter The Operation Rate\n");
        process = sc.nextInt();
        System.out.println("Enter The No. Of Seconds You Want To Stimulate\n");
        nsec = sc.nextInt();
        for (i = 0; i < nsec; i++) {
            System.out.print("Enter The Size Of The Packet Entering At " + i + 1 + "sec");
            inp[i] = sc.nextInt();

        }
        System.out.println("\nSecond | Packet Recieved | Packet Sent | Packet Left |Packet Dropped|\n");
        System.out.println("----------------------------------------------------------------------\n");
        for (i = 0; i < nsec; i++) {
            count += inp[i];
            if (count > cap) {
                drop = count - cap;
                count = cap;
            }
            System.out.print(i + 1);
            System.out.print("\t\t" + inp[i]);
            mini = min(count, process);
            System.out.print("\t\t" + mini);
            count = count - mini;
            System.out.print("\t\t" + count);
            System.out.print("\t\t" + drop);
            drop = 0;
            System.out.println();
        }
        for (; count != 0; i++) {
            if (count > cap) {
                drop = count - cap;
                count = cap;
            }
            System.out.print(i + 1);
            System.out.print("\t\t0");
            mini = min(count, process);
            System.out.print("\t\t" + mini);
            count = count - mini;
            System.out.print("\t\t" + count);
            System.out.print("\t\t" + drop);
            System.out.println();
        }
    }
}
/*
 * Output:
 * 
 * Enter The Bucket Size
 * 5
 * Enter The Operation Rate
 * 2
 * Enter The No.Of Seconds You Want To Stimulate
 * 3
 * Enter The Size Of The Packet Entering At 1 sec
 * 5
 * Enter The Size Of The Packet Entering At 1 sec
 * 4
 * Enter The Size Of The Packet Entering At 1 sec
 * 3 Second|Packet Recieved|Packet Sent|Packet Left|Packet Dropped|
 * -----------------------------------------------------------------------------
 * ----
 * 1 5 2 3 0
 * 2 4 2 3 2
 * 3 3 2 3 1
 * 4 0 2 1 0
 * 5 0 1 0 0
 */





Program 12: 
Title: Implementation of Leaky Bucket Algorithm.  
Aim: Write a program for congestion control using leaky bucket algorithm.

import java.io.*;
import java.util.*;

class Queue

{
    int q[], f = 0, r = 0, size;

    void insert(int n) {
        Scanner in = new Scanner(System.in);
        q = new int[10];
        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter " + i + " element: ");
            int ele = in.nextInt();
            if (r + 1 > 10) {
                System.out.println("\nQueue is full \nLost Packet: " + ele);
                break;
            } else {
                r++;
                q[i] = ele;
            }
        }
    }

    void delete() {
        Scanner in = new Scanner(System.in);
        Thread t = new Thread();
        if (r == 0)
            System.out.print("\nQueue empty ");
        else {
            for (int i = f; i < r; i++) {
                try {
                    t.sleep(1000);
                } catch (Exception e) {
                }
                System.out.print("\nLeaked Packet: " + q[i]);
                f++;
            }
        }
        System.out.println();
    }
}

class Leaky extends Thread {
    public static void main(String ar[]) throws Exception {
        Queue q = new Queue();
        Scanner src = new Scanner(System.in);
        System.out.println("\nEnter the packets to be sent:");
        int size = src.nextInt();
        q.insert(size);
        q.delete();
    }
}


/*OUTPUT:
 
Enter the packets to be sent: 
12 
Enter 0 element: 2 
Enter 1 element: 3 
Enter 2 element: 5 
Enter 3 element: 6 
Enter 4 element: 8 
Enter 5 element: 9 
Enter 6 element: 4 
Enter 7 element: 5 
Enter 8 element: 6 
Enter 9 element: 2 
Enter 10 element: 3 
Queue is full  

Lost Packet: 3 
Leaked Packet: 2 
Leaked Packet: 3 
Leaked Packet: 5 
Leaked Packet: 6 
Leaked Packet: 8 
Leaked Packet: 9 
Leaked Packet: 4 
Leaked Packet: 5 
Leaked Packet: 6 
Leaked Packet: 2 */
