package com.citsh.deploy.web;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring/ctx*.xml" })
public class ModelerControllerTest {

	@Autowired
	private WebApplicationContext wac;
	

	private MockMvc mockMvc;
	
	private String url;

	@Before
	public void setUp() {
	/*	MockitoAnnotations.initMocks(this);*/
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	



	/**
	 * 部署
	 * */
	@Test
	public void testDeploy() throws Exception {

		MvcResult mvcResult  = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/modeler/modeler-deploy")
						.accept(MediaType.APPLICATION_JSON).param("id", "55001"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println("=====:" + result);
	}

}
