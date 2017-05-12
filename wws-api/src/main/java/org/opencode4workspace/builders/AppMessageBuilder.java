package org.opencode4workspace.builders;

import java.util.ArrayList;
import java.util.Arrays;

import org.opencode4workspace.bo.Actor;
import org.opencode4workspace.bo.Annotation;
import org.opencode4workspace.bo.Annotation.AnnotationType;
import org.opencode4workspace.endpoints.AppMessage;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Class for building a Message to post to a Space
 */
public class AppMessageBuilder {

	private String text;
	private String title;
	private String actorName;
	private String actorAvatar;
	private String actorUrl;
	private String color;

	/**
	 * Constructor
	 */
	public AppMessageBuilder() {

	}

	/**
	 * @param text
	 *            String text for the message
	 * @return AppMessageBuilder, this object
	 * 
	 * @since 0.5.0
	 */
	public AppMessageBuilder setMessage(String text) {
		this.text = text;
		return this;
	}

	/**
	 * @param title
	 *            String title for the message
	 * @return AppMessageBuilder, this object
	 * 
	 * @since 0.5.0
	 */
	public AppMessageBuilder setMessageTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * @param name
	 *            String name of the actor
	 * @return AppMessageBuilder, this object
	 * 
	 * @since 0.5.0
	 */
	public AppMessageBuilder setActorName(String name) {
		this.actorName = name;
		return this;
	}

	/**
	 * @param avatar
	 *            String url for the avatar of the Actor
	 * @return AppMessageBuilder, this object
	 * 
	 * @since 0.5.0
	 */
	public AppMessageBuilder setActorAvatar(String avatar) {
		this.actorAvatar = avatar;
		return this;
	}

	/**
	 * @param url
	 *            String url for the Actor's web page
	 * @return AppMessageBuilder, this object
	 * 
	 * @since 0.5.0
	 */
	public AppMessageBuilder setActorUrl(String url) {
		this.actorUrl = url;
		return this;
	}

	/**
	 * @param color
	 *            String hex colour for the message
	 * @return AppMessageBuilder, this object
	 * 
	 * @since 0.5.0
	 */
	public AppMessageBuilder setColor(String color) {
		this.color = color;
		return this;
	}

	/**
	 * Build the AppMessage object (Actor is a child of AppMessage)
	 * 
	 * @return AppMessage, ready to post to WWS
	 * 
	 * @since 0.5.0
	 */
	public AppMessage build() {
		AppMessage message = new AppMessage();
		message.setVersion(1);
		Annotation annotation = new Annotation();
		annotation.setType(AnnotationType.GENERIC);
		annotation.setVersion(1);
		annotation.setColor(color);
		annotation.setText(text);
		annotation.setTitle(title);
		if (actorAvatar != null || actorName != null || actorUrl != null) {
			Actor actor = new Actor();
			actor.setAvatar(actorAvatar);
			actor.setName(actorName);
			actor.setUrl(actorUrl);
			annotation.setActor(actor);
		}
		message.setAnnotations(new ArrayList<Annotation>(Arrays.asList(annotation)));
		return message;
	}
}
