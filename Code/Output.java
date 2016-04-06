import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;

public class Output extends javax.swing.JFrame {

    static int count=1;
    static int errorCounter=0;
    static String tmp="" , error="", nik="";
    static File file = new File("Output.txt");

public Output() {
        initComponents();
        setTitle("TOKENS");
        setSize(800,650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        
        
    }

public void display(String filename) {
    String s = "",att_parent="--",check="";
    Token t;
    int line_counter=0;
    try { 
        BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
	BufferedReader in_ = new BufferedReader(new FileReader(new File(filename)));
        
        if (!file.exists()) {
            file.createNewFile();
	}
        
        else if (file.exists()) {
            file.delete();
             tmp="" ;
             error="";
             count=1;
             errorCounter=0;
            file.createNewFile();
        }
        
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
        
                
		if((check = in_.readLine()) != null) {
			tmp = "{TOKEN NO. , TOKEN , NAME , TYPE/VALUE , PARENT TAG ,LINE NO. }" + "\n";
			tmp += "========================================================" + "\n" ;
		}
		else {tmp += "-------Empty file---------\n" ;error="None";}
        while((s = in.readLine()) != null) {
            ArrayList<Token> a = getToken(s);
			line_counter+=1;
			generateTokens(a,line_counter);
        }   
			bw.write(tmp);
                        bw.write("\nError Messages:-\n");
                        bw.write(error);
                        bw.write("\n^ "+errorCounter+" Errors");
                        bw.close();
        try 
            {    
                 FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    jTextArea1.read( br, null );
                    br.close();
                    jTextArea1.requestFocus();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Lexer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Lexer.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
	catch(Exception e) {
            //e.printStackTrace();
		System.out.print(e);
    }
    
        
}

public static void generateTokens(ArrayList<Token> a,int line_counter ) {
	String att_parent="--";
	MyTags T=new MyTags();
	   for(int i = 0; i < a.size(); i++) {
				if(a.get(i).getToken_type()== "TAG" && a.get(i).getTag_type_or_attr_value()== "OT") {
					att_parent=a.get(i).getToken_name();
				}
				
				if	(a.get(i).getToken_type()== "ATT" ) {
					if (T.findAttr(a.get(i).getToken_name()) && a.get(i).getTag_type_or_attr_value()!= "null" ) {
						tmp += "{ " +count+ " , " + a.get(i).getToken_type() +" , "+a.get(i).getToken_name() + " , "+a.get(i).getTag_type_or_attr_value() + " , " +att_parent+ " , "+line_counter+ " } " + "\n";	
                                                tmp += "--------------------------------------------------------------------------------" + "\n" ;
                                                count++;
					}
                                        
                                        else if (a.get(i).getTag_type_or_attr_value()== "null") {
                                            tmp += "{ " +count+ " , " + a.get(i).getToken_type() +" , "+a.get(i).getToken_name() + " , "+a.get(i).getTag_type_or_attr_value() + " , " +att_parent+ " , "+line_counter+ " } " + "\n";	
                                            tmp += "--------------------------------------------------------------------------------" + "\n" ;
                                            count++;
                                            error += " @line "+line_counter+"  : Token '"+a.get(i).getToken_name() +"' Value Can't be 'null'." + "\n";
                                            errorCounter ++;
                                        }
                                        
					else {
                                            error += " @line "+line_counter+"  : '"+a.get(i).getToken_name() +"' is not allowed as Attribute of '"+att_parent+"' TAG." + "\n";
                                            errorCounter ++;
                                        }
                                }
				else if(a.get(i).getToken_type()== "TAG") {
					if (T.findTag(a.get(i).getToken_name())) {
					tmp += "{ " +count+ " , " + a.get(i).getToken_type() +" , "+a.get(i).getToken_name() + " , "+a.get(i).getTag_type_or_attr_value() + " , "+line_counter+ " } " + "\n";
					tmp += "--------------------------------------------------------------------------------" + "\n" ;
                                        count++;
					}
                                        else {
                                            error += " @line "+line_counter+"  : '"+a.get(i).getToken_name() +"' is an UnExpected Token." + "\n";
                                            if (T.CorrectIt(a.get(i).getToken_name()) != "") {
                                                error += "Corrected Values = " +T.CorrectIt(a.get(i).getToken_name()) + "\n";
                                            }
                                            errorCounter ++;
                                        }
				}
			
		}
}

public static ArrayList<Token> getToken(String input) {
	
    String lexeme = "" , value = ""; 
    Token t = null;
    ArrayList<Token> a = new ArrayList<Token>();
    int d=0;
    int j=0,k=0;
    for (int i = 0; i < input.length(); i++) {
        if (input.charAt(i) == '<' && input.charAt(input.length()-1) != '<' && input.charAt(input.length()-1) != '/') {
            i++;
            if (isALetter(input.charAt(i))) {
                lexeme = "" + input.charAt(i);
                i++;
                while (input.charAt(i) != '>' && input.charAt(i) != ' ') {
                    lexeme += input.charAt(i);
                    i++;
                    j=i;	
                }
                                        
                while (input.charAt(i) != '>') {
                    i++;
                    k=i;
                }
                                    
                t = new Token("TAG", lexeme,"OT");
                a.add(t);
                for (int z = j; z < k; z++) {
                    while (input.charAt(z) == ' ') {
                        z++;
                        if (isALetter(input.charAt(z))) {
                            lexeme = "" + input.charAt(z);
                            z++;
                            while (input.charAt(z) != '=' && input.charAt(z) != ' ') {
                                lexeme += input.charAt(z);
                                z++;
                            }
                                                            
                            while (input.charAt(z) != '"') {
                                z++;
                            }
                                
							if (input.charAt(z) == '"') {
                                z++;
                                if(isALetter(input.charAt(z)) || isAtt_value(input.charAt(z)) ) {
                                    value = "" + input.charAt(z);
                                    z++;
                                    while( (isALetter(input.charAt(z)) || isAtt_value(input.charAt(z))) && input.charAt(z)!= '"') {
                                        value += input.charAt(z);
                                        z++;
                                    }
                                }
                                                                    
                                else if (input.charAt(z) == '"') {
                                        value="null";
                                }   

                            }
                            t = new Token("ATT", lexeme,value);
                            a.add(t);
                        }
                    }
                }  
                            
            }

            if (input.charAt(i) == '/') {
                i++;
                if (isALetter(input.charAt(i))) {
                    lexeme = "" + input.charAt(i);
                    i++;
                    while (input.charAt(i) != '>' && input.charAt(i) != ' ') {
                        lexeme += input.charAt(i);
                        i++;
                                                    
                    }
                                        
                    while (input.charAt(i) != '>') {
                        i++;
                    }
                    t = new Token("TAG", lexeme,"CT");
                    a.add(t);
                                                    
                }
            }
        }
		/* else if (isALetter(input.charAt(input.length()-1))) {
			nik="";
		} */
		
		/* else if (input.charAt(input.length()-1) != '<' && input.charAt(input.length()-1) != '/'){
			error += "Syntax Error" + "\n";
			errorCounter ++;
		} */
    }
	return a;
}

public static boolean isALetter(char inputChar) {
    Boolean itIsALetter = false;
    if("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM".indexOf(inputChar) != -1) {
        itIsALetter = true;
    }
    return itIsALetter;
}

public static boolean isANumber(char inputChar) {
    Boolean itIsANumber = false;
    if("1234567890".indexOf(inputChar) != -1) {
        itIsANumber = true;
    }
    return itIsANumber;
}

public static boolean isAtt_value(char inputChar) {
    Boolean itIsAtt_value = false;
    if("1234567890".indexOf(inputChar) != -1 || "_:#-.!/ ".indexOf(inputChar) != -1) {
        itIsAtt_value = true;
    }
    return itIsAtt_value;
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(204, 204, 255));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Lucida Bright", 1, 16)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Output().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
