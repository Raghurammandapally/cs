/**
 * The main class that handles the entire network
 * Has multiple attributes each with its own use
 * 
 */

import java.util.*;
import java.lang.System;


public class NNImpl{
    public ArrayList<Node> inputNodes=null;//list of the output layer nodes.
    public ArrayList<Node> hiddenNodes=null;//list of the hidden layer nodes
    public ArrayList<Node> outputNodes=null;// list of the output layer nodes
	
    public ArrayList<Instance> trainingSet=null;//the training set
	
    Double learningRate=1.0; // variable to store the learning rate
    int maxEpoch=1; // variable to store the maximum number of epochs
	
    /**
     * This constructor creates the nodes necessary for the neural network
     * Also connects the nodes of different layers
     * After calling the constructor the last node of both inputNodes and  
     * hiddenNodes will be bias nodes. 
     */
	
    public NNImpl(ArrayList<Instance> trainingSet, int hiddenNodeCount, Double learningRate, int maxEpoch, Double [][]hiddenWeights, Double[][] outputWeights)
    {
	this.trainingSet=trainingSet;
	this.learningRate=learningRate;
	this.maxEpoch=maxEpoch;
		
	//input layer nodes
	inputNodes=new ArrayList<Node>();
	int inputNodeCount=trainingSet.get(0).attributes.size();
	int outputNodeCount=trainingSet.get(0).classValues.size();
	for(int i=0;i<inputNodeCount;i++)
	    {
		Node node=new Node(0);
		inputNodes.add(node);
	    }
		
	//bias node from input layer to hidden
	Node biasToHidden=new Node(1);
	inputNodes.add(biasToHidden);
		
	//hidden layer nodes
	hiddenNodes=new ArrayList<Node> ();
	for(int i=0;i<hiddenNodeCount;i++)
	    {
		Node node=new Node(2);
		//Connecting hidden layer nodes with input layer nodes
		for(int j=0;j<inputNodes.size();j++)
		    {
			NodeWeightPair nwp=new NodeWeightPair(inputNodes.get(j),hiddenWeights[i][j]);
			node.parents.add(nwp);
		    }
		hiddenNodes.add(node);
	    }
		
	//bias node from hidden layer to output
	Node biasToOutput=new Node(3);
	hiddenNodes.add(biasToOutput);
			
	//Output node layer
	outputNodes=new ArrayList<Node> ();
	for(int i=0;i<outputNodeCount;i++)
	    {
		Node node=new Node(4);
		//Connecting output layer nodes with hidden layer nodes
		for(int j=0;j<hiddenNodes.size();j++)
		    {
			NodeWeightPair nwp=new NodeWeightPair(hiddenNodes.get(j), outputWeights[i][j]);
			node.parents.add(nwp);
		    }	
		outputNodes.add(node);
	    }	
    }
	
    /**
     * Get the output from the neural network for a single instance
     * Return the idx with highest output values. For example if the outputs
     * of the outputNodes are [0.1, 0.5, 0.2], it should return 1.
     * The parameter is a single instance
     */
	
    public int calculateOutputForInstance(Instance inst)
    {

	// TODO: add code here
	for(int n = 0; n < inputNodes.size() - 1; n++) {
	    inputNodes.get(n).setInput(inst.attributes.get(n));
	}

	inputNodes.get(inputNodes.size()-1).setInput(1.0);

	for(Node n : hiddenNodes) {
	    n.calculateOutput();
	}

	for(Node n : outputNodes) {
	    n.calculateOutput();
	}

	int ret = 0;
	double max = 0.0;
	for(int i = 0; i < outputNodes.size(); i++) {
	    if(outputNodes.get(i).getOutput() > max) {
		max = outputNodes.get(i).getOutput();
		ret = i;
	    }
	}

	return ret;

    }
	

	
	
	
    /**
     * Train the neural networks with the given parameters
     * 
     * The parameters are stored as attributes of this class
     */
	
    public void train()
    {

	/*
function BACK-PROP-LEARNING(examples , network ) returns a neural network
inputs: examples , a set of examples, each with input vector x and output vector y
network , a multilayer network with L layers, weights wi,j , activation function g
local variables: Δ, a vector of errors, indexed by network node
repeat

  for each weight wi,j in network do
    wi,j ← a small random number
  for each example (x, y) in examples do

    // Propagate the inputs forward to compute the outputs 
    for each node i in the input layer do
      ai←xi
    for l=2 to L do
      for each node j in layer do
        inj← iwi,jai
	aj ←g(inj)

    // Propagate deltas backward from output layer to input layer
    for each node j in the output layer do
      Δ[j]←g (inj) ×(yj − aj)
    for l=L−1to1do
      for each node i in layer do
        Δ[i]←g (ini) j wi,j Δ[j]
    // Update every weight in network using deltas
    for each weight wi,j in network do
      wi,j←wi,j+α×ai×Δ[j]
until some stopping criterion is satisfied
return network

Figure 18.24 The back-propagation algorithm for learning in multilayer networks.
	 */
        //function BACK-PROP-LEARNING(examples , network ) returns a neural network
        //inputs: examples , a set of examples, each with input vector x and output vector y
        //network , a multilayer network with L layers, weights wi,j , activation function g
        //local variables: Δ, a vector of errors, indexed by network node
        //repeat

	for(int epoch = 0; epoch < maxEpoch; epoch++) {
        //  for each weight wi,j in network do
        //      wi,j ← a small random number

        //  for each example (x, y) in examples do
	    for(Instance i : trainingSet) {

        //      // Propagate the inputs forward to compute the outputs 
        //      for each node i in the input layer do
		for(int n = 0; n < inputNodes.size() - 1; n++) {
        //          ai←xi
		    inputNodes.get(n).setInput(i.attributes.get(n));
		}
		
        //      for l=2 to L do
        //          for each node j in layer do
        //              inj← iwi,jai
        //	    aj ←g(inj)
		for(Node n : hiddenNodes) {
		    n.calculateOutput();
		}

		for(Node n : outputNodes) {
		    n.calculateOutput();
		}

        //      // Propagate deltas backward from output layer to input layer
        //      for each node j in the output layer do
        //          Δ[j]←g (inj) × (yj − aj)
		List<Double> delta_out = new ArrayList<Double>();
		for(int n = 0; n < outputNodes.size(); n++) {
		    Double err = 0.0;
		    err += outputNodes.get(n).getGPrime();
		    err *= i.classValues.get(n) - outputNodes.get(n).getOutput(); // (yj - aj)
		    delta_out.add(err);
		}

        //      for l=L−1to1do
        //          for each node i in layer do
        //              Δ[i]←g (ini) j wi,j Δ[j]
		List<Double> delta_hidden = new ArrayList<Double>();
		// include bias node: https://piazza.com/class/ij0elbmn6x64s5?cid=215
		for(int hidden = 0; hidden < hiddenNodes.size(); hidden++) {
		    Double val = 0.0;
		    for(int output = 0; output < outputNodes.size(); output++) {
			val += outputNodes.get(output).parents.get(hidden).weight * delta_out.get(output);
		    }
		    
		    delta_hidden.add(val * hiddenNodes.get(hidden).getGPrime());
		}

        //      // Update every weight in network using deltas
        //      for each weight wi,j in network do
        //          wi,j ← wi,j + (α × a_i × Δ[j])
		for(int output = 0; output < outputNodes.size(); output++) {
		    Node outputNode = outputNodes.get(output);
		    for(int hidden = 0; hidden < hiddenNodes.size(); hidden++) {
			outputNode.parents.get(hidden).weight +=
			    learningRate * hiddenNodes.get(hidden).getOutput() * delta_out.get(output);
		    }
		}

		// ignore bias because it has no parents
		for(int hidden = 0; hidden < hiddenNodes.size() - 1; hidden++) {
		    Node hiddenNode = hiddenNodes.get(hidden);
		    for(int input = 0; input < inputNodes.size(); input++) {
			hiddenNode.parents.get(input).weight +=
			    learningRate * inputNodes.get(input).getOutput() * delta_hidden.get(hidden);
		    }
		}
		
	    }
        //until some stopping criterion is satisfied
	}
        //return network

    }
}
