import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	return "";
    }

	private DecTreeNode DTL(List<Instance> examples, List<String> attrs, List<Instance> parentEx, String parentAttr, String attr) {

	// function Decision-Tree-Learning (examples, attributes, parent-examples )
	// returns a tree
	//   if examples is empty then
	//     return Plurality-Value(parent-examples)
	    if (examples.isEmpty()) {
		return (parentEx == null ? null : 
			//DecTreeNode(String _label, String _attribute, String _parentAttributeValue, boolean _terminal) {
			new DecTreeNode(getMajority(parentEx), attr,  parentAttr, true)
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
	if(diff) {
	    return new DecTreeNode(class1, attr, parentAttr, true);
	}
	//   else if attributes is empty then
	//     return Plurality-Value(examples)
	else if(attrs.isEmpty()) {
	    return new DecTreeNode(getMajority(examples), attr, parentAttr, true);
	}
	//   else
	else {
	//     A ← argmax(a∈attributes, Importance(a, examples))
	    String A = "";
	    int imp = 0;
	    for(int i = 0; i < attributes.size(); i++) {
		//int newImp = importance(attributes.get(i), examples);
		//if(newImp > imp) {
		//imp = newImp;
		//A = attributes.get(i);
		//}
	    }
	    
	//     tree ← a new decision tree with root test A
	//     for each value vk of A do
	//       exs ← { e : e ∈ examples and e.A = vk }
	//       subtree ← Decision-Tree-Learning (exs, attributes-A, examples)
	//       add a branch to tree with label (A = vk ) and subtree subtree
	//   return tree

	return null;
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
  }

  @Override
  public String classify(Instance instance) {
      return null;
    // TODO: add code here
  }

  @Override
  public void rootInfoGain(DataSet train) {
    this.labels = train.labels;
    this.attributes = train.attributes;
    this.attributeValues = train.attributeValues;
    // TODO: add code here
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
