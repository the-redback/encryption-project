package encryptionproject;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/*
 Custom class for generating Biginteger sqrt ceil/floor
 */
class BigIntSqRoot {

    public static BigInteger bigIntSqRootFloor(BigInteger x)
            throws IllegalArgumentException {
        if (x.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Negative argument.");
        }
        // square roots of 0 and 1 are trivial and
        // y == 0 will cause a divide-by-zero exception
        if (x.equals(BigInteger.ZERO) || x.equals(BigInteger.ONE)) {
            return x;
        } // end if
        BigInteger two = BigInteger.valueOf(2L);
        BigInteger y;
        // starting with y = x / 2 avoids magnitude issues with x squared
        for (y = x.divide(two);
                y.compareTo(x.divide(y)) > 0;
                y = ((x.divide(y)).add(y)).divide(two));
        return y;
    } // end bigIntSqRootFloor

    public static BigInteger bigIntSqRootCeil(BigInteger x)
            throws IllegalArgumentException {
        if (x.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Negative argument.");
        }
        // square roots of 0 and 1 are trivial and
        // y == 0 will cause a divide-by-zero exception
        if (x.equals(BigInteger.ZERO) || x.equals(BigInteger.ONE)) {
            return x;
        } // end if
        BigInteger two = BigInteger.valueOf(2L);
        BigInteger y;
        // starting with y = x / 2 avoids magnitude issues with x squared
        for (y = x.divide(two);
                y.compareTo(x.divide(y)) > 0;
                y = ((x.divide(y)).add(y)).divide(two));
        if (x.compareTo(y.multiply(y)) == 0) {
            return y;
        } else {
            return y.add(BigInteger.ONE);
        }
    } // end bigIntSqRootCeil
} // end class bigIntSqRoot

class CustomKey {

    protected BigInteger p, q;
    protected BigInteger n;
    protected BigInteger PhiN;
    protected BigInteger e, d;

    public CustomKey() {
        initialize();
    }

    public void initialize() {
        int SIZE = 24;

        /*
         1. Let N = P*Q.
         */
        n = new BigInteger(SIZE, new Random());
        //String str="35";
        //n=new BigInteger(str);
        //System.out.print(n+"****\n");
        /*
         2. Compute X =ceil (sqrt (N)).
         */
        BigInteger x = BigIntSqRoot.bigIntSqRootCeil(n);

        while (true) {
            /*
             3. Compute Y =sqrt (X2 – N).
             */
            BigInteger tmp = x.multiply(x);
            tmp = tmp.subtract(n);
            BigInteger y = BigIntSqRoot.bigIntSqRootFloor(tmp);
            BigInteger yy = BigIntSqRoot.bigIntSqRootCeil(tmp);
            
           // System.out.print(x+" "+y+"\n");

            /*
             4. If Y is integer .
             5. Compute P =X – Y and Q =X + Y.
             Stop.
            
             6. Otherwise X ĺ X +1, X+ 2,…. , X + 2*X, …
             ……,X+ N.
             7. Continue step 3 to 6, till Y is integer.
             */
            if (y.equals(yy)) {
                p = x.subtract(y);
                q = x.add(y);
                break;
            } else {
                x = x.add(BigInteger.ONE);
            }
        }
        /* Step 3: Calculate ø(n) = (p - 1).(q - 1) */
        PhiN = p.subtract(BigInteger.valueOf(1));
        PhiN = PhiN.multiply(q.subtract(BigInteger.valueOf(1)));
        /* Step 4: Find e such that gcd(e, ø(n)) = 1 ; 1 < e < ø(n) */
        e = new BigInteger("2");
        while (PhiN.gcd(e).intValue() != 1) {
            e = e.add(new BigInteger("1"));
        }
        /* Step 5: Calculate d such that e.d = 1 (mod ø(n)) */
        d = e.modInverse(PhiN);

        //Debug
        /*
         n=BigInteger.valueOf(35);
         e=BigInteger.valueOf(11);
         d=BigInteger.valueOf(11);
         p=BigInteger.valueOf(5);
         q=BigInteger.valueOf(7);
         */
    }
}

public class MainClass extends javax.swing.JFrame {

    String inputText = "";
    String outputText = "";

    String[] msgText;
    String[] cipherText;

    int prime[] = new int[50010];
    CustomKey key;

    int SIZE = 24;
    int Mod = 256;
    int ModSize = 8;

    /**
     * Creates new form MainClass
     */
    public MainClass() {
        initComponents();
        sieve(50000);

        // RSA KEY
        key = new CustomKey();
        System.out.println("P:" + key.p + "\r\n" + "q:" + key.q + "\r\n" + "n:" + key.n + "\r\n" + "e:" + key.e + "\r\n" + "d:" + key.d + "\r\n" + "phi(n):" + key.PhiN + "\r\n");

    }

    /*
     Genarate required Primes.
     */
    void sieve(int n) {
        int i, j, k, l;

        for (i = 0; i <= n; i++) {
            prime[i] = 0;
        }

        prime[1] = 1;
        for (i = 4; i <= n; i += 2) {
            prime[i] = 1;
        }
        for (i = 3; i * i <= n; i += 2) {
            if (prime[i] == 0) {
                for (j = i * i; j <= n; j += 2 * i) {
                    prime[j] = 1;
                }
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new java.awt.Panel();
        label1 = new java.awt.Label();
        textField1 = new java.awt.TextField();
        button1 = new java.awt.Button();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        button3 = new java.awt.Button();
        button4 = new java.awt.Button();
        button5 = new java.awt.Button();
        textArea1 = new java.awt.TextArea();
        textArea2 = new java.awt.TextArea();
        button6 = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Encryption-Decryption");

        panel1.setBackground(new java.awt.Color(240, 240, 240));
        panel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label1.setText("File Path:");

        textField1.setPreferredSize(new java.awt.Dimension(60, 22));

        button1.setBackground(new java.awt.Color(220, 220, 220));
        button1.setLabel("Browse");
        button1.setPreferredSize(new java.awt.Dimension(57, 22));
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        label2.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        label2.setText("Input:");

        label3.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        label3.setText("Output:");

        button3.setBackground(new java.awt.Color(220, 220, 220));
        button3.setLabel("Decrypt");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        button4.setBackground(new java.awt.Color(220, 220, 220));
        button4.setLabel("Clear All");
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });

        button5.setBackground(new java.awt.Color(220, 220, 220));
        button5.setLabel("Encrypt");
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });

        textArea1.setName("asd"); // NOI18N

        button6.setBackground(new java.awt.Color(220, 220, 220));
        button6.setLabel("Save");
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22))))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textArea2, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(button5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                        .addComponent(button3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(22, 22, 22))
        );

        button3.getAccessibleContext().setAccessibleName("button4");
        button4.getAccessibleContext().setAccessibleName("button5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /*
    Browse and open a file.... 
    */
    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        final JFileChooser fc = new JFileChooser();

        int tc = fc.showOpenDialog(this);
        File selectedFile = fc.getSelectedFile();
        textField1.setText(selectedFile.getAbsolutePath());

        try {
            // Open an input stream
            Scanner reader = new Scanner(selectedFile);
            inputText = "";
            while (reader.hasNextLine()) {
                inputText += reader.nextLine();
                inputText += "\r\n";
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        textArea1.setText(inputText);

    }//GEN-LAST:event_button1ActionPerformed

    /*
     Encryption Part....................
     */

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button5ActionPerformed
        // TODO add your handling code here:
        inputText = textArea1.getText();
        msgText = inputText.split("\r\n");
        outputText = "";

        for (int ii = 0; ii < msgText.length; ii++) {

            String str = msgText[ii];
            int i, j;
            int n = str.length();
            if (n == 0) {
                continue;
            }


            /*
             Step 1: Initialize array to store all characters, numbers and symbols of the plain text.
             */
            String cipher = "";

            /*
             Step 2: Select the first character of the message string as the first character for the cipher text.
             */
            cipher += str.charAt(0);

            /*
             Step 3: For all primes greater than or equal to 2 and less than the length of the string,
             insert the characters that have multiples of the prime as index and have not been inserted
             before into the cipher text in the order that they appear in the message i.e. first insert all
             multiples of 2, then all multiples of 3 (ignoring 6 as it was already inserted while
             inserting multiples of 2), and so on.
             */
            int flg[] = new int[n + 10];

            for (i = 0; i <= n; i++) {
                flg[i] = -1;
            }

            for (int in = 2; in <= n; in++) {
                if (prime[in] != 0) {
                    continue;
                }
                for (j = in; j <= n; j += in) {
                    if (flg[j] == -1) {
                        cipher += str.charAt(j - 1);
                        flg[j] = 1;
                    }
                }
            }

            /*
             Step 4: Private Key p & q is generated by Fermat’s factorization method and d is
             generated by Euclid’s algorithm since d is the multiplicative inverse of Public Key (n, e)
             e.
             */
            //Already Done in "key" object. 
            /*
             Step 5: Convert all characters, numbers and symbols into its ASCII value.
             */
            int[] cipherAscii = new int[5010];

            for (i = 0; i < n; i++) {
                cipherAscii[i] = cipher.charAt(i);
            }

            /*
             Step 6: Add a private number to ASCII value as a private key into the ASCII valued text.
             Step 7: X mod 256=Y.
             */
            //For very Letter......
            for (i = 0; i < n; i++) {
                BigInteger temp1 = key.d.mod(BigInteger.valueOf(256));
                BigInteger temp2 = key.p.mod(BigInteger.valueOf(256));
                BigInteger temp3 = key.q.mod(BigInteger.valueOf(256));
                cipherAscii[i] += temp1.intValue() + temp2.intValue() + temp3.intValue();
                BigInteger Y = BigInteger.valueOf(cipherAscii[i] % 256);

                /*
                 Step 8: Convert Y valued into its binary number.
                 */
                String yy = Y.toString(2);
                yy = new StringBuffer(yy).reverse().toString();
                for (j = yy.length(); j < ModSize; j++) {
                    yy += "0";
                }
                yy = new StringBuffer(yy).reverse().toString();

                /*
                 Step 9: Take 2’s complement of binary valued text.
                 */
                yy = new StringBuffer(yy).reverse().toString();

                int flag = 0;
                for (j = 0; j < yy.length(); j++) {
                    if (yy.charAt(j) == '1') {
                        if (flag == 1) {
                            yy = yy.substring(0, j) + '0' + yy.substring(j + 1);
                        }

                        flag = 1;
                    } else if (flag == 1) {
                        yy = yy.substring(0, j) + '1' + yy.substring(j + 1);
                    }
                }

                yy = new StringBuffer(yy).reverse().toString();


                /*
                 Step 10: Take 1’s complement of 2’s complemented binary valued text.
                 */
                for (j = 0; j < yy.length(); j++) {
                    if (yy.charAt(j) == '1') {
                        yy = yy.substring(0, j) + '0' + yy.substring(j + 1);

                    } else {
                        yy = yy.substring(0, j) + '1' + yy.substring(j + 1);
                    }
                }

                /*
                 Step 11: Apply XOR operation of binary valued text with public key
                 */
                Y = new BigInteger(yy, 2);
                Y = Y.xor(key.n);
                Y = Y.xor(key.e);

                yy = Y.toString(2);

                for (j = yy.length(); j < ModSize || j < SIZE; j++) {
                    yy = "0" + yy;
                }

                //output store
                outputText += yy + "\r\n";

            }
            //Divide each line by "\r\n\r\n"
            outputText += "\r\n\r\n";
            //Show Output
            textArea2.setText(outputText);

        }
    }//GEN-LAST:event_button5ActionPerformed

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
        // TODO add your handling code here:
        inputText = "";
        outputText = "";
        textField1.setText("");
        textArea1.setText("");
        textArea2.setText("");
    }//GEN-LAST:event_button4ActionPerformed

    /*
     Decryption Starts Here......
     */
    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        // TODO add your handling code here:
        /*
         Step 1: Private Key p & q is generated by Fermat’s factorization method and d is
         generated by Euclid’s algorithm since d is the multiplicative inverse of Public Key (n, e) e.
         */
        //Done......

        inputText = textArea1.getText();
        msgText = inputText.split("\r\n\r\n\r\n");
        outputText = "";
        for (int ii = 0; ii < msgText.length; ii++) {
            /*
             Step 2: Initialize two dimensional array to store binary number and all cipher text.
             */

            String cipherText[];
            cipherText = msgText[ii].split("\r\n");
            String msg = "";

            for (int jj = 0; jj < cipherText.length; jj++) {

                String yy = cipherText[jj];
                int n = yy.length();
                if (n == 0) {
                    continue;
                }

                /*
                 Step 3: Perform XOR operation of binary valued text with public key.
                 */
                BigInteger Y;
                Y = new BigInteger(yy, 2);
                Y = Y.xor(key.n);
                Y = Y.xor(key.e);

                /*
                 Step 4: Takes 1’s complement of binary valued text.
                 */
                yy = Y.toString(2);

                yy = new StringBuffer(yy).reverse().toString();
                for (int j = yy.length(); j < ModSize; j++) {
                    yy += "0";
                }
                yy = new StringBuffer(yy).reverse().toString();

                for (int j = 0; j < yy.length(); j++) {
                    if (yy.charAt(j) == '1') {
                        yy = yy.substring(0, j) + '0' + yy.substring(j + 1);

                    } else {
                        yy = yy.substring(0, j) + '1' + yy.substring(j + 1);
                    }
                }

                /*
                 Step 5: Takes 2’s complement of 1’s complement binary valued text.
                 */
                yy = new StringBuffer(yy).reverse().toString();

                int flag = 0;
                for (int j = 0; j < yy.length(); j++) {
                    if (yy.charAt(j) == '1') {
                        if (flag == 1) {
                            yy = yy.substring(0, j) + '0' + yy.substring(j + 1);
                        }

                        flag = 1;
                    } else if (flag == 1) {
                        yy = yy.substring(0, j) + '1' + yy.substring(j + 1);
                    }
                }

                yy = new StringBuffer(yy).reverse().toString();

                /*
                 Step 6: Convert binary valued to its resultant ASCII values.
                 */
                Y = new BigInteger(yy, 2);

                /*
                 Step 7: Substract private number from resultant ASCII valued that is Y.
                 Step 8: Y mod 256= X.
                 */
                BigInteger temp1 = key.d.mod(BigInteger.valueOf(256));
                BigInteger temp2 = key.p.mod(BigInteger.valueOf(256));
                BigInteger temp3 = key.q.mod(BigInteger.valueOf(256));
                Y = Y.mod(BigInteger.valueOf(256));
                int X = (((Y.intValue() - temp1.intValue() - temp2.intValue() - temp3.intValue()) % 256) + 256) % 256;

                /*
                 Step 9: Convert X into its resultant characters, numbers and symbols.
                 */
                msg += (char) X;
            }

            /*
             Step 10: After step 9 cipher text again converted into plain text but it is shuffled by its
             index number. 
             Step 11: The first character of the text of will be the first character of the message string.
             */
            int m = msg.length();
            char[] str = new char[m + 10];

            str[0] = msg.charAt(0);

            /*
             Step 12: For all primes (index number) greater or equal to 2 and less than the size of the
             text, calculate n where, n=ceil (remaining length of cipher text/prime number)
             Step 13: For each prime take n consecutive characters from cipher text and place them in
             multiples of prime indices of the message string which have not been filled before.
            
             */
            int[] flag = new int[m + 10];
            for (int i = 0; i <= m; i++) {
                flag[i] = -1;
            }
            int in = 2;

            for (int i = 1; i < m;) {
                while (in <= m && prime[in] != 0) {
                    in++;
                }
                if (in > m) {
                    break;
                }

                int mul = 1;
                for (; true;) {
                    while (mul * in <= m && flag[mul * in] != -1) {
                        mul++;
                    }
                    if (mul * in > m) {
                        break;
                    }

                    str[(mul * in) - 1] = msg.charAt(i);
                    flag[mul * in] = 1;

                    mul++;
                    i++;
                }
                in++;
            }

            str[m] = '\r';
            str[m+1]='\n';
            msg = new String(str).substring(0, m);
            outputText += msg;
            outputText += "\r\n";
            textArea2.setText(outputText);
        }
        textArea2.setText(outputText);
    }//GEN-LAST:event_button3ActionPerformed

    
    /*
    Save the oput in a file.....
    */
    private void button6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button6ActionPerformed
        // TODO add your handling code here:
        JFileChooser c = new JFileChooser();
        int x = c.showSaveDialog(null);
        if (x == JFileChooser.APPROVE_OPTION) {
            
            try {
                String s = textArea2.getText();
                //if there is no extension
                File f = c.getSelectedFile();
                String filePath = f.getPath();
                if(!filePath.toLowerCase().endsWith(".txt"))
                {
                      f = new File(filePath + ".txt");
                }
                // create your buffered writer from f
                BufferedWriter file = new BufferedWriter(new FileWriter(f));
                file.write(s);
                file.close();

            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_button6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // new MainClass().setVisible(true);
                MainClass newJFrame = new MainClass();
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                newJFrame.setLocation(dim.width / 2 - newJFrame.getSize().width / 2, dim.height / 2 - newJFrame.getSize().height / 2);
                newJFrame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.Button button3;
    private java.awt.Button button4;
    private java.awt.Button button5;
    private java.awt.Button button6;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Panel panel1;
    private java.awt.TextArea textArea1;
    private java.awt.TextArea textArea2;
    private java.awt.TextField textField1;
    // End of variables declaration//GEN-END:variables
}
