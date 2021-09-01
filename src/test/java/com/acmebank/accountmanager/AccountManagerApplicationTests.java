package com.acmebank.accountmanager;

import com.acmebank.accountmanager.controller.request.TransferAmountRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AccountManagerApplicationTests {
	@Autowired
	WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testGetAccountBalanceApi() throws Exception {
		MvcResult result = mockMvc.perform(get("/account/balance/12345678")).andExpect(status().isOk()).andReturn();
		outputResponse(result);
	}

	@Test
	void testGetAccountBalanceApi_NonExistingAcc() throws Exception {
		MvcResult result = mockMvc.perform(get("/account/balance/12341234")).andExpect(status().isNotFound()).andReturn();
		outputResponse(result);
	}

	@Test
	void testTransferAmountApi() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		TransferAmountRequest request = new TransferAmountRequest();
		request.setFromAccountNumber("88888888");
		request.setToAccountNumber("12345678");
		request.setAmount(new BigDecimal(20));
		request.setCurrency("HKD");
		String jsonStr = mapper.writeValueAsString(request);
		MvcResult result = mockMvc.perform(post("/account/transfer-amount")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonStr))
				.andExpect(status().isOk()).andReturn();
		outputResponse(result);
	}

	@Test
	void testTransferAmountApi_insufficientAmount() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		TransferAmountRequest request = new TransferAmountRequest();
		request.setFromAccountNumber("88888888");
		request.setToAccountNumber("12345678");
		request.setAmount(new BigDecimal(1000020));
		request.setCurrency("HKD");
		String jsonStr = mapper.writeValueAsString(request);
		MvcResult result = mockMvc.perform(post("/account/transfer-amount")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonStr))
				.andExpect(status().isBadRequest()).andReturn();
		outputResponse(result);
	}

	void outputResponse(MvcResult result) throws UnsupportedEncodingException {
		System.out.println("Status: " + result.getResponse().getStatus());
		System.out.println("Response: " + result.getResponse().getContentAsString());
	}
}
