import java.util.*;
import java.io.*;
public class decoder{
	private static final String BYTE_FORMAT = "%8s";
	static final String DECODEDFILENAME = "decoded.txt";
	private final Character CHAR_BLANK = ' ';
	private final Character CHAR_ZERO = '0';
	Map<Integer,String> decmap = new HashMap<Integer,String>();

	public String getEncodeBinMessage(String ENCFILENAME){
		StringBuilder sb = new StringBuilder();
		String decodeString;
		File file = new File(ENCFILENAME);
		byte byteRead;
		try {
			FileInputStream fis = new FileInputStream(file);
			while (fis.available()>0) {
				byteRead = (byte)fis.read();
		        decodeString = String.format(BYTE_FORMAT, Integer.toBinaryString(byteRead & 0xFF)).replace(CHAR_BLANK,CHAR_ZERO);
		        sb.append(decodeString);
		        }
		fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
      return sb.toString();
	}

	public Map<Integer,String> getDecMap(String codetbfile){
    try{
    	String Empty = "";
    	BufferedReader br = new BufferedReader(new FileReader(codetbfile));
    	String line = br.readLine();
		while (line != null && !(Empty.equals(line)))
		{
			String[] parts = line.split(" ", 2);
			if(parts.length >= 2)
			{
				String key = parts[0];
				String value = parts[1];
				decmap.put(Integer.parseInt(key), value);
				line = br.readLine();
			}
			else
			{
				System.out.println("Line doesn't contain HuffmanCode");
			}
		}
		br.close();
    }catch(Exception e){
    	e.printStackTrace();
    }
	return decmap;

  }

	public HeapNode getDecodeTree(String codetbfile){
		Map<Integer,String>decmap = new HashMap<Integer,String>();
        decmap = getDecMap(codetbfile);
		HeapNode root = new HeapNode();
		root.setData(-2);
		HeapNode temp = new HeapNode();
		int i = 0;
		for(int key: decmap.keySet())
		{
			int data = key;
			String HuffCode = decmap.get(key);
			temp = root;
		for(i = 0; i<HuffCode.length()-1;i++)
		{
			if(HuffCode.charAt(i) == '0')
			{
				if(temp.getLeft() == null)
				{
					HeapNode node = new HeapNode();
					node.setData(-1);
					temp.setLeft(node);
					temp = temp.getLeft();
				}
				else
				{
					temp = temp.getLeft();
				}

			}
			else if(HuffCode.charAt(i) == '1')
			{
				if(temp.getRight() == null)
				{
					HeapNode node = new HeapNode();
					node.setData(-1);
					temp.setRight(node);
					temp = temp.getRight();
				}
				else
				{
					temp = temp.getRight();
				}

			}
			else
			{
				System.out.println("Invalid Huffman Code");

			}
		}

		if(HuffCode.charAt(i) == '0')
		{
			HeapNode node = new HeapNode();
			node.setData(data);
            temp.setLeft(node);
		}
		else if(HuffCode.charAt(i) == '1')
		{
			HeapNode node = new HeapNode();
			node.setData(data);
            temp.setRight(node);
		}
		else
		{
			System.out.println("Invalid Huffman Code");
		}

	}

		return root;
  }

	public void getDecodedMessage(String encoding,String codetbfile){
        HeapNode root = getDecodeTree(codetbfile);
        HeapNode temp = new HeapNode();
        temp = root;
        try {
        	File file = new File(DECODEDFILENAME);
	        file.createNewFile();
			BufferedWriter writerd = new BufferedWriter(new FileWriter(file));

        for(int i = 0;i<encoding.length();i++){

            if(encoding.charAt(i) == '0'){
                temp = temp.getLeft();

                if(temp.getLeft() == null && temp.getRight() == null){
                    Integer outputl = temp.getData();
                    writerd.write(outputl + "\n");
                    temp = root;
                }
            }
            else if((encoding.charAt(i) == '1'))
            {
                temp = temp.getRight();
                if(temp.getLeft() == null && temp.getRight() == null){
                    Integer outputr = temp.getData();
                    writerd.write(outputr + "\n");
                    temp = root;
                }

            }
            else
            {
            	System.out.println("Invalid character");
            }
        }
        writerd.close();
     } catch (IOException e) {
		    e.getMessage();
		}
    }

	public static void main(String args[]){
		//long start4 = System.currentTimeMillis();
		String encoded_file = args[0];
		String code_tablefile = args[1];
		decoder decoder = new decoder();
        String encodeMessage = decoder.getEncodeBinMessage(encoded_file);
        decoder.getDecodedMessage(encodeMessage,code_tablefile);
        //long end4 = System.currentTimeMillis();
        //System.out.println("Finished in " + (float)(end4-start4)/1000 + "seconds");
		System.out.println("Decoded!!");

	}

}


