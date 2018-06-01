package com.citsh.process.category.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.druid.support.json.JSONUtils;
import com.citsh.process.entity.BpmProcess;
import com.citsh.user.entity.BpmConfUser;
import com.citsh.util.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring/ctx*.xml" })
@Transactional
public class BpmCategoryControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private String url;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/**
	 * 根据ID查询流程 配置
	 * 
	 * @throws Exception
	 */
	@Test
	public void findProcessList() throws Exception {
		url = "/bpm/bpm-process-input";
		String id = null;
		mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON).param("id", id)).andDo(print())
				.andExpect(status().isOk()).andReturn();
	}

	/**
	 * save流程 配置
	 * 
	 * @throws Exception
	 */
	@Test
	public void saveProcess() throws Exception {
		url = "/bpm/bpm-process-save";
		BpmProcess bpmProces = new BpmProcess();
		JsonUtil jsonUtil = new JsonUtil();
		String bpmProcess = jsonUtil.toJson(bpmProces);
		String id = null;
		mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON).content(bpmProcess).param("bpmCategoryId", "1")
				.param("bpmConfBaseId", "1183471988703232")).andDo(print()).andExpect(status().isOk()).andReturn();
	}

	/**
	 * 查询 node节点数据
	 */
	@Test
	public void findNodeList() throws Exception {
		url = "/bpm/bpm-conf-node-list";
		String bpmConfBaseId = "1183471988703232";
		MvcResult mvcResult = this.mockMvc
				.perform(get(url).accept(MediaType.APPLICATION_JSON).param("bpmConfBaseId", bpmConfBaseId))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println("=====:" + result);
	}

	@Test
	public void findBpmConfUserList() throws Exception {
		url = "/bpm/bpm-conf-user-save";
		BpmConfUser bpmConfUser = new BpmConfUser();
		bpmConfUser.setValue("1");
		bpmConfUser.setType(1);
		mockMvc.perform(post(url).accept(MediaType.APPLICATION_JSON).flashAttr("bpmConfUser",bpmConfUser)
				.param("bpmConfNodeId", "1183472165076992")).andDo(print())
				.andExpect(status().isOk()).andReturn();
	}

}
