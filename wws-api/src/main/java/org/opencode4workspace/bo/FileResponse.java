package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Paul Withers
 * @since 0.7.0
 * 
 *        Response from posting a file to a space
 */
public class FileResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int size;
	private Map<String, String> urls;
	private long created;
	private String createdBy;
	private String contentType;

	/**
	 * @author Paul Withers
	 * @since 0.7.0
	 * 
	 *        Enum for URL types, keys for {@link FileResponse#urls}
	 */
	public enum UrlTypes {
		METADATA("metadata"), NO_REDIRECT_DOWNLOAD("noredirect_download"), REDIRECT_DOWNLOAD("redirect_download");
		private String value;

		private UrlTypes(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * @return String ID of the file
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            String ID of the file
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return file's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            file's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return size of the file in bytes
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            of the file in bytes
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return URLs for the file's metadata, noredirect_download,
	 *         redirect_download
	 */
	public Map<String, String> getUrls() {
		return urls;
	}

	/**
	 * @param urls
	 *            for the file's metadata, noredirect_download,
	 *            redirect_download
	 */
	public void setUrls(Map<String, String> urls) {
		this.urls = urls;
	}

	/**
	 * @return timestamp of when the file was added to the space
	 */
	public long getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            timestamp for when the file was added to the space
	 */
	public void setCreated(long created) {
		this.created = created;
	}

	/**
	 * @return String id of the user who added the file to the space
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            String id of the user who added the file to the space
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return content type for the file
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType
	 *            for the file
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
