/**
 * The Timeline class uses SimpleLinkedList to build a time ordered list of 
 * following tweets. Tweets with smaller Time fields should come earlier in the list.
 */
class Timeline{

    private SimpleLinkedList<Tweet> list;

    /**
     * Constructs an empty timeline
     */
    public Timeline(){
    }

    /**
     * Adds a single tweet to the Timeline
     * 
     * @param tweet the tweet to add
     */
    public void add(Tweet tweet){
    }

    /**
     * Adds an ordered list of tweets to the Timeline
     * @param tweets the list of tweets to add
     */
    public void add(List<Tweet> tweets){
    }

    /**
     * Removes all tweets by user from the Timeline
     * 
     * @param user the user whose tweets should be removed
     */
    public void remove(String user){
    }

    /**
     * Create a new Timeline containing only the tweets containing keyword
     * 
     * @param keyword the keyword to search for
     * @return a Timeline of tweets containing keyword
     */
    public Timeline search(String keyword){
    }

    /**
     * Print each tweet in the timeline
     */
    public void print(){
    }   
    
    /**
     * Print each tweet in the timeline since time
     * 
     * @param time the largest time to print tweets
     */
    public void print(int time){
    }
}

