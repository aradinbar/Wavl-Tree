/**
 * 
 * WAVL Tree
 * 
 */

	public class WAVLTree {
		private WAVLNode root=new WAVLNode();
		private WAVLNode min=new WAVLNode();
		private WAVLNode max=new WAVLNode();

		/**returns true if tree is empty (when root = null) 
		 * O(1)**/
	  public boolean empty() {
		  if (!this.root.isRealNode())
			  return true; 
		  return false;
	  }

	  /**returns info of node where key = k. 
	   * Returns null if node with such a key does not exist. 
	   * Uses the method search(WAVLNode x, int k) (which is explained later) that searches (in O(logn) complexity) for such a node 
	   * O(logn)**/
	  public String search(int k)
	  {
		  return search(this.root,k);
	  }
	  
	  /**searches (just like a regular binary search of a binary search tree) for a node where key = k. 
	   * If such node exists it returns the info of the node, 
	   * if the tree is empty return null 
	   * O(logn)**/
	  public String search(WAVLNode x, int k){
			if (!x.isRealNode())
				return null;
			if (x.key==k)
				return x.info;
			if (x.key > k)
				return search(x.left,k);
			else{
				return search(x.right,k);
			}
	  }
	  
	  /**searches (just like a regular binary search of a binary search tree) for a node where key = k. 
	   * If such node exists it returns the node , 
	   * else it returns the last node that was visited (the node that would be the parent of the node searched for if that node has existed)
	   * if the tree is empty return null 
	   * O(logn)**/
	  public WAVLNode searchins(WAVLNode x, int k){
			if(empty())
				return null;
		  	if (x.rank==-1){
				
				return x.parent;
			}
			
			if (x.key==k)
				return x;
			
			if (x.key > k)
				return searchins(x.left,k);
			else{
				return searchins(x.right,k);
			}	
	  }
	  
	  /**following the algorithm from the lecture, 
	   * the function receives a node to rotate and preforms the necessary actions (switching of nodes, replacing links) in order to rebalance the tree.
	   * The method returns the number of actions used to balance the tree .
	   * O(1)**/
	  public int Rotation(WAVLNode parent,int control){
		  WAVLNode z=parent;//swap subtrees of z and x
		  WAVLNode x=z.left;
		  WAVLNode y=z.right;
		  WAVLNode b=x.right;
		  int size_y=y.size;//swap subtree sizes of z and x
		  int size_z=z.size;
		  int size_b=b.size;
		  x.size=size_z;
		  z.size=size_b+size_y+1;
		  if (z==this.root)
			  this.root=x;
		  if (z.parent==null){
			  x.parent=null;
		  }
		  else{
			  x.parent=z.parent;
			  String son=leftrightson(z);
			  if(son.equals("left"))
				  z.parent.left=x;
			  else
				  z.parent.right=x;
		  }
		  x.right=z;
		  z.left=b;
		  b.parent=z;
		  z.parent=x;
		  //ranks adjustment
		  if(control==0) //0 represents insert
		  {
			z.rank--;
		  	return 2;
		  }
		  else //1 represents delete
		  {
			  x.rank++;
			  z.rank--;
			  if ((z.rank-z.left.rank==2)&&(z.rank-z.right.rank==2)){
				  z.rank--;
				  return 4;
			  }
			  return 3;
		  }
	  }
	  
	  /**mirroring the function Rotation 
	   * O(1)**/
	  public int RotationSimetry(WAVLNode parent,int control){
		  WAVLNode z=parent;
		  WAVLNode x=z.right;
		  WAVLNode b=x.left;
		  WAVLNode a=x.right;
		  int size_a=a.size;
		  int size_z=z.size;
		  int size_b=b.size;
		  x.size=size_z;
		  z.size=size_a+size_b+1;
		  if (z==this.root)
			  this.root=x;
		  if (z.parent==null){
			  x.parent=null;
		  }
		  else{
			  x.parent=z.parent;
			  String son=leftrightson(z);
			  if(son.equals("left"))
				  z.parent.left=x;
			  else
				  z.parent.right=x;
		  }
		  x.left=z;
		  z.right=b;
		  b.parent=z;
		  z.parent=x;
		  if(control==0)//insert
		  {
		  z.rank--;
		  return 2;
		  }
		  else//delete
		  {
			  x.rank++;
			  z.rank--;
			  if ((z.rank-z.left.rank==2)&&(z.rank-z.right.rank==2)){
				  z.rank--;
				  return 4;
			  }
			  return 3;
		  }
		 }
	  
	  /**following the algorithm from the lecture, 
	   * the function receives a node to rotate and preforms the necessary actions (switching of nodes, replacing links) in order to rebalance the tree.
	   * the methods preforms two rotations on the tree.
	   * The method returns the number of actions used to balance the tree .
	   * O(1)**/
	  public int DoubleRotation(WAVLNode parent,int control){
		  WAVLNode z=parent;
		  WAVLNode x=z.left;
		  WAVLNode b=x.right;
		  int num=RotationSimetry(x,control);
		  num +=Rotation(z,control);
		  if(control==0)//insert
		  {
			  b.rank++;
			  num++;
		  }
		  return num;
	  }
	  
	  /**mirroring the function DoubleRotation 
	   * O(1)**/
	  public int DoubleRotationSimetry(WAVLNode parent,int control){
		  WAVLNode z=parent;
		  WAVLNode x=z.right;
		  WAVLNode b=x.left;
		  int num=Rotation(x,control);
		  num +=RotationSimetry(z, control);
		  if(control==0)//insert
		  {
			  b.rank++;
			  num++;
		  }
		  return num;
	  }
	  
	  /**inserts node with key=k and info=i, to the tree. 
	   * Counts number of rebalancing actions performed during insertions and returns that number. 
	   * Uses searchins(O(logn)) method to get the node that is supposed to be the parent of the inserted node. 
	   * Then uses method promote to promote necessary promotion and get node where promotions stop and potential rotations are needed. 
	   * Then uses check_case method in order to discover, according to the situation, which of the methods: 
	   * Rotation,RotationSimetry,DoubleRotation,DoubleRotationsimetry to use. 
	   * O(logn)**/
	  public int insert(int k, String i) {
		   int count = 0;
		   WAVLNode Node=new WAVLNode(k,i);
		   String s=search(k);
		   if (s!=null){
			   return 0;
		   }
		   WAVLNode P_Node= searchins(this.root,k);
		   if (P_Node==null){
			   this.root=Node;
			   this.min=this.max=Node;
		   }
		   if(k>this.max.key) 
			   this.max=Node;
		   if(k<this.min.key)
			   this.min=Node;
		   WAVLNode leafL=new WAVLNode(Node);
		   WAVLNode leafR=new WAVLNode(Node);
		   Node.left=leafL;
		   Node.right=leafR;
		   if(P_Node!=null){
		   if (P_Node.key > Node.key){
			   Node.parent=P_Node;
			   P_Node.left=Node;
		   }
		   else if (P_Node.key < Node.key){
			   Node.parent=P_Node;
			   P_Node.right=Node;
		   }
		   }
		   WAVLNode temp=Node;
		   while(temp!=this.root){//update ancestors' subtree sizes
			temp.parent.size++;
			temp=temp.parent;
	   }
		   //look for invalid nodes and rebalance the tree
		   while( (check_case(Node)=="1")|| (check_case(Node)=="1simetry") && (Node!=this.root)){
			   promote(Node);
			   count++;
			   Node=Node.parent;
		   } 
		   String sit=null;
		   if (Node==this.root){
			   if (Node.rank-Node.left.rank==0){
				   sit=check_case(Node.left);
			   }
			   else if (Node.rank-Node.right.rank==0){
				   sit=check_case(Node.right);
			   }
		   }
		   else{
			sit=check_case(Node); 
			Node=Node.parent;
		   }
		   if(sit!=null){
		   if (sit.equals("2")){
			   count+=Rotation(Node,0);
		   }
		   else if (sit.equals("2simetry")){
			   count+=RotationSimetry(Node,0);
		   }
		   if (sit.equals("3")){
			   count+=DoubleRotation(Node,0);
		   }
		   else if (sit.equals("3simetry")){
			   count+=DoubleRotationSimetry(Node,0);
		   }
		   }
		   
		   return count;
	   }
	   
	   /**Returns "left" if the node son if left son, returns "right" if the node son is a right son.
	    * O(1).**/
	   public String leftrightson(WAVLNode son){
		   WAVLNode parent = son.parent;
		   if (parent.left==son){
			   return "left";
		   }
		   else{
			   return "right";
		   }
	   }
	   
	   /**returns cases for problematic situations during insertion,
	    * corresponding to the cases from the lecture,
	    * by simply checking the rank differences between the nodes.
	    * These are the values the method returns and their corresponding cases in constant time complexity:
	    * - "1" – case 1 left: (0,1)
	    * - "1simetry"– case 1 right: (1,0)
	    * - "2" – case 2 left: (1,2) <- (0,2)
	    * - "2simetry" – case 2 right: (2,0) -> (2,1)
	    * - "3" case 3 left: (2,1) <- (0,2)
	    * - " 3simetry" case 3 right: (2,0) -> (1,2)
	    **/
	   public String check_case(WAVLNode son){
		   if(son==null)
			   return null;
		   if(son.parent==null)
			   return null;
		   if(!son.isRealNode())
			   return null;
		   String leftORright = leftrightson(son);
		   WAVLNode parent = son.parent;
		   if(leftORright.equals("left")){
			   if ((parent.rank-son.rank==0)&& (parent.rank-parent.right.rank==1)){
				   return "1";
			   }
			   
			   if ((parent.rank-son.rank==0)&&(parent.rank-parent.right.rank==2)){
				   if(son.rank-son.left.rank==1 && son.rank-son.right.rank==2)
					   return "2";
				   else if (son.rank-son.left.rank==2 && son.rank-son.right.rank==1)
					   return "3";
			   }
		   }
		   if(leftORright.equals("right")){
			   if ((parent.rank-son.rank==0)&& (parent.rank-parent.left.rank==1)){
				   return "1simetry";
			   }
			   if ((parent.rank-son.rank==0)&&(parent.rank-parent.left.rank==2)){
				   if(son.rank-son.left.rank==1 && son.rank-son.right.rank==2)
					   return "3simetry";
				   else if (son.rank-son.left.rank==2 && son.rank-son.right.rank==1)
					   return "2simetry";
			   }
		   }
		   return null;
	   }

	   /**preformes promotion to the father of the node.
	    * O(l)**/
	   public void promote(WAVLNode son){
		   son.parent.rank ++;
	   }
	   
	   /**returns cases for problematic situations during deletion, 
	    * corresponding to the cases from the lecture, 
	    * by simply checking the rank differences between the nodes. 
	    * These are the values the method returns and their corresponding cases in constant time complexity:
	    * - "1" – case 1 left: (2,1)
	    * - "1simetry" – case 1 right: (1,2)
	    * - "2" – case 2 left: (2,2) 
	    * - "2simetry"– case 2 right: (2,2) 
	    * - "3_2" - case 3 left1: (3,2)
	    * - "3_2simetry"– case3 right1: (2,3) 
	    * - "3_1" – case 3 left2: (3,1) 
	    * - "3_1simetry– case 3 right2: (1,3). 
	    **/
	   public String checkdeletecase(WAVLNode leaf){
		   if (this.root!=leaf){
			   	String leftORright = leftrightson(leaf);
		   		WAVLNode z=leaf.parent;
		   		if (leftORright.equals("left")){
		   			if( (z.rank-leaf.rank==2) && (z.rank-z.right.rank==1)){
		   				return "1";
		   			}
		   			else if( (z.rank-leaf.rank==2) && (z.rank-z.right.rank==2)){
		   				return "2";
		   			}
		   			else if( (z.rank-leaf.rank==3) && ((z.rank-z.right.rank==2))){
		   				return "3_2";
		   			}
		   			else if( (z.rank-leaf.rank==3) && ((z.rank-z.right.rank==1))){
		   				return "3_1";
		   			}
		   			
		   		}
		   		else if (leftORright.equals("right")){
		   			if( (z.rank-leaf.rank==1) && (z.rank-z.left.rank==2)){
		   				return "1simetry";
		   			}
		   			else if( (z.rank-leaf.rank==2) && (z.rank-z.left.rank==2)){
		   				return "2simetry";
		   			}
		   			else if( (z.rank-leaf.rank==3) && ((z.rank-z.left.rank==2))){
		   				return "3_2simetry";
		   			}
		   			else if( (z.rank-leaf.rank==3) && ((z.rank-z.left.rank==1))){
		   				return "3_1simetry";
		   			}
		   		}
		   }
		   return "";  
	   }
	   
	   /**returns cases for problematic situations during deletion, specifically in (3,1) cases.
	    * corresponding to the cases from the lecture, by simply checking the rank differences between the nodes. 
	    * These are the values the method returns and their corresponding cases in constant time complexity:
	    * - "3_1_a" –: (3,1) -> (2,2)
	    * "3_1_a_simetry" –: (1,1) -> (2,2)
	    * "3_1_b" –: (3,1) -> (2,1)
	    * "3_1_b" –: (3,1) -> (1,1)
	    * "3_1_b_simetry"–: (1,3) -> (2,1)
	    * "3_1_b_simetry" –: (1,3) -> (1,1)
	    * "3_1_c"–: (3,1) -> (1,2)
	    * "3_1_c_simetry"–: (1,3) -> (1,2)
	    **/
	   public String checkdelete3_1(WAVLNode z){
		   if ((z.rank-z.left.rank==3) && (z.rank-z.right.rank==1)){
			  WAVLNode y=z.right;
			   if ((y.rank-y.left.rank==2)&&(y.rank-y.right.rank==2)){
				   return "3_1_a";
			   }
			   
			   if (((y.rank-y.left.rank==2)||(y.rank-y.left.rank==1)) && (y.rank-y.right.rank==1)){
				   return "3_1_b";
			   }
			   
			   if ((y.rank-y.left.rank==1)&&(y.rank-y.right.rank==2)){
				   return "3_1_c";
			   }
		   }
			   if ((z.rank-z.left.rank==1) && (z.rank-z.right.rank==3)){
				  WAVLNode y=z.left;
				   if ((y.rank-y.left.rank==2)&&(y.rank-y.right.rank==2)){
					   return "3_1_a_simetry";
				   }
				   
				   if (((y.rank-y.right.rank==2)||(y.rank-y.right.rank==1)) && (y.rank-y.left.rank==1)){
					   return "3_1_b_simetry";
				   }
				   
				   if ((y.rank-y.left.rank==2)&&(y.rank-y.right.rank==1)){
					   return "3_1_c_simetry";
				   }
		   }
		   return null;
	   }
	   
	   /**returns the successor of the node. 
	    * O(logn)**/
	   public WAVLNode successor(WAVLNode node){
		   if (node.right.rank!=-1){ //go right once and then left all the way
			   node=node.right;
			   while (node.left.rank!=-1){
				   node=node.left;
			   }
			   return node;
		   }
		   if (node.right.rank==-1){//if there's no right child, look for successor
			    String leftorright=leftrightson(node);
				   
			   if (leftorright.equals("left")){
				   if (node.parent!=null){
					   node=node.parent;
				   }
			   }
				   if (leftorright.equals("right")){
					   while (node!=this.root&&leftrightson(node).equals("right"))
					   {
						   node=node.parent;
					   }
					   if(node==this.root)
						   return null;
					   else
						   node=node.parent;
				   }
				  }
		   return node; 
	   }
	   
	   /**deletes node with key=k. 
	    * Returns 0 if such node does not exist. 
	    * Uses method searchins (O(logn)) to get the node that will be removed from the tree. 
	    * Uses method numofchildren (o(1)) to get the number of the children of the node. 
	    * Uses method successor (O(logn)) to replace with its successor (if necessary), 
	    * then deletes the node and returns the node where demotions should begin. 
	    * Then uses method demote (O(1)) to demote or doubledemote\doubledemotesimetry, 
	    * until rotations are needed. After the necessary demotions are carried out, 
	    * the functions checks which case it has reached at checkdeletecase (O(1)) and then performs the rotations and demotions needed. 
	    * At the end of the process, the function returns the total number of actions performed 
	    * O(logn)**/
	   public int delete(int k)
	   {
		   int count=0;
		   WAVLNode node = searchins(this.root,k);//look for the node in the tree
		   if (node==null || node.key!=k) //404:node not found
			   return 0;
		   int x=numOfChildren(node);//check how many children node has
		   if(k==this.min.key)//update min/max
			   this.min=successor(this.min);
		   if(k==this.max.key)
		   {
			   if(numOfChildren(this.max)==1)//max's predecessor
				   this.max=this.max.left;
			   else 
				   this.max=this.max.parent;
		   }
		   if(x==2) //binary node
		   {
			WAVLNode succ=successor(node);
			node.key=succ.key;
			node.info=succ.info;
			if(succ==this.min) node=this.min;
			if(succ==this.max) node=this.max;
			node=succ;
			if(succ.right.isRealNode()) x=-1;
			else x=0;
		   }
		   
		   if(x==0)//leaf
		   {
			   if(node==this.root)
			   {
				   this.root=new WAVLNode();
				   return 0;
			   }
			   else
			   {
				 if(leftrightson(node).equals("left")){
					 node.parent.left=node.left; 
					 node.left.parent=node.parent;
					 node=node.left;}	
				 if(leftrightson(node).equals("right")){
					 node.parent.right=node.right;
					 node.right.parent=node.parent;
					 node=node.right;
					 }
				 }
				
		   }
		   if(x==1)//unary node, one left child
		   {
			   if(node==this.root)
			   {
				   this.root=node.left;
				   return 0;
			   }
			   else
			   {
				   if(leftrightson(node).equals("left")){
					  node.parent.left=node.left;
					  node.left.parent=node.parent;
					  node=node.left;}
				   if(leftrightson(node).equals("right")){
					 node.parent.right=node.left;
					 node.left.parent=node.parent;
					 node=node.left;}

			   }
				   
		   }
		   if(x==-1)//unary node, one right child
		   {
			   if(node==this.root)
			   {
				   this.root=node.right;
				   return 0;
			   }
			   else
			   {
				   if(leftrightson(node).equals("left")){
					  node.parent.left=node.right;
					  node.right.parent=node.parent;
					  node=node.right;}
				   if(leftrightson(node).equals("right")){
					 node.parent.right=node.right;
					 node.right.parent=node.parent;
					 node=node.right;}
				   
			   }
		   }
		   WAVLNode temp=node;
		   while(temp!=this.root){ //update ancestors' subtree sizes
			temp.parent.size--;
			temp=temp.parent;
		   }
		   //balance -demote all 2-2 nodes and rotate if needed at last
		   while( (checkdeletecase(node)=="2")|| (check_case(node)=="2simetry") && (node!=this.root)){
			   count+=demote(node.parent);
			   node=node.parent;
		   }
		   String sit=checkdeletecase(node);

		   if (sit.equals("3_1")) 
			 {
			   String sit3_1=checkdelete3_1(node.parent);
			   if (sit3_1.equals("3_1_a")){
					   count+=doubledemote(node.parent);
			   }
			   else if (sit3_1.equals("3_1_b")){
				   count+=RotationSimetry(node.parent,1);
			   }
			   else if (sit3_1.equals("3_1_c")){
				   count+=DoubleRotationSimetry(node.parent,1);
			   }
		   
		   }
		   
		   if (sit.equals("3_1simetry") )
			 {
			   String sit3_1=checkdelete3_1(node.parent);
			   if (sit3_1.equals("3_1_a_simetry")){
					   count+=doubledemote(node.parent);
			   }
			   else if (sit3_1.equals("3_1_b_simetry")){
				   count+=Rotation(node.parent,1);
			   }
			   else if (sit3_1.equals("3_1_c_simetry")){
				   count+=DoubleRotation(node.parent,1);
			   }
		   
			   
		   }
		   else if (sit.equals("3_2") ||sit.equals("3_2simetry")){
			   count+=demote(node.parent);
		   }
		return count;
		   }
		   
	   /**performs one demotion, always returns 1 
	    * O(1)**/
	   public int demote(WAVLNode node){
		   node.rank --;
		   return 1;
	   }
	   
	   /**performs one demotion to the node,and demotion to the right child of the node by calling the method demote(o(1))
	    * always returns 2 
	    * O(1)**/
	   public int doubledemote(WAVLNode z){
		   return demote(z)+demote(z.right);
	   }
	   
	   /**performs one demotion to the node,and one demotion to the left child of the node by calling the method demote(o(1))
	    * always returns 2
	    * O(1)**/
	   public int doubledemotesimetry(WAVLNode z){
		   return demote(z)+demote(z.left);
	   }
	   
	   /**returns the exact number of the children of the node,
	    * 1 and -1 both represent a unary node- 1 stand for one left child and -1 stands for one right child. 
	    * O(1)**/
	   public int numOfChildren(WAVLNode node)
	   {
		   if(node.left.isRealNode() && node.right.isRealNode())
			   return 2;
		   if(node.left.isRealNode() && !node.right.isRealNode())
			   return 1; // 1 left child
		   if(!node.left.isRealNode() && node.right.isRealNode())
			   return -1; //1 right child
		   return 0; // no children
	   }
	   
	   /**if tree is empty returns null, else returns info of node with the lowest key.
	    * O(1).**/
	   public String min()
	   {
		   if(empty()) return null;
		  return this.min.info;
	   }

	   /**if tree is empty returns null, else returns info of node with the highest key. 
	    * O(1).**/
	   public String max()
	   {
		   if(empty()) return null;
		  return this.max.info;
	   }
	   
	   /**returns an array sorted by key, 
	    * by calling successor function beginning from the node with minimum key until reaching node with maximum key,
	    * this is equal to in-order tree traversal, as shown in class. 
	    * O(n)**/
	   public WAVLNode[] successort(){
		   WAVLNode[] arr=new WAVLNode[this.size()];
		   WAVLNode x=this.min;
		   int i=0;
		   while(x!=null)
		   {
			   arr[i]=x;
			   x=successor(x);
			   i++;
			   
		   }
		   return arr;
	   }

	  /**returns a sorted array which contains all keys in the tree, or an empty array if the tree is empty.
	   * Does so by using the function successort() to receive a sorted array of nodes, and then extracting their keys. 
	   * O(n).**/ 
	  public int[] keysToArray()
	  {
		  	
	        int[] arr = new int[this.root.size];
	        if (!this.root.isRealNode()){
	        	return arr; 
	        }
	        WAVLNode[] nodes=successort();
	        for (int i=0;i<arr.length;i++)
	        {
	        	arr[i]=nodes[i].key;
	        }
	        return arr;
	  }

	  /**same as keysToArray, only returns an array of strings which contains the info of the nodes in the tree 
	   * O(n).**/
	  public String[] infoToArray()
	  {
	        String[] arr = new String[this.root.size]; 
	        if (!this.root.isRealNode()){
	        	return arr;              
	        }
	        WAVLNode[] nodes=successort();
	        for (int i=0;i<nodes.length;i++)
	        {
	        	arr[i]=nodes[i].info;
	        }
	        return arr;
	      
	  }

	  /**returns the field "size" of the root,which contains the number of nodes in the tree 
	   * O(1)**/ 
	  public int size()
	   {
		   return this.root.size; 
	   }
	  
	  /**returns the root of the tree O(1)
	   * **/
	  public IWAVLNode getRoot()
	   {
		   if (empty()) return null;
		   return (IWAVLNode)this.root;
	   }
	     
	   /**returns the info of the i-th order statistic node. Uses the function select(WAVLNode x,int i).
	   * O(logn). **/
	   public String select(int i)
	   {
		   return select(this.root,i-1).info;
	   }
	   
	   /**returns the i-th order statistic node implementing the algorithm taught in class. 
	    * O(log n).*/
	   public WAVLNode select(WAVLNode x,int i)
	   {
		   int r=x.left.size;
		   if (i==r) return x;
		   else if (i<r) return select(x.left,i);
		   else return select(x.right,i-r-1);
	   }
		
	  
		public interface IWAVLNode{	
			public int getKey(); //returns node's key (for virtuval node return -1)
			public String getValue(); //returns node's value [info] (for virtuval node return null)
			public IWAVLNode getLeft(); //returns left child (if there is no left child return null)
			public IWAVLNode getRight(); //returns right child (if there is no right child return null)
			public boolean isRealNode(); // Returns True if this is a non-virtual WAVL node (i.e not a virtual leaf or a sentinal)
			public int getSubtreeSize(); // Returns the number of real nodes in this node's subtree (Should be implemented in O(1))
		}

		
	  public class WAVLNode implements IWAVLNode{
		  private int key;
		  private String info;
		  private int rank;
		  private int size;
		  private WAVLNode parent;
		  private WAVLNode right;
		  private WAVLNode left;
		  
		  /**constructor: setting initial node's initial values 
		   * O(1)**/
		  public WAVLNode(int key,String info){
			  this.key=key;
			  this.info=info;
			  this.rank=0;
			  this.size=1;
			  this.parent=null;
			  this.right=null;
			  this.left=null;
			  
		  }
		  
		  /**constructor: setting initial node's initial values 
		   * O(1)**/
		  public WAVLNode(WAVLNode parent){
			  this.rank=-1;
			  this.parent=parent;
			  this.right=null;
			  this.left=null;
			  this.size=0;
		  }
		  
		  /**constructor: setting initial node's initial values 
		   * O(1)**/
		  public WAVLNode(){
			  this.rank=-1;
			  this.parent=null;
			  this.right=null;
			  this.left=null;
			  this.size=0;
		  }

		    /**returns the key of the node**/
			public int getKey()
			{
				if(empty())
					return -1;
				if(this.rank == -1)
					return -1;
				return this.key;
			}
			
			/**returns the info of the node**/
			public String getValue()
			{
				if(empty())
					return "";
				if (this.rank == -1)
					return null; 
				return this.info;
			}
			
			/**returns the left child of the node**/
			public IWAVLNode getLeft()
			{
				return this.left;
			}
			
			/**returns the right child of the node**/
			public IWAVLNode getRight()
			{
				return this.right;
			}
			
			/**returns false if node is an external leaf 
			 * O(1)**/
			public boolean isRealNode()
			{
				if(this.rank==-1 || this==null)
					return false;
				return true;
			}

			/**returns the number of real nodes in this  node's subtree.**/
			public int getSubtreeSize()
			{
				return this.size;
			}
	  }

	}
	  


