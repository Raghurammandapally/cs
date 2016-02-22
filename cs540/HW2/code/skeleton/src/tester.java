public class tester {
    public static void main(String[] args) {
	Player k = new wfunkhouserPlayer();
	k.initialize("keith", 1);
	GameState s = new GameState();
	int[] i = {5, 5, 4, 4, 4, 4, 0, 4, 4, 0, 0, 6, 6, 2};
	s.state = i;
	k.move(s);
    }
}
