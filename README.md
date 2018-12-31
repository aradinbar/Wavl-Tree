# Wavl-Tree
Representation of weak Avl Trees. 
Class WAVLTree:

This class is used to create instances of node objects of WAVL tree
Fields:

•WAVLNode root – the root of the tree

•WAVLNode min – the node of the tree with the lowest key.

•WAVLNode max– the node of the tree with the highest key.

Methods:

•Public Boolean empty() – returns true if tree is empty (when root = null) O(1)

•Public string search(int k) – returns info of node where key = k. Returns null if
node with such a key does not exist. Uses the method search(WAVLNode x, int k)
(which is explained later) that searches (in O(logn) complexity) for such a node
O(logn)

•public String search(WAVLNode x,int k) – searches (just like a regular binary
search of a binary search tree) for a node where key = k. If such node exists it returns
the info of the node, if the tree is empty return null O(logn)

•public WAVLNode searchins(WAVLNode x,int k) - searches (just like a regular
binary search of a binary search tree) for a node where key = k. If such node exists it
returns the node , else it returns the last node that was visited (the node that would be
the parent of the node searched for if that node has existed)
if the tree is empty return null O(logn)

•Public int Rotation(WAVLNode parent, int control) – following the algorithm
from the lecture, the function receives a node to rotate and preforms the necessary
actions (switching of nodes, replacing links) in order to rebalance the tree. The
method returns the number of actions used to balance the tree .O(1)

•Public int RotationSimetry(WAVLNode parent, int control)– mirroring the
function Rotation O(1)

•Public int DoubleRotation(WAVLNode parent, int control) – following the
algorithm from the lecture, the function receives a node to rotate and preforms the
necessary actions (switching of nodes, replacing links) in order to rebalance the tree
.the methods preforms two rotations on the tree. The method returns the number of
actions used to balance the tree .O(1)

•Public int DoubleRotationSimetry(WAVLNode parent, int control)– mirroring
the function DoubleRotation O(1)

•Public int insert(int k,string i) – inserts node with key=k and info=i, to the tree.
Counts number of rebalancing actions performed during insertions and returns that
number. Uses searchins(O(logn)) method to get the node that is supposed to be the
parent of the inserted node. Then uses method promote to promote necessary
promotion and get node where promotions stop and potential rotations are needed.
Then uses check_case method in order to discover, according to the situation, which
of the methods: Rotation,RotationSimetry,DoubleRotation,DoubleRotationsimetry to
use. O(logn)

•public String leftrightson (WAVLNode son)- Returns "left" if the node son if left
son, returns "right" if the node son is a right son. null otherwise(root or null
object).O(1).•public string check_case (WAVLNode son) – returns cases for problematic
situations during insertion, corresponding to the cases from the lecture, by
simply checking the rank differences between the nodes. These are the values the
method returns and their corresponding cases in constant time complexity:
- "1" – case 1 left: (0,1)
- "1simetry"– case 1 right: (1,0)
- "2" – case 2 left: (1,2) <- (0,2)
- "2simetry" – case 2 right: (2,0) -> (2,1)
- "3" case 3 left: (2,1) <- (0,2)
- " 3simetry" case 3 right: (2,0) -> (1,2)

•public WAVLNode promote(WAVLNode node) – preformes promotion to the
father of the node.O(l)

•public String checkdeletecase(WAVLNode) – returns cases for problematic
situations during deletion, corresponding to the cases from the lecture, by simply
checking the rank differences between the nodes. These are the values the method
returns and their corresponding cases in constant time complexity:
- "1" – case 1 left: (2,1)
- "1simetry" – case 1 right: (1,2)
- "2" – case 2 left: (2,2)
- "2simetry"– case 2 right: (2,2)
- "3_2" - case 3 left1: (3,2)
- "3_2simetry"– case3 right1: (2,3)
- "3_1" – case 3 left2: (3,1)
- "3_1simetry– case 3 right2: (1,3).

•public String checkdelete3_1(WAVLNode z) – returns cases for problematic
situations during deletion, specifically in (3,1) cases. , corresponding to the cases
from the lecture, by simply checking the rank differences between the nodes. These
are the values the method returns and their corresponding cases in constant time
complexity:
- "3_1_a" –: (3,1) -> (2,2)
"3_1_a_simetry" –: (1,1) -> (2,2)
"3_1_b" –: (3,1) -> (2,1)
"3_1_b" –: (3,1) -> (1,1)
"3_1_b_simetry"–: (1,3) -> (2,1)
"3_1_b_simetry" –: (1,3) -> (1,1)
"3_1_c"–: (3,1) -> (1,2)
"3_1_c_simetry"–: (1,3) -> (1,2)

•Public WAVLNode successor(WAVLNode node)- returns the successor of the
node. O(logn)

•Public int delete(int k) – deletes node with key=k. Returns 0 if such node does not
exist. Uses method searchins (O(logn)) to get the node that will be removed from the
tree. Uses method numofchildren (o(1)) to get the number of the children of the node.
Uses method successor (O(logn)) to replace with its successor (if necessary), then
deletes the node and returns the node where demotions should begin. Then uses
method demote (O(1)) to demote or doubledemote\doubledemotesimetry, until
rotations are needed. After the necessary demotions are carried out, the functionschecks which case it has reached at checkdeletecase (O(1)) and then performs the
rotations and demotions needed. At the end of the process, the function returns the
total number of actions performed O(logn)

•public WAVLNode demote(WAVLNode node) – performs one demotion, always
returns 1 O(1)

•public WAVLNode doubledemote(WAVLNode z) – performs one demotion to
the node,and demotion to the right child of the node by calling the method
demote(o(1)) always returns 2 O(1)

•public int doubledemotesimetry(WAVLNode z) – performs one demotion to the
node,and one demotion to the left child of the node by calling the method
demote(o(1)) always returns 2 O(1)

•public int numOfChildren(WAVLNode node)- returns the exact number of the
children of the node, 1 and -1 both represent a unary node- 1 stand for one left child
and -1 stands for one right child. O(1)

•public string min() – if tree is empty returns null, else returns info of node with the
lowest key. O(1).

•public string max() – if tree is empty returns null, else returns info of node with the
highest key. O(1).

•public int size() – returns the field "size" of the root,which contains the number of
nodes in the tree O(1)

•public WAVLNode[] successort() – returns an array sorted by key, by calling
successor function beginning from the node with minimum key until reaching node
with maximum key, this is equal to in-order tree traversal, as shown in class. O(n)

•public int[] keysToArray() - returns a sorted array which contains all keys in the
tree, or an empty array if the tree is empty. Does so by using the function successort()
to receive a sorted array of nodes, and then extracting their keys. O(n).

•public String[] infoToArray() – same as keysToArray, only returns an array of
strings which contains the info of the nodes in the tree O(n).

•public IWAVLNode getRoot() – returns the root of the tree O(1)

• public String select(int i)- returns the info of the i-th order statistic node. Uses the
function select(WAVLNode x,int i).O(logn).

• public WAVLNode select(WAVLNode x,int i) – returns the i-th order statistic
node implementing the algorithm taught in class. O(log n).
