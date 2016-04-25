import java.util.HashMap;
import java.util.Map;
import java.lang.Math;

/**
 * Your implementation of a naive bayes classifier. Please implement all four methods.
 */

public class NaiveBayesClassifierImpl implements NaiveBayesClassifier {
  private double delta = 0.00001;

  private int v; 

  private int n_business;
  private int n_sports;

  private int n_business_words;
  private int n_sports_words;

  private HashMap<String, Integer> sports_hash;
  private HashMap<String, Integer> busi_hash;

  /**
   * Trains the classifier with the provided training data and vocabulary size
   */
  @Override
  public void train(Instance[] trainingData, int v) {

    sports_hash = new HashMap<String, Integer>();
    busi_hash = new HashMap<String, Integer>();

    HashMap<String,Integer> hash = null;

    this.v = v;
    // TODO : Implement
    for (Instance i : trainingData) {
      if (i.label == Label.SPORTS) {
        n_sports++;
        n_sports_words += i.words.length;
        hash = sports_hash;
      }

      if (i.label == Label.BUSINESS) {
        n_business++;
        n_business_words += i.words.length;
        hash = busi_hash;
      }

      for (String s : i.words) {
        hash.put(s, (hash.containsKey(s) ? hash.get(s) : 0) + 1);
      }
    }
  }

  /*
   * Prints out the number of documents for each label
   */
  public void documents_per_label_count(){
    // TODO : Implement
    System.out.format("SPORTS=%d\n", n_sports);
    System.out.format("BUSINESS=%d\n", n_business);
  }

  /*
   * Prints out the number of words for each label
   */
  public void words_per_label_count(){
    // TODO : Implement
    System.out.format("SPORTS=%d\n", n_sports_words);
    System.out.format("BUSINESS=%d\n", n_business_words);
  }

  /**
   * Returns the prior probability of the label parameter, i.e. P(SPAM) or P(HAM)
   */
  @Override
  public double p_l(Label label) {
    // TODO : Implement
    return (label == Label.SPORTS ? n_sports : n_business)/((double) n_sports + n_business);
  }

  private int C_l(String w, Label l) {
    if(l == Label.SPORTS) {
      return (sports_hash.containsKey(w) ? sports_hash.get(w) : 0);
    } else {
      return (busi_hash.containsKey(w) ? busi_hash.get(w) : 0);
    }
  }


  /**
   * Returns the smoothed conditional probability of the word given the label, i.e. P(word|SPORTS) or
   * P(word|BUSINESS)
   */
  @Override
  public double p_w_given_l(String word, Label label) {
    // TODO : Implement
    return (C_l(word, label) + delta)/(v * delta + (label == Label.SPORTS ? n_sports_words : n_business_words));
  }

  /**
   * Classifies an array of words as either SPAM or HAM.
   */
  @Override
  public ClassifyResult classify(String[] words) {
    // TODO : Implement
    ClassifyResult res = new ClassifyResult();
    double log_sports = Math.log(p_l(Label.SPORTS));
    double log_busi = Math.log(p_l(Label.BUSINESS));

    for (String s : words) {
      log_sports += Math.log(p_w_given_l(s, Label.SPORTS));
      log_busi += Math.log(p_w_given_l(s, Label.BUSINESS));
    }

    res.label = (log_sports > log_busi ? Label.SPORTS : Label.BUSINESS);
    res.log_prob_sports = log_sports;
    res.log_prob_business = log_busi;
    return res;
  }
  
  /*
   * Constructs the confusion matrix
   */
  @Override
  public ConfusionMatrix calculate_confusion_matrix(Instance[] testData){
    // TODO : Implement
    ConfusionMatrix c = new ConfusionMatrix(0, 0, 0, 0);
    for(Instance i : testData) {
      if(classify(i.words).label == Label.SPORTS) {
        if(i.label == Label.SPORTS) {
          c.TP++;
        } else {
          c.FP++;
        }
      } else {
        if(i.label == Label.SPORTS) {
          c.FN++;
        } else {
          c.TN++;
        }
      }
    }


    return c;
  }
  
}
