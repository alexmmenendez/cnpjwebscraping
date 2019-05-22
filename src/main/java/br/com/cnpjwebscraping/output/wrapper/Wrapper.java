package br.com.cnpjwebscraping.output.wrapper;

import com.google.gson.Gson;

public abstract class Wrapper {
	
	public String toJson() {
		return new Gson().toJson(this);
	}
	
}
