package ayrat.salavatovich.gmail.com.frumatic.test.android.application.model;

import java.util.Date;

public class PopularStory {

	private String _id;
	private String title;
	private String description;
	private Date createDateTime;
	private String imageHashCode;
	private volatile int hashCode;

	public PopularStory() {
	}

	public PopularStory(String title, String description, Date createDateTime,
			String imageHashCode) {
		this.title = title;
		this.description = description;
		this.createDateTime = createDateTime;
		this.imageHashCode = imageHashCode;
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}

	public String getImageHashCode() {
		return imageHashCode;
	}

	public void setImageHashCode(String imageHashCode) {
		this.imageHashCode = imageHashCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public String getShortDescription(int length) {
		return description.substring(0, Math.min(description.length(), length));
	}

	public void setDescription(String description) {
		hashCode = 0;
		this.description = description;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			final int prime = 31;
			int result = 17;
			result = prime * result + imageHashCode.hashCode();
			result = prime * result + title.hashCode();
			result = prime * result + description.hashCode();
			hashCode = prime * result + createDateTime.hashCode();
		}

		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (hashCode() != obj.hashCode())
			return false;

		PopularStory other = (PopularStory) obj;
		if (!imageHashCode.equals(other.getImageHashCode()))
			return false;
		if (!title.equals(other.getTitle()))
			return false;
		if (!description.equals(other.getDescription()))
			return false;
		if (!createDateTime.equals(other.getCreateDateTime()))
			return false;

		return true;
	}

}
