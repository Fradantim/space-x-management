package com.fradantim.spacexmanagement.config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fradantim.spacexmanagement.dto.trello.Board;
import com.fradantim.spacexmanagement.dto.trello.Column;
import com.fradantim.spacexmanagement.dto.trello.Label;
import com.fradantim.spacexmanagement.service.TrelloBoardManager;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfig {

	private static final String MARKDOWN_LINE_BREAK = "\n\n";

	@Bean
	public OpenAPI springShopOpenAPI(@Autowired TrelloBoardManager boardManager, @Value("${app.name}") String appName,
			@Value("${app.build-version}") String buildVersion) {
		Board board = boardManager.getWorkBoard().block();
		return new OpenAPI().info(new Info().title(appName).description(buildDescription(board))
				.version("v" + buildVersion));
	}

	private static String buildDescription(Board board) {
		if (board == null) {
			return "No Trello Board found :S";
		}

		StringBuilder sBuilder = new StringBuilder();

		sBuilder.append("Working with the ");
		sBuilder.append(buildMarkDownUrl(board.getName(), board.getUrl()));
		sBuilder.append(" trello board.");
		sBuilder.append(MARKDOWN_LINE_BREAK);

		String imageUrl = getSmallestImage(board.getPrefs());
		if (imageUrl != null) {
			sBuilder.append(buildMarkDownImageUrl(board.getName(), imageUrl));
			sBuilder.append(MARKDOWN_LINE_BREAK);
		}

		if (!StringUtils.isEmpty(board.getDesc())) {
			sBuilder.append(board.getDesc());
			sBuilder.append(MARKDOWN_LINE_BREAK);
		}

		String lists = "[]", labels = "[]";

		if (board.getLists() != null) {
			lists = board.getLists().stream().map(Column::getName).collect(Collectors.toList()).toString();
		}

		if (board.getLabels() != null) {
			labels = board.getLabels().stream().map(Label::getName).filter(StringUtils::isEmpty)
					.collect(Collectors.toList()).toString();
		}

		appendElementsAsList(sBuilder, "Board id **" + board.getId() + "**",
				"Organization id **" + board.getOrganitzationId() + "**",
				"Creator id **" + board.getMemberCreatorId() + "**", "Available Lists at startup : " + lists,
				"Available Labels at startup : " + labels);

		return sBuilder.toString();
	}

	private static String buildMarkDownUrl(String text, String url) {
		return "[" + text + "]" + "(" + url + ")";
	}

	@SuppressWarnings("rawtypes")
	private static String getSmallestImage(Map<String, Object> preferences) {
		if (preferences == null || !(preferences.get("backgroundImageScaled") instanceof List)
				|| ((List) preferences.get("backgroundImageScaled")).isEmpty()
				|| !(((List) preferences.get("backgroundImageScaled")).get(0) instanceof Map))
			return null;

		Object url = ((Map) ((List) preferences.get("backgroundImageScaled")).get(0)).get("url");

		if (!(url instanceof String)) {
			return null;
		}

		return (String) url;
	}

	private static String buildMarkDownImageUrl(String text, String url) {
		return "![" + text + "]" + "(" + url + ")";
	}

	private static void appendElementsAsList(StringBuilder sBuilder, String... elements) {
		for (String element : elements) {
			sBuilder.append("- ");
			sBuilder.append(element);
			sBuilder.append(MARKDOWN_LINE_BREAK);
		}
	}
}
