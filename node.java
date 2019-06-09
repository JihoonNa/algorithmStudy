package ahocorasic;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class node {

	
	char var;
	node failureLink;
	node [] children;
	int outputLink;	
	int sucessLink;
	
	public node(char var)
	{
		this.var=var;
		this.children = new node[26];
		this.sucessLink=0;
	}
	
}
