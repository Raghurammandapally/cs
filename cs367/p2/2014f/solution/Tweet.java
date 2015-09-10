class Tweet{
	private String user;
	private String message;
	private int time;

	public Tweet(int t, String m, String u) throws TweetTooLongException{
		if(m.length() > 140){
			throw new TweetTooLongException();
		}
		time = t;
		message = m;
		user = u;
	}

	public String getMessage(){
		return message;
	}
	
	public int getTime(){
		return time;
	}

	public String getUser(){
		return user;
	}

	public void print(){
		System.out.println(time+" "+user+":"+message);
	}
}


