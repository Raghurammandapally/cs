import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Fill in the implementation details of the class DecisionTree using this file. Any methods or
 * secondary classes that you want are fine but we will only interact with those methods in the
 * DecisionTree framework.
 * 
 * You must add code for the 1 member and 4 methods specified below.
 * 
 * See DecisionTree for a description of default methods.
 */
public class DecisionTreeImpl extends DecisionTree {
    private DecTreeNode root;
    //ordered list of class labels
    private List<String> labels; 
    //ordered list of attributes
    private List<String> attributes; 
    //map to ordered discrete values taken by attributes
    private Map<String, List<String>> attributeValues; 
  
    /**
     * Answers static questions about decision trees.
     */
    DecisionTreeImpl() {
	// no code necessary this is void purposefully
    }
    
    private String getMajority(List<Instance> examples) {
	int[] counts = new int[labels.size()];
	for(Instance i : examples) {
	    counts[labels.indexOf(i.label)]++;
	}
	
	int max = -1;
	int count = 0;
	for(int i = 0; i < labels.size(); i++) {
	    if(counts[i] == max) {
		count++;
	    } else if (counts[i] > max) {
		max = counts[i];
		count = 1;
	    }
	}
	
	int[] maxes = new int[count];
	int start = 0;
	for(int i = 0; i < labels.size(); i++) {
	    if(counts[i] == max) {
		maxes[start] = i;
		start++;
	    }
	}

	//Random r = new Random();
	//return labels.get(maxes[r.nextInt(count)]);
	return labels.get(maxes[0]);

    }
    
    private List<List<Instance>> sepInstances(String a, List<Instance> ex) {
	List<List<Instance>> l = new ArrayList<List<Instance>>();
	List<String> vals = attributeValues.get(a);
	int num = vals.size();

	for(int i = 0; i < num; i++) {
	    l.add(new ArrayList<Instance>());
	}

	// this is the attribute index to check in each instance
	int idx = attributes.indexOf(a);
	
	for(Instance i : ex) {
	    l.get(vals.indexOf(i.attributes.get(idx))).add(i);
	}
	    
	return l;
    }

    private double H(List<Instance> ex) {
	// labels is List<String>
	int[] l = new int[labels.size()];
	for(Instance i : ex) {
	    l[labels.indexOf(i.label)]++;
	}
	int N = 0;
	for(int i : l) {
	    N += i;
	}
	double P = 0.0;
	for(int i : l) {
	    double ii = (double) i;
	    if(i > 0) {
		P -= (ii/N) * ( Math.log(ii/N) / Math.log(2) );

	    }
	}

	return P;
    }

    // return H(Y|X)
    private double getH(String a, List<Instance> examples) {
	List<List<Instance>> l = sepInstances(a, examples);
	int N = 0;  // N = examples.size()
	for(List li : l) {
	    N += li.size();
	}
	double P = 0.0;
	for(List li : l) {
	    double i = (double) li.size();
	    P += (i/N)*H(li);
	}
	return P;
    }

    private DecTreeNode DTL(List<Instance> examples, List<String> attrs, List<Instance> parentEx, DecTreeNode parentNode, String val) {
	// function Decision-Tree-Learning (examples, attributes, parent-examples )
	// returns a tree
	//   if examples is empty then
	//     return Plurality-Value(parent-examples)
	if (examples.isEmpty()) {
	    return (parentEx == null ? null : 
		    new DecTreeNode(getMajority(parentEx), null,  val, true)
		    );
	}
	//   else if all examples have the same classification then
	//     return the classification
	String class1 = examples.get(0).label;
	boolean diff = false;
	for(int i = 1; i < examples.size(); i++) {
	    if(!examples.get(i).label.equals(class1)) {
		diff = true;
		break;
	    }
	}
	if(!diff) {
	    return new DecTreeNode(class1, null, val, true);
	}
	//   else if attributes is empty then
	//     return Plurality-Value(examples)
	else if(attrs.isEmpty()) {
	    return new DecTreeNode(getMajority(examples), null, val, true);
	}
	//   else
	else {
	    //     A ← argmax(a∈attributes, Importance(a, examples))
	    // maximizing I(Y;X) = H(Y) - H(Y|X) is the same as minimizing
	    // H(Y|X)
	    String A = "";
	    double imp = Double.MAX_VALUE;

	    for(String s : attrs) {
		double newImp = getH(s, examples);
		if(newImp < imp) {
		    imp = newImp;
		    A = s;
		}
	    }

	    if(A.equals("")) {
		throw new RuntimeException();
	    }
	    
	    //     tree ← a new decision tree with root test A
	    DecTreeNode tree = new DecTreeNode(null, A, val, false);
	    //     for each value vk of A do
	    List<List<Instance>> l = sepInstances(A, examples);
	    List<String> vals = attributeValues.get(A);
	    for(int i = 0; i < l.size(); i++) {
		//for(List<Instance> li : l) {
		ArrayList<String> newAttrs = new ArrayList(attrs);
		//       exs ← { e : e ∈ examples and e.A = vk }
		//       subtree ← Decision-Tree-Learning (exs, attributes-A, examples)
		//private DecTreeNode DTL(List<Instance> examples, List<String> attrs, List<Instance> parentEx, String parentAttr, String attr) {
		if(!newAttrs.remove(A)) {
		    throw new RuntimeException();
		}
		DecTreeNode subtree = DTL(l.get(i), newAttrs, examples, tree, vals.get(i));
		//       add a branch to tree with label (A = vk ) and subtree subtree
		tree.addChild(subtree);
	    }
	    //   return tree
	    return tree;
	}
    }

    /**
     * Build a decision tree given only a training set.
     * 
     * @param train: the training set
     */
    DecisionTreeImpl(DataSet train) {

	this.labels = train.labels;
	this.attributes = train.attributes;
	this.attributeValues = train.attributeValues;
	// TODO: add code here
    
	root = DTL(train.instances, attributes, null, null, null);
    }

    private double acc(DecTreeNode root, List<Instance> examples) {
	double num = 0.0;
	double denom = (double) examples.size();
	for(Instance i : examples) {
	    if(classify(i).equals(i.label)) {
		num++;
	    }
	}
	return denom == 0 ? 0 : num/denom;
    }
    
    


    /*
    Prune(treeT,TUNEset)
	1.  ComputeT’saccuracyonTUNE;callitAcc(T)
	    2.  ForeveryinternalnodeNinT:
    a)  NewtreeTN=copyofT,butprune(delete)thesubtree
    underN
    b)  NbecomesaleafnodeinTN.Thelabelisthemajorityvote
	ofTRAINexamplesreachingN
	c)  Acc(TN)=TN’saccuracyonTUNE
	      3.  LetT*bethetree(amongtheTN’sandT)withthelargestAcc()
	      SetT=T
	      4.  RepeatfromStep1un@lnomoreimprovement
	      5.  ReturnT
    */
    private DecTreenode prune(DecTreeNode root, double acc, List<Instance> examples) {
	
	return null;
    }

    

    /**
     * Build a decision tree given a training set then prune it using a tuning set.
     * 
     * @param train: the training set
     * @param tune: the tuning set
     */
    DecisionTreeImpl(DataSet train, DataSet tune) {

	this.labels = train.labels;
	this.attributes = train.attributes;
	this.attributeValues = train.attributeValues;
	// TODO: add code here
	root = DTL(train.instances, attributes, null, null, null);
	
	
    }

    @Override
	public String classify(Instance instance) {
	DecTreeNode curr = root;
	while(!curr.terminal) {
	    curr = curr.children.get(
				     //private int getAttributeValueIndex(String attr, String value) {				   
				     getAttributeValueIndex(curr.attribute,
							    instance.attributes.get(
										    getAttributeIndex(curr.attribute)
										    )
							    )
				     );
	}
	
	return curr.label;
    }

    @Override
	public void rootInfoGain(DataSet train) {
	this.labels = train.labels;
	this.attributes = train.attributes;
	this.attributeValues = train.attributeValues;
	// TODO: add code here
	root = DTL(train.instances, attributes, null, null, null);
	double hClass = H(train.instances);
	for(String s : attributes) {
	    System.out.format("%s %.5f\n", s, hClass-getH(s, train.instances));
	}
	    
    }
  
    @Override
    /**
     * Print the decision tree in the specified format
     */
	public void print() {

	printTreeNode(root, null, 0);
    }

    /**
     * Prints the subtree of the node with each line prefixed by 4 * k spaces.
     */
    public void printTreeNode(DecTreeNode p, DecTreeNode parent, int k) {
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < k; i++) {
	    sb.append("    ");
	}
	String value;
	if (parent == null) {
	    value = "ROOT";
	} else {
	    int attributeValueIndex = this.getAttributeValueIndex(parent.attribute, p.parentAttributeValue);
	    value = attributeValues.get(parent.attribute).get(attributeValueIndex);
	}
	sb.append(value);
	if (p.terminal) {
	    sb.append(" (" + p.label + ")");
	    System.out.println(sb.toString());
	} else {
	    sb.append(" {" + p.attribute + "?}");
	    System.out.println(sb.toString());
	    for (DecTreeNode child : p.children) {
		printTreeNode(child, p, k + 1);
	    }
	}
    }

    /**
     * Helper function to get the index of the label in labels list
     */
    private int getLabelIndex(String label) {
	for (int i = 0; i < this.labels.size(); i++) {
	    if (label.equals(this.labels.get(i))) {
		return i;
	    }
	}
	return -1;
    }
 
    /**
     * Helper function to get the index of the attribute in attributes list
     */
    private int getAttributeIndex(String attr) {
	for (int i = 0; i < this.attributes.size(); i++) {
	    if (attr.equals(this.attributes.get(i))) {
		return i;
	    }
	}
	return -1;
    }

    /**
     * Helper function to get the index of the attributeValue in the list for the attribute key in the attributeValues map
     */
    private int getAttributeValueIndex(String attr, String value) {
	for (int i = 0; i < attributeValues.get(attr).size(); i++) {
	    if (value.equals(attributeValues.get(attr).get(i))) {
		return i;
	    }
	}
	return -1;
    }
}
