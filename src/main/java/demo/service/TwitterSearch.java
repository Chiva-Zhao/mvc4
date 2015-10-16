package demo.service;

import java.util.List;

import demo.model.LightTweet;

public interface TwitterSearch {

	List<LightTweet> search1(String searchType, List<String> keywords);

}