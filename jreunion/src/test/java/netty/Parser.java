package netty;

public interface Parser {
	Packet parse(String input);
}