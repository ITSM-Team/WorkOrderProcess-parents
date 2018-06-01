package com.citsh.handler;

import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

public class MultipartHandler {
	private static Logger logger = LoggerFactory.getLogger(MultipartHandler.class);
	private MultipartResolver multipartResolver;
	private MultipartHttpServletRequest multipartHttpServletRequest = null;
	private MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap();
	private MultiValueMap<String, MultipartFile> multiFileMap;

	public MultipartHandler(MultipartResolver multipartResolver) {
		this.multipartResolver = multipartResolver;
	}

	public void handle(HttpServletRequest request) {
		if ((request instanceof MultipartHttpServletRequest)) {
			logger.debug("force cast to MultipartHttpServletRequest");

			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			this.multiFileMap = req.getMultiFileMap();
			logger.debug("multiFileMap : {}", this.multiFileMap);
			handleMultiValueMap(req);
			logger.debug("multiValueMap : {}", this.multiValueMap);

			return;
		}

		if (this.multipartResolver.isMultipart(request)) {
			logger.debug("is multipart : {}", Boolean.valueOf(this.multipartResolver.isMultipart(request)));
			this.multipartHttpServletRequest = this.multipartResolver.resolveMultipart(request);

			logger.debug("multipartHttpServletRequest : {}", this.multipartHttpServletRequest);
			this.multiFileMap = this.multipartHttpServletRequest.getMultiFileMap();
			logger.debug("multiFileMap : {}", this.multiFileMap);
			handleMultiValueMap(this.multipartHttpServletRequest);
			logger.debug("multiValueMap : {}", this.multiValueMap);
		} else {
			handleMultiValueMap(request);
			logger.debug("multiValueMap : {}", this.multiValueMap);
		}
	}

	public void clear() {
		if (this.multipartHttpServletRequest == null) {
			return;
		}

		this.multipartResolver.cleanupMultipart(this.multipartHttpServletRequest);
	}

	@SuppressWarnings("rawtypes")
	public void handleMultiValueMap(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry entry : parameterMap.entrySet()) {
			String key = (String) entry.getKey();

			for (String value : (String[]) entry.getValue())
				this.multiValueMap.add(key, value);
		}
	}

	public MultiValueMap<String, String> getMultiValueMap() {
		return this.multiValueMap;
	}

	public MultiValueMap<String, MultipartFile> getMultiFileMap() {
		return this.multiFileMap;
	}
}