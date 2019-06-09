package ahocorasic;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ahocorasic {
	public static void main(String[] args) throws IOException {
		node root = new node(' ');
		String[] patternArr;
		String patternFileAddress = "C:/Users/hp/Desktop/aho corasik, KMP/PatternMatching/Same.txt";
		String textFileAddress = "C:/Users/hp/Desktop/aho corasik, KMP/PatternMatching/CompactExongraph.revCatZ.FASTA";
		int count=0;
		node temp;
		int idx;

		patternArr = patternFileLoad(patternFileAddress);		
		for(int i=0 ; i < patternArr.length; i++)
			insert(patternArr[i],root);
		makeFailureLink(root);


		FileReader fr = new FileReader(textFileAddress);
		BufferedReader br = new BufferedReader(fr);
		String fastaStr=null;

		while ((fastaStr=br.readLine())!=null) {
			if(!(fastaStr.charAt(0)=='>')) {	
				idx=0;
				temp=root;
				while(idx<fastaStr.length()) {
					if(temp.children[(int)fastaStr.charAt(idx)-(int)'A']!=null) {
						temp=temp.children[(int)fastaStr.charAt(idx)-(int)'A'];
						idx++;
						if(temp.sucessLink!=0) {
							count+=temp.sucessLink;
							System.out.println(count);
						}
						if(temp.outputLink!=0){
							count+=temp.outputLink;
							System.out.println(count);
						}
					}
					else {
						if(temp==root)
							idx++;
						else
							temp=temp.failureLink;	
					}
				}
			}
		}
	}


	public static void makeFailureLink(node rootNode) {
		Queue<node> queue = new LinkedList<node>();
		node currentNode = null;
		queue.add(rootNode);

		while(!queue.isEmpty()) {
			currentNode=queue.poll();
			for(int i = 0 ; i<26; i++) {
				if(currentNode.children[i]!=null) {
					calFailureValue(currentNode,currentNode.children[i],rootNode);
					queue.add(currentNode.children[i]);
				}
			}
		}
	}

	public static void calFailureValue(node parentNode,node childNode, node rootNode) {
		if(parentNode==rootNode)
			childNode.failureLink=rootNode;
		else {
			if(parentNode.failureLink.children[(int)childNode.var-(int)'A']!=null)
			{
				childNode.failureLink=parentNode.failureLink.children[(int)childNode.var-(int)'A'];
				if(childNode.failureLink.sucessLink!=0)
					childNode.outputLink++;
				if(childNode.failureLink.outputLink!=0)
					childNode.outputLink+=childNode.failureLink.outputLink;		
			}
			else
				calFailureValue(parentNode.failureLink,childNode,rootNode);
		}
	}



	public static void insert(String str, node head) {
		node tempNode = head;
		for(int i=0; i<str.length(); i++) {
			if(tempNode.children[((int)str.charAt(i))-'A']==null)
			{
				node newNode = new node(str.charAt(i));
				tempNode.children[((int)str.charAt(i))-'A']=newNode;
			}
			tempNode=tempNode.children[((int)str.charAt(i))-'A'];
		}
		tempNode.sucessLink++;
	}
	
	
	public static String[] patternFileLoad(String data)
	{
		FileReader fr = null;
		String s=null;
		String [] arr = new String[10000000];
		String [] returnArr = null;
		int idx=0;

		try {
			fr = new FileReader(data);
			BufferedReader br = new BufferedReader(fr);
			while ((s=br.readLine())!=null) {
				arr[idx]=s;
				idx++;
			}
			br.close();
			returnArr = new String[idx];
			for(int i=0; i<returnArr.length;i++)
				returnArr[i]=arr[i];
			return returnArr;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("error in readmatchingheader");
		return returnArr;
	}
}