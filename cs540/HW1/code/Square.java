/**
 * Represents a simple square.
 * 
 * You do not need to change this class.
 */
public class Square {
	final public int X;
	final public int Y;

	public Square(int x, int y) {
		this.X = x;
		this.Y = y;
	}

  public String toString() {
    return "(" + X + "," + Y + ")";
  }

  public boolean equals(Square s) {
    return this.X == s.X && this.Y == s.Y;
  }
}
