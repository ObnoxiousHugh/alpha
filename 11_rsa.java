import java.util.*;
import java.io.*;

public class TCPServer {
    static int gcd(int m, int n) {
        while (n != 0) {
            int r = m % n;
            m = n;
            n = r;
        }
        return m;
    }

    public static void main(String args[]) {
        int p = 0, q = 0, n = 0, e = 0, d = 0, phi = 0;
        int nummes[] = new int[100];
        int encrypted[] = new int[100];
        int decrypted[] = new int[100];
        int i = 0, j = 0, nofelem = 0;
        Scanner sc = new Scanner(System.in);
        String message;
        System.out.println("Enter the Message tobe encrypted:");
        message = sc.nextLine();
        System.out.println("Enter value of p and q\n");
        p = sc.nextInt();
        q = sc.nextInt();
        n = p * q;
        phi = (p - 1) * (q - 1);
        for (i = 2; i < phi; i++)
            if (gcd(i, phi) == 1)
                break;
        e = i;
        for (i = 2; i < phi; i++)
            if ((e * i - 1) % phi == 0)
                break;
        d = i;
        for (i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            int a = (int) c;
            nummes[i] = c - 96;
        }
        nofelem = message.length();
        for (i = 0; i < nofelem; i++) {
            encrypted[i] = 1;
            for (j = 0; j < e; j++)
                encrypted[i] = (encrypted[i] * nummes[i]) % n;
        }

        System.out.println("\n Encrypted message\n");
        for (i = 0; i < nofelem; i++) {
            System.out.print(encrypted[i]);
            System.out.print((char) (encrypted[i] + 96));
        }
        for (i = 0; i < nofelem; i++) {
            decrypted[i] = 1;
            for (j = 0; j < d; j++)
                decrypted[i] = (decrypted[i] * encrypted[i]) % n;
        }
        System.out.println("\n Decrypted message\n ");
        for (i = 0; i < nofelem; i++)
            System.out.print((char) (decrypted[i] + 96));
        return;
    }
}

/*Output
Enter the text:
hello
Enter the value of P and Q :
5
7
Encrypted Text is: 8 h 10 j 17 q 17 q 15 o
Decrypted Text is: hello*/








Title: Implementation of Simple RSA algorithm 
Aim: Write a program for simple RSA algorithm to encrypt and decrypt the data.  
Program:

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
 
public class RSA
{
    private BigInteger P;
    private BigInteger Q;
    private BigInteger N;
    private BigInteger PHI;
    private BigInteger e;
    private BigInteger d;
    private int maxLength = 1024;
    private Random R;
 
    public RSA()
    {
        R = new Random();
        P = BigInteger.probablePrime(maxLength, R);
         Q = BigInteger.probablePrime(maxLength, R);
        N = P.multiply(Q);
       PHI = P.subtract(BigInteger.ONE).multiply(  Q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(maxLength / 2, R);
        while (PHI.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(PHI) < 0)
        {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(PHI);
    }
 
    public RSA(BigInteger e, BigInteger d, BigInteger N)
    {
        this.e = e;
        this.d = d;
        this.N = N;
    }
 
    public static void main (String [] arguments) throws IOException
    {
        RSA rsa = new RSA();
        DataInputStream input = new DataInputStream(System.in);
        String inputString;
        System.out.println("Enter message you wish to send.");
        inputString = input.readLine();
        System.out.println("Encrypting the message: " + inputString);
        System.out.println("The message in bytes is:: "
                + bToS(inputString.getBytes()));
        // encryption
        byte[] cipher = rsa.encryptMessage(inputString.getBytes());
        // decryption
        byte[] plain = rsa.decryptMessage(cipher);
        System.out.println("Decrypting Bytes: " + bToS(plain));
        System.out.println("Plain message is: " + new String(plain));
    }
 
    private static String bToS(byte[] cipher)
    {
        String temp = "";
        for (byte b : cipher)
        {
            temp += Byte.toString(b);
        }
        return temp;
    }
 
    // Encrypting the message
    public byte[] encryptMessage(byte[] message)
    {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }
    // Decrypting the message
    public byte[] decryptMessage(byte[] message)
    {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }
}

/*OUTPUT:
Enter message you wish to send.
cns
Encrypting the message: cns
The message in bytes is:: 99110115
Decrypting Bytes: 99110115
Plain message is: cns*/
