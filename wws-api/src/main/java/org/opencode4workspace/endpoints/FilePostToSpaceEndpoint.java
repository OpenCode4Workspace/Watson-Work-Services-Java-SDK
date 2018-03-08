package org.opencode4workspace.endpoints;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.activation.MimeType;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.opencode4workspace.IWWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.FileResponse;
import org.opencode4workspace.bo.PhotoResponse;
import org.opencode4workspace.json.ResultParser;

/**
 * @author Paul Withers
 * @since 0.7.0
 * 
 *        Endpoint for posting a new photo for the current user
 *
 */
public class FilePostToSpaceEndpoint extends AbstractWWGraphQLEndpoint {

	/**
	 * @param client
	 *            WWClient containing authentication details and token
	 * 
	 * @since 0.7.0
	 */
	public FilePostToSpaceEndpoint(IWWClient client) {
		super(client);
	}

	/**
	 * Post a file to Watson Workspace
	 * 
	 * @param file
	 *            to post into the Workspace
	 * @param spaceId
	 *            String id of the space to post to
	 * @param imageSize
	 *            image size as height x width, e.g. 200x200. Only blank or null
	 *            values are current supported
	 * @return FileResponse object corresponding to the successful posting of
	 *         the file
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.7.0
	 */
	public FileResponse postfile(File file, String spaceId, String imageSize) throws WWException {
		HttpPost post = preparePost(spaceId, imageSize);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			if (!isShouldBeValid()) {
				getClient().authenticate();
			}
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.setContentType(ContentType.MULTIPART_FORM_DATA);

			if (null == imageSize || "".equals(imageSize)) {
				builder.addBinaryBody("file", file);
			} else {
				String fileType = FilenameUtils.getExtension(file.getName());
				if ("jpg".equals(fileType) || "jpeg".equals(fileType)) {
					builder.addPart("file", new FileBody(file, ContentType.create("image/jpeg"), file.getName()));
				} else if ("gif".equals(fileType)) {
					builder.addPart("file", new FileBody(file, ContentType.create("image/gif"), file.getName()));
				} else if ("png".equals(fileType)) {
					builder.addPart("file", new FileBody(file, ContentType.create("image/png"), file.getName()));
				} else {
					throw new WWException("Unexpected image type provided, expecting jpg, jpeg, gif or png");
				}
			}
			HttpEntity multipart = builder.build();
			post.setEntity(multipart);
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 201) {
				String content = EntityUtils.toString(response.getEntity());
				FileResponse fileResponse = new ResultParser<FileResponse>(FileResponse.class).parse(content);
				return fileResponse;
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
	private HttpPost preparePost(String spaceId, String imageSize) {
		String url = WWDefinedEndpoints.V1_SPACE_ID + spaceId + "/files";
		if (null != imageSize && !"".equals(imageSize)) {
			url += "?dim=" + imageSize;
		}
		HttpPost post = new HttpPost(url);
		post.addHeader("Authorization", "Bearer " + getClient().getJWTToken());
		post.addHeader("Accept", ContentType.APPLICATION_JSON.toString());
		return post;
	}

}
