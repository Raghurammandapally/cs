import java.util.*;
class Timeline{

private ListADT<Tweet> list;

public Timeline(){
	list = new SimpleLinkedList<Tweet>();
}


	public void add(Tweet tweet){
		int ndx = 0;
		while(ndx < list.size()){
			Tweet h=list.get(ndx);
			int h1 = tweet.getTime();
			int h2 = h.getTime();
			if( tweet.getTime() > list.get(ndx).getTime()){
				ndx++;
			}
			else{
				break;
			}
		}
		list.add(ndx,tweet);
	}

	public void add(List<Tweet> userTweets){
		for(int ndx = 0;ndx<userTweets.size();ndx++){
			this.add(userTweets.get(ndx));
		}
	}

//remove all tweets with this user.
	public void remove(String user){
		int ndx = 0;
        while(ndx < list.size()){
			if(list.get(ndx).getUser().equals(user)){
				list.remove(ndx);
			}
			else{
				ndx++;
			}
		}
	}

	public Timeline search(String keyword){
		Timeline newLine = new Timeline();
		for(int ndx = 0;ndx < list.size();ndx++){
			if(list.get(ndx).getMessage().contains(keyword)){
				newLine.add(list.get(ndx));
			}
		}
		return newLine;
	}

	public void print(){
        for(int ndx = 0;ndx < list.size();ndx++){
			list.get(ndx).print();
		}
	}	
	public void print(int time){
		for(int ndx = 0;ndx< list.size();ndx++){
			if(list.get(ndx).getTime() < time)
				list.get(ndx).print();
		}
	}
}

