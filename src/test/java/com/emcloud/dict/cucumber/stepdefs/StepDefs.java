package com.emcloud.dict.cucumber.stepdefs;

import com.emcloud.dict.EmCloudDictApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = EmCloudDictApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
