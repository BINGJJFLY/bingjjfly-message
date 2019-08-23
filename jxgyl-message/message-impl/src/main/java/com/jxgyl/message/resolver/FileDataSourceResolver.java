package com.jxgyl.message.resolver;

import java.io.File;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

/**
 * LocalFile组件
 *
 * @author iss002
 *
 */
public class FileDataSourceResolver implements DataSourceResolver {

	@Override
	public DataSource resolve(String dir, String path) {
		return new FileDataSource(new File(suffix(dir) + prefix(path)));
	}

	public String prefix(String path) {
		if (path.charAt(0) != '/') {
			path = '/' + path;
		}
		return path;
	}

	public String suffix(String dir) {
		dir = dir.replace('\\', '/');
		if (dir.charAt(dir.length() - 1) != '/') {
			dir += '/';
		}
		return dir;
	}

}
