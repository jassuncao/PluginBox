/*
 * Copyright (C) 2004 - 2010 by Marcel Schoen and Andre Bossert
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.tetrade.eclipse.plugins.easyshell;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.tetrade.eclipse.plugins.easyshell.preferences.EasyShellPreferencePage;

/**
 * The main plugin class to be used in the desktop.
 */
public class EasyShellPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static EasyShellPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	// debug output
	private boolean debug = false;

	/**
	 * The constructor.
	 */
	public EasyShellPlugin(IPluginDescriptor descriptor) {
		super(descriptor);
		plugin = this;
		try {
			resourceBundle= ResourceBundle.getBundle("org.easyexplore.EasyExplorePluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * Returns the shared instance.
	 */
	public static EasyShellPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the workspace instance.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle= EasyShellPlugin.getDefault().getResourceBundle();
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	static public void log(Object msg) {
		ILog log = EasyShellPlugin.getDefault().getLog();
		Status status = new Status(IStatus.ERROR, EasyShellPlugin.getDefault().getDescriptor().getUniqueIdentifier(), IStatus.ERROR, msg + "\n", null);
		log.log(status);
	}

	static public void log(Throwable ex) {
		ILog log = EasyShellPlugin.getDefault().getLog();
		StringWriter stringWriter = new StringWriter();
	    ex.printStackTrace(new PrintWriter(stringWriter));
		String msg = stringWriter.getBuffer().toString();
		Status status = new Status(IStatus.ERROR, EasyShellPlugin.getDefault().getDescriptor().getUniqueIdentifier(), IStatus.ERROR, msg, null);
		log.log(status);
	}
    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeDefaultPreferences(org.eclipse.jface.preference.IPreferenceStore)
     */
    protected void initializeDefaultPreferences(IPreferenceStore store) {
        EasyShellPreferencePage pref = new EasyShellPreferencePage();
        store = pref.getPreferenceStore();
        super.initializeDefaultPreferences(store);
    }

    /**
     * Return the target program setted in EasyExplorePreferencePage.
     * @return String
     */
    public String getTarget(int num) {
    	if (num == 0) {
    		return getPreferenceStore().getString(EasyShellPreferencePage.P_TARGET);
    	} else if (num == 1) {
    		return getPreferenceStore().getString(EasyShellPreferencePage.P_TARGET_RUN);
    	} else if (num == 2) {
    		return getPreferenceStore().getString(EasyShellPreferencePage.P_TARGET_EXPLORE);
    	} else if (num == 3) {
    		return getPreferenceStore().getString(EasyShellPreferencePage.P_TARGET_COPYPATH);
    	}
        return null;
    }

    public boolean isDebug() {
    	return debug;
    }
}
