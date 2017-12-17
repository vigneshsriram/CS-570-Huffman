/*
 * Group_A1_Omkar_Vignesh_Sharan
 * Omkar Ahir: 10429867
 * Vignesh Sriram: 10430312
 * Sharan Jotwani: 10429842
*/

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
 

abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency; // the frequency of this tree
    public HuffmanTree(int freq) { frequency = freq; }
 
    // compares on the frequency
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}
 
class HuffmanLeaf extends HuffmanTree {
    public final char value; // the character this leaf represents
 
    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}
 
class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right;  //subtrees
 
    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}
 
public class Huffman {
	static List<Character> listval = new ArrayList<Character>();
    static List<Float> listfreq = new ArrayList<Float>();
    static List<String> listpref = new ArrayList<String>();
   
    
    public static HuffmanTree buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));
 
        assert trees.size() > 0;
        
        while (trees.size() > 1) {
      
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
 
            
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }
 
  
    
    public static void codes(HuffmanTree tree, StringBuffer prefix, int num) {
        assert tree != null;
       
      
       
        
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            float perc = (float)(leaf.frequency*100)/(float)num;
            
            listval.add(leaf.value);
            listfreq.add(perc);
            listpref.add(prefix.toString());
         
            
            
 
        } 
          else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
            
            // traverse left
            prefix.append('0');
            codes(node.left, prefix, num);
            prefix.deleteCharAt(prefix.length()-1);
            
            // traverse right
            prefix.append('1');
            codes(node.right, prefix, num);
            prefix.deleteCharAt(prefix.length()-1);
            
        }
        
    }
    
    public static void printarr(List<Character> listval2, List<Float> listfreq2, List<String> listpref2, String test)
    {
    	
    	char []arrv = new char[listval2.size()];
        float []arrf = new float[listfreq2.size()];
        String []arrp = new String[listpref2.size()];
        float tempf=0;
       
        char tempv;
        int tbits=0;
        String tempp="";
        
        for (int k = 0; k < listval2.size(); k++) {
			arrv[k] = listval2.get(k);
			arrf[k] = listfreq2.get(k);
			arrp[k] = listpref2.get(k);
		}
        
        
        //Sorting based on frequency
        for(int i = 0; i<arrf.length; i++)
    	{
    		for(int j = 0; j<arrf.length-1; j++)
    		{
    			if(arrf[j]<arrf[j+1])
    			{
    				tempf = arrf[j];
    				arrf[j] = arrf[j+1];
    				arrf[j+1]=tempf;
    				
    				tempp = arrp[j];
    				arrp[j] = arrp[j+1];
    				arrp[j+1]=tempp;
    				
    				tempv = arrv[j];
    				arrv[j] = arrv[j+1];
    				arrv[j+1]=tempv;
    				
    				
    			}
    		}
    	}
        //Storing output in another file
        FileWriter fWriter = null;
        BufferedWriter writer = null;
        try {
        	String pathop="";
        	String str1;
        	Scanner sc1 = new Scanner(System.in);
        	System.out.println("Enter the path and filename for output:");
        	pathop = sc1.nextLine();
        	if(pathop.isEmpty())
        	{
        		str1 = "outfile.dat";
        		System.out.println("Default output file is outfile.dat stored in current working directory");
        	}
        	else
        	{
        		str1 = pathop;
        	}
          fWriter = new FileWriter(str1);
          writer = new BufferedWriter(fWriter);
          writer.write("SYMBOL\tWEIGHT");
          writer.newLine();
          
          
       
       // System.out.println("SYMBOL\tWEIGHT");
        for(int l = 0; l<arrf.length; l++)
    	{
        	writer.write(arrv[l] + "\t" + arrf[l] + "%");
        	writer.newLine();
    		//System.out.println(arrv[l] + "\t" + arrf[l] + "%");	
    	}
        writer.newLine();
       // System.out.println("\n");
        //System.out.println("SYMBOL\tHUFFMAN CODES");
       // writer.newLine();
        writer.write("SYMBOL\tHUFFMAN CODES");
        writer.newLine();
        
        for(int y = 0; y<arrf.length; y++)
    	{	
        	 writer.write(arrv[y] + "\t" + arrp[y]);
        	 writer.newLine();
    		//System.out.println(arrv[y] + "\t" + arrp[y]);
    	}
        
        writer.newLine();
        
        //Calculating Total bits
        for(int u = 0; u < arrv.length; u++)
        {
        	for(int q = 0; q<test.length(); q++)
        	{	
        		if(arrv[u] == test.charAt(q))
        		{
        			tbits = tbits + arrp[u].length();
        		}
        	}
        }
        System.out.println("\n");
        writer.write("Total Bits: "+tbits);
        //System.out.println("Total Bits: "+tbits);
        writer.close();
        System.err.println("SUCCESS");
      } catch (Exception e) {
          System.out.println("Wrong path for output file!");
      }
        
        
    }
    
    //Reading from file.
     static String readFile(String input) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(input));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
    
    public static void main(String[] args) throws IOException {
    	
    	try 
    	{
        
    	String pathip = "";
    	
    	String str = "";
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Enter the path and filename for input:");
    	pathip = sc.nextLine();
    	if(pathip.isEmpty())
    	{
    		str = "infile.dat";
    		System.out.println("Default input file taken is infile.dat from current working directory\n");
    	}
    	else
    	{
    		str = pathip;
    	}
    	
    	
    	
    	
    	String test1 = readFile(str);
    	
        String test="";
        
       
      
        int[] charFreqs = new int[256];
        
        int num = 0;
        test = test1.toLowerCase();
        
     // read each character and record the frequencies
        for (char c : test.toCharArray())
        {
        	if(Character.isLetter(c) || Character.isDigit(c))
        	{
        		num++;
        		charFreqs[c]++;
        	}
        }
        
       
        // build tree
        HuffmanTree tree = buildTree(charFreqs);
       
        // print out results
        
        codes(tree, new StringBuffer(), num);
        
        printarr(listval, listfreq, listpref, test);
        
    }
    	catch(Exception e)
    	{
    		System.out.println("File name/path for input file not found.");
    	}
    }
}