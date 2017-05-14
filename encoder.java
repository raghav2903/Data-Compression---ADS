import java.util.*;
import java.io.*;
public class encoder {

	static int freq_table[] = new int[1000000];
	static final String CODEFILENAME = "code_table.txt";
	static final String INPFILENAME = "sample_input_large.txt";
    static final String ENCFILENAME = "encoded.bin";


    public void readFromFile(String inputfile){
		String Empty = "";
		File file = new File(inputfile);

		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while (line != null && !(Empty.equals(line))) {
		    int data = Integer.parseInt(line);
			freq_table[data] += 1;
			line = br.readLine();
				   }
			br.close();

		}catch(Exception e){
             e.printStackTrace();
		}
	}

	public void writeCodeTable(Map<Integer,String>hmap){
		try{
			File file = new File(CODEFILENAME);
	        file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    for(Integer key: hmap.keySet()){
				writer.write(key + " " + hmap.get(key) + "\n");
			}
		    writer.close();
		} catch (IOException e) {
		    e.getMessage();
		}

	}

	public void encoderbin(Map<Integer,String>hmap, String inputfile){
		String lastLine = "";
		StringBuilder encvalue = new StringBuilder();
		FileOutputStream fos;
		BufferedReader br;
		try {
			fos = new FileOutputStream(new File(ENCFILENAME));
			br = new BufferedReader(new FileReader(INPFILENAME));

			String line = br.readLine();
			while( line != null && !(line.equals(lastLine)))
			{
				encvalue.append(hmap.get(Integer.parseInt(line)));
				line = br.readLine();
			}

			for (int i = 0; i < encvalue.length(); i += 8) {
		        String byteString = encvalue.substring(i, i + 8);
		        int parsedByte = 0xFF & Integer.parseInt(byteString, 2);
		        fos.write(parsedByte);
		    }
			fos.flush();
			fos.close();
			br.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]){

        String input_filename = args[0];
		encoder encoderob = new encoder();
		encoderob.readFromFile(input_filename);


		/*System.out.println(" <<<<<<<BINARY HEAP IMPLEMENTATION>>>>>>>");
		long start1 = System.currentTimeMillis();
		BinaryHeap binaryheap = new BinaryHeap();
		Map<Integer,String> binHuffCode = new HashMap<Integer,String>();
		for(int i = 0; i<= freq_table.length-1 ; i++)
		{
			if(freq_table[i] != 0)
			{
				HeapNode heapnode = new HeapNode();
				heapnode.set(i, freq_table[i]);
				binaryheap.insert(heapnode);
			}
		}
		System.out.println("Binary Heap Created");
		HuffmanTree binhuffmantree = new HuffmanTree();
		HeapNode heapnode =  binhuffmantree.buildHuffmanTreeUsingBinHeaps(binaryheap);
		System.out.println("Binary Heap Huffman Tree Created");
		binHuffCode =  binhuffmantree.getBinHuffCode(heapnode, new StringBuilder());
		System.out.println("Bin Code Hash Map Table created");
		encoderob.writeCodeTable(binHuffCode);
		encoderob.encoderbin(binHuffCode,input_filename);
        System.out.println("Encoded!");
        long end1 = System.currentTimeMillis();
        System.out.println((float) (end1-start1)/1000+ " seconds");

		System.out.println("");


		System.out.println(" <<<<<<<PAIRING HEAP IMPLEMENTATION>>>>>>>");
		long start2 = System.currentTimeMillis();
        PairHeap pairheap = new PairHeap();
		Map<Integer,String> pairHuffCode = new HashMap<Integer,String>();
		for(int i = 0; i<= freq_table.length-1 ; i++)
		{
			if(freq_table[i] != 0)
			{
				PairNode pairnode = new PairNode();
				pairnode.set(i, freq_table[i]);
				pairheap.insert(pairnode);
			}
		}
		HuffmanTree pairhuffmantree = new HuffmanTree();
		PairNode pairnode =  pairhuffmantree.buildHuffmanTreeUsingPairHeaps(pairheap);
		System.out.println("Pairing Heap Huffman Tree Created");
		pairHuffCode =  pairhuffmantree.getPairHuffCode(pairnode, new StringBuilder());
		System.out.println("Pair Code Hash Map Table created");
		encoderob.writeCodeTable(pairHuffCode);
		encoderob.encoderbin(pairHuffCode,input_filename);
		System.out.println("Encoded!");
	    long end2 = System.currentTimeMillis();
	    System.out.println((float) (end2-start2)/1000+ " seconds");



		System.out.println("");*/

		//System.out.println(" <<<<<<<FOUR-ARY HEAP IMPLEMENTATION>>>>>>>");
		//long start3 = System.currentTimeMillis();
		FourAryHeap fouraryheap = new FourAryHeap();
		Map<Integer,String> fourHuffCode = new HashMap<Integer,String>();
		for(int i = 0; i<= freq_table.length-1 ; i++)
		{
			if(freq_table[i] != 0)
			{
				FourNode fournode = new FourNode();
				fournode.set(i, freq_table[i]);
				fouraryheap.insert(fournode);
			}
		}
		//System.out.println("FourAry Heap Created");
		HuffmanTree fourhuffmantree = new HuffmanTree();
		FourNode fournode =  fourhuffmantree.buildHuffmanTreeUsingFourHeaps(fouraryheap);
		//System.out.println("Four-ary Heap Huffman Tree Created");
		fourHuffCode =  fourhuffmantree.getFourHuffCode(fournode, new StringBuilder());
		//System.out.println("Four-ary Code Hash Map Table created");
        encoderob.writeCodeTable(fourHuffCode);
        encoderob.encoderbin(fourHuffCode,input_filename);
        System.out.println("Encoded!");
        //long end3 = System.currentTimeMillis();
        //System.out.println((float)(end3-start3)/1000 + "seconds");
	}

}

	class HeapNode{
		int frequency;
		HeapNode left;
		HeapNode right;
		int data;

		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}
		public int getFrequency() {
			return frequency;
		}
		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}
		public HeapNode getLeft() {
			return this.left;
		}
		public void setLeft(HeapNode left) {
			this.left = left;
		}
		public HeapNode getRight() {
			return this.right;
		}
		public void setRight(HeapNode right) {
			this.right = right;
		}
		public boolean isLeaf(){
			return this.getLeft() == null && this.getRight() == null;
		}
		public void set(int data, int frequency){
			this.data = data;
			this.frequency = frequency;
		}

		//Delete  this. Only for testing purposes
		public String toString() {
	        return Integer.toString(this.data) + " , " + Integer.toString(this.frequency) + " ; ";
	    }

		public void inorder(HeapNode r) {
			if (r != null) {
				inorder(r.getLeft());
				System.out.print("[" + r.data + "]," );
				inorder(r.getRight());
			}
		}
	}

	class PairNode {
		int data;
		int frequency;
		PairNode child;
		PairNode leftSibling;
		PairNode rightSibling;
		PairNode left;
		PairNode right;
		PairHeap node;

		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}
		public int getFrequency() {
			return frequency;
		}
		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}
		public PairNode getChild() {
			return child;
		}
		public void setChild(PairNode child) {
			this.child = child;
		}
		public PairNode getLeftSibling() {
			return leftSibling;
		}
		public void setLeftSibling(PairNode leftSibling) {
			this.leftSibling = leftSibling;
		}
		public PairNode getRightSibling() {
			return rightSibling;
		}
		public void setRightSibling(PairNode rightSibling) {
			this.rightSibling = rightSibling;
		}
		public PairNode getLeft() {
			return left;
		}
		public void setLeft(PairNode left) {
			this.left = left;
		}
		public PairNode getRight() {
			return right;
		}
		public void setRight(PairNode right) {
			this.right = right;
		}
		public boolean isLeaf(){
			return this.getLeft() == null && this.getRight() == null;
		}
		public void setSib(){
			this.leftSibling = null;
			this.rightSibling = null;
		}
		public void set(int data, int frequency){
			this.data = data;
			this.frequency = frequency;
			this.child = null;
			this.leftSibling = null;
			this.rightSibling = null;
		}

		//Only For Testing Purposes
		public String toString() {
	        return Integer.toString(this.data) + " , " + Integer.toString(this.frequency) + " ; ";
	    }

		public void inorder(PairNode r) {
			if (r != null) {
				inorder(r.getLeft());
				System.out.print(r.frequency + "[" + r.data + "]," );
				inorder(r.getRight());
			}
		}

	}

	class FourNode {
		int frequency;
		FourNode left;
		FourNode right;
		int data;

		public int getFrequency() {
			return frequency;
		}
		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}
		public FourNode getLeft() {
			return left;
		}
		public void setLeft(FourNode left) {
			this.left = left;
		}
		public FourNode getRight() {
			return right;
		}
		public void setRight(FourNode right) {
			this.right = right;
		}
		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}
		public void set(int data, int frequency){
			this.data = data;
			this.frequency = frequency;
		}
		public boolean isLeaf(){
			return this.getLeft() == null && this.getRight() == null;
		}

	}

	class BinaryHeap extends HeapNode{
		HeapNode HNode = new HeapNode();
	    private final int capacity = 10;
	    int hsize = 0;
	    HeapNode binarr[] = new HeapNode[capacity];

	    public BinaryHeap() {
		}

		public boolean IsEmpty() {
			if(hsize == 0)
				return true;
			else
				return false;
		}

		public int size() {
			return hsize;
		}

		public HeapNode findMin() throws NullPointerException{
			if(this.IsEmpty())
				throw new IllegalStateException("The Binary Heap is Empty");
			else
				return binarr[1];
		}

		public void insert(HeapNode x) {
			if(hsize >= binarr.length-1){
				binarr = this.resize();
			}

			hsize++;
			int index = hsize;
			binarr[index] = x;

			moveUp();

		}

		public HeapNode delMin() {
			HeapNode min = findMin();

			binarr[1] = binarr[hsize];
			binarr[hsize] = null;
			hsize--;

			moveDown();

			return min;
		}

		public void moveUp(){
			int index = this.hsize;

			while(hasParent(index) && (parentElem(index).frequency >= binarr[index].frequency))
			{
				swap(parentIndex(index), index);
	            index = parentIndex(index);
			}

		}

		public void moveDown(){
			int index = 1;

			while(hasLeftChild(index)){
				int newmin = leftChildIndex(index);

				if(hasRightChild(index) && binarr[rightChildIndex(index)].frequency < binarr[leftChildIndex(index)].frequency){
					newmin = rightChildIndex(index);
				}

				if(binarr[newmin].frequency < binarr[index].frequency){
					swap(index,newmin);
				}
				else{
					break;
				}

				index = newmin;
			}

		}


		public HeapNode[] resize(){
	          return Arrays.copyOf(binarr, (binarr.length*2));
		}

		public void swap(int index1, int index2){
			HeapNode temp = binarr[index1];
			binarr[index1] = binarr[index2];
			binarr[index2] = temp;
		}

		public boolean hasParent(int i){
			if(i>1)
				return true;
			else
				return false;
		}

		public int leftChildIndex(int i){
			return i*2;
		}

		public int rightChildIndex(int i){
			return i*2 + 1;
		}

		public boolean hasLeftChild(int i){
			if(!(leftChildIndex(i)<=hsize))
				return false;
			else
				return true;
		}

		public boolean hasRightChild(int i){
			if(!((rightChildIndex(i))<=hsize))
				return false;
			else
				return true;
		}

		public int parentIndex(int i){
			return i/2;
		}

		public HeapNode parentElem(int i){
			return binarr[parentIndex(i)];
		}

		public String toString() {
	        return Arrays.toString(binarr);
	    }
	}

	class PairHeap extends PairNode{
		int psize;
		PairNode root;
		PairNode pairarr[];
		private static final int capacity = 10;

		public PairHeap() {
	         root = null;
	         pairarr = new PairNode[capacity];
	         psize = 0;
		}

		public boolean IsEmpty() {
			if(root == null)
				return true;
			else
				return false;
		}

		public int size() {
			return psize;
		}

		public PairNode findMin() {
			if(IsEmpty())
				throw new IllegalStateException("The Pairing Heap is Empty");
			else
				return root;
		}

		public void insert(PairNode x) {
			PairNode newNode = x;
			psize++;

			if(root == null)
				root = newNode;
			else
				root = compareAndLink(root,newNode);
		}

		public PairNode delMin() {

				PairNode x = findMin();

				if(root.child == null){
					root = null;
				}
				else{
					root = combineSiblings(root.child);
				}
			psize--;

			return x;
		}

		public PairNode compareAndLink(PairNode firstNode, PairNode secondNode){
			if(secondNode == null){
				return firstNode;
			}
			if(secondNode.frequency <= firstNode.frequency){
	               secondNode.leftSibling = firstNode.leftSibling;
	               firstNode.leftSibling = secondNode;
	               firstNode.rightSibling = secondNode.child;
	               if(firstNode.rightSibling != null){
	                       firstNode.rightSibling.leftSibling = firstNode;
	               }
	               secondNode.child = firstNode;
	               return secondNode;
			}
			else{
				secondNode.leftSibling = firstNode;
				firstNode.rightSibling = secondNode.rightSibling;
				if(firstNode.rightSibling != null){
					firstNode.rightSibling.leftSibling = firstNode;
				}
				secondNode.rightSibling = firstNode.child;
				if(secondNode.rightSibling != null){
					secondNode.rightSibling.leftSibling = secondNode;
				}
				firstNode.child = secondNode;
	            return firstNode;
			}

		}

		public PairNode[] resize(PairNode[] arr, int index){
			if(index == arr.length){
			return Arrays.copyOf(arr, (index*2));
			}
			else{
	        return arr;
			}
		}

		public PairNode combineSiblings(PairNode firstSibling){
			if(firstSibling.rightSibling == null)
				return firstSibling;
			int numSib = 0;
			for(; firstSibling != null; numSib++){
	              pairarr = resize(pairarr,numSib);
	              pairarr[numSib] = firstSibling;
	              firstSibling.leftSibling.rightSibling = null;
	              firstSibling = firstSibling.rightSibling;
			}
			pairarr = resize(pairarr,numSib);
			pairarr[numSib] = null;

			int i = 0;
			for(; i+1 < numSib; i+=2)
			{
				pairarr[i] = compareAndLink(pairarr[i],pairarr[i+1]);
			}

			int j = i-2;
			if(j == numSib - 3)
				pairarr[j] = compareAndLink(pairarr[j], pairarr[j+2]);

			 for( ; j >= 2; j -= 2 )
		            pairarr[ j - 2 ] = compareAndLink( pairarr[ j - 2 ], pairarr[ j ] );

		        return pairarr[ 0 ];

		}

		// Only for testing purposes
		public void inorder() {
			inorder(root);
		}

		public void inorder(PairNode r) {
			if (r != null) {
				inorder(r.child);
				System.out.print(r.getFrequency() + "," + r.getData() + ";");
				inorder(r.rightSibling);
			}
		}
	}


	class FourAryHeap extends FourNode{
		FourNode FNode = new FourNode();
	    private static final int capacity = 10;
	    int fsize = 0;
	    FourNode fourarr[] = new FourNode[capacity];

	    public FourAryHeap() {
		}

		public boolean IsEmpty() {
			if(fsize == 0)
				return true;
			else
				return false;
		}

		public int size() {
			return fsize;
		}

		public FourNode findMin() throws NullPointerException{
			if(this.IsEmpty())
				throw new IllegalStateException("The Four-ary Heap is Empty");
			else
				return fourarr[1];
		}

		public void insert(FourNode x) {
			if(fsize >= fourarr.length-1){
				fourarr = this.resize();
			}

			fsize++;
			int index = fsize;
			fourarr[index] = x;

			moveUp();

		}

		public FourNode delMin() {
			FourNode min = findMin();

			fourarr[1] = fourarr[fsize];
			fourarr[fsize] = null;
			fsize--;

			moveDown();

			return min;
		}

		public void moveUp(){
			int index = this.fsize;

			while(hasParent(index) && (parentElem(index).frequency >= fourarr[index].frequency))
			{
				swap(index,parentIndex(index));
	            index = parentIndex(index);
			}

		}

		public void moveDown(){
			int index = 1;

			while(hasFirstChild(index)){
				int minIndex = firstChildIndex(index);
				int maxChildIndex = childNum(index);
				int newmin = fetchMin(minIndex, maxChildIndex);

				if(fourarr[newmin].frequency <= fourarr[index].frequency){
					swap(index,newmin);
				}
				else{
					break;
				}

				index = newmin;
			}

		}

	   public int childNum(int i){

			int maxChildIndex = (4*i+1);
			if (fsize < maxChildIndex)
				maxChildIndex = fsize;

			return maxChildIndex;
		}

		public int fetchMin(int firstIndex, int lastIndex){

			int minChildVal = fourarr[firstIndex].getFrequency();
			int minIndex = firstIndex;
			for(int i=firstIndex; i<= lastIndex; i++){
				if(fourarr[i].getFrequency() < minChildVal){
					minChildVal = fourarr[i].getFrequency();
					minIndex = i;
				}
			}

			return minIndex;
		}


		public FourNode[] resize(){
	          return Arrays.copyOf(fourarr, (fourarr.length*2));
		}

		public void swap(int index1, int index2){
			FourNode temp = fourarr[index1];
			fourarr[index1] = fourarr[index2];
			fourarr[index2] = temp;
		}

		public boolean hasParent(int i){
			if(i>1)
				return true;
			else
				return false;
		}

		public int firstChildIndex(int i){
			return (4*(i-1)+2);
		}

		public boolean hasFirstChild(int i){
			if(!((4*(i-1)+2)<=fsize))
				return false;
			else
				return true;
		}

		public boolean hasSecondChild(int i){
			if(!((4*(i-1)+3)<=fsize))
				return false;
			else
				return true;
		}

		public boolean hasThirdChild(int i){
			if(!((4*(i-1)+4)<=fsize))
				return false;
			else
				return true;
		}

		public boolean hasFourthChild(int i){
			if(!((4*(i-1)+5)<=fsize))
				return false;
			else
				return true;
		}

		public int parentIndex(int i){
			return ((int)Math.ceil((double)(i-1)/4.0));
		}

		public FourNode parentElem(int i){
			return fourarr[parentIndex(i)];
		}

	}

	class HuffmanTree{
		Map<Integer, String>BinHuffCode = new HashMap<Integer,String>();
		Map<Integer, String>PairHuffCode = new HashMap<Integer,String>();
		Map<Integer, String>FourHuffCode = new HashMap<Integer,String>();
	    StringBuilder s = new StringBuilder();
		public HeapNode buildHuffmanTreeUsingBinHeaps(BinaryHeap BinNode){

			while(BinNode.hsize > 1)
			{
				HeapNode node1 =  BinNode.delMin();
				HeapNode node2 =  BinNode.delMin();
				HeapNode node3 =  new HeapNode();
				node3.set(-1, node1.getFrequency()+node2.getFrequency());
				node3.setLeft(node1);
				node3.setRight(node2);
				BinNode.insert(node3);
			}

			return BinNode.delMin();
		}

		public PairNode buildHuffmanTreeUsingPairHeaps(PairHeap PairHeapNode){

			while(PairHeapNode.psize > 1)
			{
				PairNode node1 =  PairHeapNode.delMin();
				PairNode node2 =  PairHeapNode.delMin();
				PairNode node3 =  new PairNode();
				node3.set(-1, node1.getFrequency()+node2.getFrequency());
				node3.setLeft(node1);
				node3.setRight(node2);
				node1.setSib();
				node2.setSib();
				node3.setSib();
				PairHeapNode.insert(node3);
			}

			return PairHeapNode.delMin();
		}

		public FourNode buildHuffmanTreeUsingFourHeaps(FourAryHeap FrNode){

			while(FrNode.fsize > 1)
			{
				FourNode node1 =  FrNode.delMin();
				FourNode node2 =  FrNode.delMin();
				FourNode node3 =  new FourNode();
				node3.set(-1, node1.getFrequency()+node2.getFrequency());
				node3.setLeft(node1);
				node3.setRight(node2);
				FrNode.insert(node3);
			}

			return FrNode.delMin();
		}

		public Map<Integer,String> getBinHuffCode(HeapNode node, StringBuilder s){
	       if(!node.isLeaf()){
	    	   StringBuilder sleft = new StringBuilder();
	    	   sleft.append(s.toString());
	    	   getBinHuffCode(node.getLeft(),sleft.append("0"));

	    	   StringBuilder sright = new StringBuilder();
	    	   sright.append(s.toString());
	    	   getBinHuffCode(node.getRight(),sright.append("1"));
	       }
	       else{
	    	   BinHuffCode.put(node.getData(), s.toString());
	       }

	        return BinHuffCode;

		}

		public Map<Integer,String> getPairHuffCode(PairNode node, StringBuilder s){
		       if(!node.isLeaf()){
		    	   StringBuilder sleft = new StringBuilder();
		    	   sleft.append(s.toString());
		    	   getPairHuffCode(node.getLeft(),sleft.append("0"));

		    	   StringBuilder sright = new StringBuilder();
		    	   sright.append(s.toString());
		    	   getPairHuffCode(node.getRight(),sright.append("1"));
		       }
		       else{
		    	   PairHuffCode.put(node.getData(), s.toString());
		       }

		        return PairHuffCode;

			}

		public Map<Integer,String> getFourHuffCode(FourNode node, StringBuilder s){
		       if(!node.isLeaf()){
		    	   StringBuilder sleft = new StringBuilder();
		    	   sleft.append(s.toString());
		    	   getFourHuffCode(node.getLeft(),sleft.append("0"));

		    	   StringBuilder sright = new StringBuilder();
		    	   sright.append(s.toString());
		    	   getFourHuffCode(node.getRight(),sright.append("1"));
		       }
		       else{
		    	   FourHuffCode.put(node.getData(), s.toString());
		       }

		        return FourHuffCode;

			}
	}




