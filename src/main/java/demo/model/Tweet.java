package demo.model;

public class Tweet {
	private String name;
	private String text;
	private String imageUrl;

	public Tweet(String name, String text, String imageUrl) {
		super();
		this.name = name;
		this.text = text;
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
