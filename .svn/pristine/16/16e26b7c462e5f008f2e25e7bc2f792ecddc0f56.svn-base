package com.tayyarah.common.controller;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.util.FolderOptions;
import com.tayyarah.configuration.CommonConfig;


@RestController
@RequestMapping("/folder")
public class GenerateDynamicFolderController {
	static final Logger logger = Logger.getLogger(GenerateDynamicFolderController.class);
	@RequestMapping(value="/createDynamicFolder",method=RequestMethod.POST, headers = "Accept=application/json")
	public  @ResponseBody FolderOptions createDynamicFolder(@RequestBody FolderOptions folderOptions){
		CommonConfig cc = CommonConfig.GetCommonConfig();
		logger.info("getLog_location-------------------------"+cc.getLog_location());
		logger.info("dirName-------------------------"+folderOptions.getDirName());
		try{
			String path =cc.getLog_location()+"/corporate";
			if(folderOptions!=null && folderOptions.getDirName()!=null){
				File file = new File(path+File.separator+folderOptions.getDirName());
				boolean b = false;
				if (!file.exists()) {
					b = file.mkdirs();
				}
				if (b){
					logger.info("Directory successfully created");
				}
				else{
					logger.info("Alredy directory createded");
				} 
			}
		}catch(Exception e){
			logger.info("Exception " +e);
		}		
		return folderOptions;
	}
}