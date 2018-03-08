package org.opencode4workspace.endpoints;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.opencode4workspace.IWWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.PhotoResponse;
import org.opencode4workspace.json.ResultParser;

/**
 * @author Paul Withers
 * @since 0.7.0
 * 
 *        Endpoint for posting a new photo for the current user
 *
 */
public class PhotoPostEndpoint extends AbstractWWGraphQLEndpoint {

	/**
	 * @param client WWClient containing authentication details and token
	 */
	public PhotoPostEndpoint(IWWClient client) {
		super(client);
	}
	
	/**
	 * Posts a new photo for a user
	 * 
	 * @param photo jpeg File (must be less than 300Kb) for the user's new photo
	 * @return PohotResponse if successful
	 * @throws WWException containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.7.0
	 */
	public PhotoResponse postPhoto(File photo) throws WWException {
		HttpPost post = preparePost();
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			if (!isShouldBeValid()) {
				getClient().authenticate();
			}
			if (photo.length() > (300*1000)) {
				throw new WWException("File must be less than 300Kb");
			}
			String fileName = photo.getName();
			if (fileName.lastIndexOf(".jpg") == -1) {
				throw new WWException("File must be a '.jpg. file");
			}
			FileInputStream stream = new FileInputStream(photo);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody(
				    "file",
				    stream,
				    ContentType.APPLICATION_OCTET_STREAM,
				    photo.getName()
				);

			HttpEntity multipart = builder.build();
			post.setEntity(multipart);
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity());
				PhotoResponse photoResponse = new ResultParser<PhotoResponse>(PhotoResponse.class).parse(content);
				return photoResponse;
			} else {
				throw new WWException("Execution failed: " + response.getStatusLine().getReasonPhrase());
			}
		} catch (Exception e) {
			throw new WWException(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @return HttpPost containing the relevant headers
	 * 
	 * @since 0.7.0
	 */
	private HttpPost preparePost() {
		HttpPost post = new HttpPost(WWDefinedEndpoints.PHOTO);
		post.addHeader("Authorization", "Bearer " + getClient().getJWTToken());
		post.addHeader("Accept", ContentType.APPLICATION_JSON.toString());
		return post;
	}

}
