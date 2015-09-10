/**
 * Stores the time, message and user of a Tweet
 */
class Tweet{

    /**
     * Constructs a Tweet for user with message at time. 
     * Throws a TweetTooLongException if message over 140 characters.
     * 
     * @param time time at which tweet occured
     * @param message message of the tweet, <=140 characters
     * @param user the person who tweeted the tweet 
     * @throws TweetTooLongException if message over 140 characters 
     */
    public Tweet(int time, String message, String user){ 
    }

    /** 
     * Returns the stored message of the Tweet
     * @return the message
     */
    public String getMessage(){
    }
    
    /** 
     * Returns the stored time of the Tweet
     * @return the stored time
     */
    public int getTime(){
    }

    /** 
     * Returns the user who tweeted the Tweet
     * @return the user
     */
    public String getUser(){
    }

    /** 
     * Print the Tweet with the following format: <TIME> <USER>:<MESSAGE>
     */
    public void print(){
    }
}


