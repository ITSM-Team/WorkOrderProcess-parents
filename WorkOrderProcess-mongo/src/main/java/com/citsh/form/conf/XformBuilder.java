package com.citsh.form.conf;

import com.citsh.file.GridFS;
import com.citsh.keyvalue.entity.Prop;
import com.citsh.keyvalue.entity.Record;
import com.citsh.user.UserConnector;
import com.citsh.user.UserDTO;
import com.citsh.util.JsonUtil;
import com.mongodb.gridfs.GridFSFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XformBuilder {
	private Logger logger = LoggerFactory.getLogger(XformBuilder.class);
	private Xform xform = new Xform();
	private JsonUtil jsonUtil = new JsonUtil();
	private GridFS gridFS;
	private UserConnector userConnector;

	public XformBuilder setGridFS(GridFS gridFS) {
		this.gridFS = gridFS;
		return this;
	}

	public XformBuilder setUserConnector(UserConnector userConnector) {
		this.userConnector = userConnector;
		return this;
	}

	public XformBuilder setContent(String content) {
		this.xform.setContent(content);
		this.logger.debug("content : {}", content);
		try {
			handleStructure();
		} catch (Exception ex) {
			this.logger.error(ex.getMessage(), ex);
		}

		return this;
	}

	public XformBuilder setRecord(Record record) {
		if (record == null) {
			this.logger.info("record is null");

			return this;
		}

		for (Prop prop : record.getProps().values()) {
			String name = prop.getCode();
			String value = prop.getValue();
			XformField xformField = this.xform.findXformField(name);

			if (xformField == null) {
				continue;
			}
			String type = xformField.getType();

			if ("fileupload".equals(type)) {
				GridFSFile gridFSFile = this.gridFS.getFile(value);
				xformField.setValue(gridFSFile.getFilename());
				xformField.setContentType(gridFSFile.getContentType());
				xformField.setLabel(gridFSFile.getFilename());
			} else if ("userpicker".equals(type)) {
				xformField.setValue(value);

				StringBuilder buff = new StringBuilder();

				for (String userId : value.split(",")) {
					if (StringUtils.isBlank(userId)) {
						continue;
					}
					UserDTO userDto = this.userConnector.findById(userId);

					if (userDto == null) {
						continue;
					}
					buff.append(userDto.getDisplayName()).append(",");
				}

				if (buff.length() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}

				xformField.setLabel(buff.toString());
			} else {
				xformField.setValue(value);
			}
		}

		return this;
	}

	public Xform build() {
		return this.xform;
	}

	public void handleStructure() throws Exception {
		if (this.xform.getContent() == null) {
			this.logger.info("cannot find xform content");

			return;
		}
		Map map = this.jsonUtil.fromJson(this.xform.getContent(), Map.class);
		this.logger.debug("map : {}", map);

		if (map == null) {
			this.logger.info("cannot find map");

			return;
		}

		List<Map> sections = (List<Map>) map.get("sections");
		this.logger.debug("sections : {}", sections);

		Map formTypeMap = new HashMap();

		for (Map section : sections) {
			if (!"grid".equals(section.get("type"))) {
				continue;
			}
			List<Map> fields = (List<Map>) section.get("fields");

			for (Map field : fields)
				handleField(field);
		}
	}

	public void handleField(Map map) {
		XformField xformField = new XformField();
		xformField.setName((String) map.get("name"));
		xformField.setType((String) map.get("type"));
		this.xform.addXformField(xformField);
	}
}