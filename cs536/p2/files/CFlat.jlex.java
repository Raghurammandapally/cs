import java_cup.runtime.*; // defines the Symbol class
// The generated scanner will return a Symbol for each token that it finds.
// A Symbol contains an Object field named value; that field will be of type
// TokenVal, defined below.
//
// A TokenVal object contains the line number on which the token occurs as
// well as the number of the character on that line that starts the token.
// Some tokens (literals and IDs) also include the value of the token.
class TokenVal {
  // fields
    int linenum;
    int charnum;
  // constructor
    TokenVal(int line, int ch) {
        linenum = line;
        charnum = ch;
    }
}
class IntLitTokenVal extends TokenVal {
  // new field: the value of the integer literal
    int intVal;
  // constructor
    IntLitTokenVal(int line, int ch, int val) {
        super(line, ch);
        intVal = val;
    }
}
class IdTokenVal extends TokenVal {
  // new field: the value of the identifier
    String idVal;
  // constructor
    IdTokenVal(int line, int ch, String val) {
        super(line, ch);
    idVal = val;
    }
}
class StrLitTokenVal extends TokenVal {
  // new field: the value of the string literal
    String strVal;
  // constructor
    StrLitTokenVal(int line, int ch, String val) {
        super(line, ch);
        strVal = val;
    }
}
// The following class is used to keep track of the character number at which
// the current token starts on its line.
class CharNum {
    static int num=1;
}


class Yylex implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_END,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_END,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NOT_ACCEPT,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_END,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_END,
		/* 56 */ YY_NOT_ACCEPT,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_END,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_END,
		/* 64 */ YY_NOT_ACCEPT,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NOT_ACCEPT,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NOT_ACCEPT,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NOT_ACCEPT,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NOT_ACCEPT,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NOT_ACCEPT,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NOT_ACCEPT,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NOT_ACCEPT,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NOT_ACCEPT,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NOT_ACCEPT,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NOT_ACCEPT,
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NOT_ACCEPT,
		/* 94 */ YY_NOT_ACCEPT,
		/* 95 */ YY_NOT_ACCEPT,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_END,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NOT_ACCEPT,
		/* 101 */ YY_NOT_ACCEPT,
		/* 102 */ YY_NOT_ACCEPT,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"24:9,28,25,24:2,26,24:18,28,36,21,46,24:2,31,23,43,44,39,29,42,35,40,41,20:" +
"10,24,45,33,34,30,23,24,18:26,24,22,24:2,19,24,13,1,15,8,11,12,18,17,4,18:2" +
",3,18,5,2,18:2,9,14,6,10,7,16,18:3,37,32,38,24:2,0,27")[0];

	private int yy_rmap[] = unpackFromString(1,127,
"0,1,2,3,4,1,5,6,7,8,9,10,11,1:4,12,1:4,13,14,1:12,13:7,15,1,13:4,4,16,1:2,1" +
"7,18,19,20,21,22,23,24,25,26,27,14,28,24,29,30,31,32,33,34,35,36,17,37,38,3" +
"9,15,40,41,42,18,43,44,19,45,46,23,47,48,49,27,50,51,52,23,45,27,53,43,23,5" +
"4,13,55,56,57,58,59,60,61,62,63,64,65,66,67,13,68,69,70,71,72,73,74,75")[0];

	private int yy_nxt[][] = unpackFromString(76,47,
"1,2,118:2,50,118,120,121,118,122,118,123,124,118,125,96,126,118:3,3,4,51:3," +
"5,-1,1,6,7,8,58,62,9,10,11,12,13,14,15,16,17,18,19,20,21,66,-1:48,118,103,1" +
"18:16,104:2,-1:46,3,-1:27,49:20,23,56,49:2,24,53,24,49:19,-1:28,6,-1:47,25," +
"-1:47,26,-1:3,27,-1:45,30,31,-1:46,32,-1:47,33,-1:45,34,-1:53,60,-1:6,118:1" +
"8,104:2,-1:27,64:21,67,64:2,-1:3,64:19,-1,79:20,43,88,79:2,-1:3,79:19,-1,11" +
"8:4,57,118:6,22,118:6,104:2,-1:27,75:20,52,77,75:2,24,53,24,75:19,-1,83:20," +
"43,92,83:2,24,98,24,83:19,-1,86:20,-1,90,86:2,44,55,44,86:19,-1,69:4,71:2,6" +
"9:14,71,73,71,69,101:2,-1,69:19,-1,118:5,36,118:12,104:2,-1:57,28,-1:16,89:" +
"20,97,89:3,-1:3,89:19,-1,60:24,35,-1:2,60:19,-1,118:4,37,118:13,104:2,-1:58" +
",29,-1:15,93:20,97,94,93:2,24,98,24,93:19,-1,118:2,38,118:15,104:2,-1:27,79" +
":4,64:2,79:14,64,67,64,79,102:2,-1,79:19,-1,118:10,39,118:7,104:2,-1:27,69:" +
"20,43,81,69:2,44,55,44,69:19,-1,118:7,40,118:10,104:2,-1:27,71:20,23,100,71" +
":2,24,98,24,71:19,-1,118:10,41,118:7,104:2,-1:27,83:4,71:2,83:14,23,100,71," +
"83,59,63,24,83:19,-1,118:5,42,118:12,104:2,-1:27,118:10,45,118:7,104:2,-1:2" +
"7,86:4,87:2,86:14,87:3,86:3,-1,86:19,-1,118:10,46,118:7,104:2,-1:27,118:4,4" +
"7,118:13,104:2,-1:27,69:4,79:2,69:14,43,88,79,69,101:2,-1,69:19,-1,118:5,48" +
",118:12,104:2,-1:27,84:20,97,85,84:2,44,55,44,84:19,-1,84:4,89:2,84:14,97,8" +
"9:2,84,86:2,-1,84:19,-1,87:20,52,91,87:2,24,98,24,87:19,-1,79:20,43,88,79:2" +
",102:2,-1,79:19,-1,86:4,-1:2,86:14,-1:3,86:3,-1,86:19,-1:5,87:2,-1:14,87:3," +
"-1:24,79:4,83:2,79:14,54,95,83,79,102:2,-1,79:19,-1,89:4,93:2,89:14,99,93:2" +
",89,-1:3,89:19,-1,83:20,43,92,83:2,59,63,24,83:19,-1,118,111,118,61,118:14," +
"104:2,-1:27,79:4,71:2,79:14,71,73,71,79,102:2,-1,79:19,-1,118,65,118:16,104" +
":2,-1:27,118:9,68,118:8,104:2,-1:27,118:3,70,118:14,104:2,-1:27,118:5,113,1" +
"18:12,104:2,-1:27,118:13,72,118:4,104:2,-1:27,118:2,114,118:15,104:2,-1:27," +
"118:8,119,118:9,104:2,-1:27,118:9,74,118:8,104:2,-1:27,118:3,115,118:14,104" +
":2,-1:27,118:9,116,118:8,104:2,-1:27,118:13,76,118:4,104:2,-1:27,118:2,78,1" +
"18:15,104:2,-1:27,118:8,80,118:9,104:2,-1:27,118:14,82,118:3,104:2,-1:27,11" +
"8:9,117,118:8,104:2,-1:27,118:8,105,118:9,104:2,-1:27,118,106,118:16,104:2," +
"-1:27,118:10,107,118:7,104:2,-1:27,118:2,108,118:15,104:2,-1:27,118:12,109," +
"118:5,104:2,-1:27,118:5,110,118:12,104:2,-1:27,118:16,112,118,104:2,-1:26");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

return new Symbol(sym.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -3:
						break;
					case 3:
						{ // NOTE: the following computation of the integer value does NOT
            //       check for overflow.  This must be changed.
            int k;
            try {
              k = Integer.parseInt(yytext());
            } catch (NumberFormatException e) {
              ErrMsg.warn(yyline+1,CharNum.num, "integer literal too large; using max value");
              k = Integer.MAX_VALUE;
            }
            Symbol S = new Symbol(sym.INTLITERAL, new IntLitTokenVal(yyline+1,
                  CharNum.num, k));
            CharNum.num += yytext().length();
            return S;
          }
					case -4:
						break;
					case 4:
						{ ErrMsg.fatal(yyline+1, CharNum.num,
                         "illegal character ignored: " + yytext());
            CharNum.num += yytext().length();
          }
					case -5:
						break;
					case 5:
						{ CharNum.num = 1; }
					case -6:
						break;
					case 6:
						{ CharNum.num += yytext().length(); }
					case -7:
						break;
					case 7:
						{ Symbol S = new Symbol(sym.PLUS, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -8:
						break;
					case 8:
						{ Symbol S = new Symbol(sym.GREATER, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -9:
						break;
					case 9:
						{ Symbol S = new Symbol(sym.LESS, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -10:
						break;
					case 10:
						{ Symbol S = new Symbol(sym.ASSIGN, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -11:
						break;
					case 11:
						{ Symbol S = new Symbol(sym.MINUS, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -12:
						break;
					case 12:
						{ Symbol S = new Symbol(sym.NOT, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -13:
						break;
					case 13:
						{ Symbol S = new Symbol(sym.LCURLY, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -14:
						break;
					case 14:
						{ Symbol S = new Symbol(sym.RCURLY, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -15:
						break;
					case 15:
						{ Symbol S = new Symbol(sym.TIMES, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -16:
						break;
					case 16:
						{ Symbol S = new Symbol(sym.DOT, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -17:
						break;
					case 17:
						{ Symbol S = new Symbol(sym.DIVIDE, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -18:
						break;
					case 18:
						{ Symbol S = new Symbol(sym.COMMA, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -19:
						break;
					case 19:
						{ Symbol S = new Symbol(sym.LPAREN, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -20:
						break;
					case 20:
						{ Symbol S = new Symbol(sym.RPAREN, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -21:
						break;
					case 21:
						{ Symbol S = new Symbol(sym.SEMICOLON, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -22:
						break;
					case 22:
						{
            Symbol S = new Symbol(sym.IF, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -23:
						break;
					case 23:
						{ 
            Symbol S = new Symbol(sym.STRINGLITERAL, new StrLitTokenVal(yyline+1,
                  CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -24:
						break;
					case 24:
						{ 
            ErrMsg.fatal(yyline+1, CharNum.num, "unterminated string literal ignored");
            CharNum.num = 1;
          }
					case -25:
						break;
					case 25:
						{ Symbol S = new Symbol(sym.PLUSPLUS, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += 2;
            return S;
          }
					case -26:
						break;
					case 26:
						{ Symbol S = new Symbol(sym.READ, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += 2;
            return S;
          }
					case -27:
						break;
					case 27:
						{ Symbol S = new Symbol(sym.GREATEREQ, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += 2;
            return S;
          }
					case -28:
						break;
					case 28:
						{ Symbol S = new Symbol(sym.AND, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += 2;
            return S;
          }
					case -29:
						break;
					case 29:
						{ Symbol S = new Symbol(sym.OR, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += 2;
            return S;
          }
					case -30:
						break;
					case 30:
						{ Symbol S = new Symbol(sym.WRITE, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += 2;
            return S;
          }
					case -31:
						break;
					case 31:
						{ Symbol S = new Symbol(sym.LESSEQ, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += 2;
            return S;
          }
					case -32:
						break;
					case 32:
						{ Symbol S = new Symbol(sym.EQUALS, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += 2;
            return S;
          }
					case -33:
						break;
					case 33:
						{ Symbol S = new Symbol(sym.MINUSMINUS, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += 2;
            return S;
          }
					case -34:
						break;
					case 34:
						{ Symbol S = new Symbol(sym.NOTEQUALS, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += 2;
            return S;
          }
					case -35:
						break;
					case 35:
						{CharNum.num = 1;}
					case -36:
						break;
					case 36:
						{
            Symbol S = new Symbol(sym.INT, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -37:
						break;
					case 37:
						{
            Symbol S = new Symbol(sym.CIN, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -38:
						break;
					case 38:
						{
            Symbol S = new Symbol(sym.BOOL, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -39:
						break;
					case 39:
						{
            Symbol S = new Symbol(sym.TRUE, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -40:
						break;
					case 40:
						{
            Symbol S = new Symbol(sym.VOID, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -41:
						break;
					case 41:
						{
            Symbol S = new Symbol(sym.ELSE, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -42:
						break;
					case 42:
						{
            Symbol S = new Symbol(sym.COUT, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -43:
						break;
					case 43:
						{  ErrMsg.fatal(yyline+1, CharNum.num, "string literal with bad escaped character ignored " + yytext());
            CharNum.num += yytext().length();
          }
					case -44:
						break;
					case 44:
						{  
            ErrMsg.fatal(yyline+1, CharNum.num, "unterminated string literal with bad escaped character ignored");
            CharNum.num = 1;
          }
					case -45:
						break;
					case 45:
						{
            Symbol S = new Symbol(sym.FALSE, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -46:
						break;
					case 46:
						{
            Symbol S = new Symbol(sym.WHILE, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -47:
						break;
					case 47:
						{
            Symbol S = new Symbol(sym.RETURN, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -48:
						break;
					case 48:
						{
            Symbol S = new Symbol(sym.STRUCT, new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -49:
						break;
					case 50:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -50:
						break;
					case 51:
						{ ErrMsg.fatal(yyline+1, CharNum.num,
                         "illegal character ignored: " + yytext());
            CharNum.num += yytext().length();
          }
					case -51:
						break;
					case 52:
						{ 
            Symbol S = new Symbol(sym.STRINGLITERAL, new StrLitTokenVal(yyline+1,
                  CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -52:
						break;
					case 53:
						{ 
            ErrMsg.fatal(yyline+1, CharNum.num, "unterminated string literal ignored");
            CharNum.num = 1;
          }
					case -53:
						break;
					case 54:
						{  ErrMsg.fatal(yyline+1, CharNum.num, "string literal with bad escaped character ignored " + yytext());
            CharNum.num += yytext().length();
          }
					case -54:
						break;
					case 55:
						{  
            ErrMsg.fatal(yyline+1, CharNum.num, "unterminated string literal with bad escaped character ignored");
            CharNum.num = 1;
          }
					case -55:
						break;
					case 57:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -56:
						break;
					case 58:
						{ ErrMsg.fatal(yyline+1, CharNum.num,
                         "illegal character ignored: " + yytext());
            CharNum.num += yytext().length();
          }
					case -57:
						break;
					case 59:
						{ 
            ErrMsg.fatal(yyline+1, CharNum.num, "unterminated string literal ignored");
            CharNum.num = 1;
          }
					case -58:
						break;
					case 61:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -59:
						break;
					case 62:
						{ ErrMsg.fatal(yyline+1, CharNum.num,
                         "illegal character ignored: " + yytext());
            CharNum.num += yytext().length();
          }
					case -60:
						break;
					case 63:
						{ 
            ErrMsg.fatal(yyline+1, CharNum.num, "unterminated string literal ignored");
            CharNum.num = 1;
          }
					case -61:
						break;
					case 65:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -62:
						break;
					case 66:
						{ ErrMsg.fatal(yyline+1, CharNum.num,
                         "illegal character ignored: " + yytext());
            CharNum.num += yytext().length();
          }
					case -63:
						break;
					case 68:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -64:
						break;
					case 70:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -65:
						break;
					case 72:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -66:
						break;
					case 74:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -67:
						break;
					case 76:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -68:
						break;
					case 78:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -69:
						break;
					case 80:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -70:
						break;
					case 82:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -71:
						break;
					case 96:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -72:
						break;
					case 97:
						{  ErrMsg.fatal(yyline+1, CharNum.num, "string literal with bad escaped character ignored " + yytext());
            CharNum.num += yytext().length();
          }
					case -73:
						break;
					case 98:
						{ 
            ErrMsg.fatal(yyline+1, CharNum.num, "unterminated string literal ignored");
            CharNum.num = 1;
          }
					case -74:
						break;
					case 99:
						{  ErrMsg.fatal(yyline+1, CharNum.num, "string literal with bad escaped character ignored " + yytext());
            CharNum.num += yytext().length();
          }
					case -75:
						break;
					case 103:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -76:
						break;
					case 104:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -77:
						break;
					case 105:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -78:
						break;
					case 106:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -79:
						break;
					case 107:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -80:
						break;
					case 108:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -81:
						break;
					case 109:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -82:
						break;
					case 110:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -83:
						break;
					case 111:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -84:
						break;
					case 112:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -85:
						break;
					case 113:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -86:
						break;
					case 114:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -87:
						break;
					case 115:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -88:
						break;
					case 116:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -89:
						break;
					case 117:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -90:
						break;
					case 118:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -91:
						break;
					case 119:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -92:
						break;
					case 120:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -93:
						break;
					case 121:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -94:
						break;
					case 122:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -95:
						break;
					case 123:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -96:
						break;
					case 124:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -97:
						break;
					case 125:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -98:
						break;
					case 126:
						{
            Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, CharNum.num, yytext()));
            CharNum.num += yytext().length();
            return S;
          }
					case -99:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
