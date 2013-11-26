package ru.ifmo.rain.loboda;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class LogicStreamTokenizer {
    private char[] name;
    private PushbackInputStream stream;

    public LogicStreamTokenizer(InputStream stream) {
        this.stream = new PushbackInputStream(new BufferedInputStream(stream));
    }

    char[] get_name(){
        return name;
    }

    public Token nextToken() throws IOException, ParserException {
        int ch = stream.read();
        while (ch == ' ') {
            ch = stream.read();
        }
        if (ch < 0) {
            return Token.END;
        }
        switch (ch) {
            case '?':
                return Token.EXISTENCE;
            case '@':
                return Token.UNIVERSAL;
            case '&':
                return Token.AND;
            case '|':
                ch = stream.read();
                if (ch == '-') {
                    return Token.PROVABLY;
                }
                if (ch != -1) {
                    stream.unread(ch);
                }
                return Token.OR;
            case '!':
                return Token.NOT;
            case '\n':
                return Token.PRINT;
            case ',':
                return Token.COMMA;
            case '-':
                stream.read();
                return Token.IMPLICATION;
            case '(':
                return Token.LP;
            case ')':
                return Token.RP;
            default:
                Token token;
                if(ch >= 'a' && ch <= 'z'){
                    token = Token.TERM;
                } else {
                    token = Token.PREDICATE;
                }
                int first = ch;
                ch = stream.read();
                if(ch >= '0' && ch <= '9'){
                    name = new char[]{(char)first, (char)ch};
                }
                if(ch != -1){
                    stream.unread(ch);
                }
                name = new char[]{(char)first};
                return token;
        }
    }
}