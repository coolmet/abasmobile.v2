package com.abas.mobile;

import org.springframework.stereotype.Component;
import com.abas.mobile.service.ReloadablePropertiesService;

@Component
public class ConfigPropertiesReloadeble extends ReloadablePropertiesService
{	
	@Override
	protected void propertiesReloaded()
	{
		// do something after a change in property values was done
	}
}
