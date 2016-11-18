package org.opencode4workspace.builders;

// TODO: Add Javadoc
import java.util.ArrayList;
import java.util.Arrays;

import org.opencode4workspace.bo.Actor;
import org.opencode4workspace.bo.Annotation;
import org.opencode4workspace.endpoints.AppMessage;

public class AppMessageBuilder {

	private String text;
	private String title;
	private String actorName;
	private String actorAvatar;
	private String actorUrl;
	private String color;

	public AppMessageBuilder() {

	}

	public AppMessageBuilder message(String text) {
		this.text = text;
		return this;
	}

	public AppMessageBuilder messageTitle(String title) {
		this.title = title;
		return this;
	}

	public AppMessageBuilder actorName(String name) {
		this.actorName = name;
		return this;
	}

	public AppMessageBuilder actorAvatar(String avatar) {
		this.actorAvatar = avatar;
		return this;
	}

	public AppMessageBuilder actorUrl(String url) {
		this.actorUrl = url;
		return this;
	}

	public AppMessageBuilder color(String color) {
		this.color = color;
		return this;
	}

	public AppMessage build() {
		AppMessage message = new AppMessage();
		message.setVersion(1);
		Annotation annotation = new Annotation();
		annotation.setType("generic");
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
