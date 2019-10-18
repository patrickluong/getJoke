package com.getjoke.models;

import java.util.ArrayList;

public class Jokes {

	private ArrayList<Joke> jokes;

	public ArrayList<Joke> getJokes() {
		return jokes;
	}

	public void setJokes(ArrayList<Joke> jokes) {
		this.jokes = jokes;
	}
	
	public String toString() {
		String result = "Jokes{";
		for (int i = 0; i < jokes.size(); i++) {
			result += jokes.get(i);
			if (i != jokes.size() - 1)
				result += ", ";
		}
		return result + '}';
	}
}
