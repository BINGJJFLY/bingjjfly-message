package com.jxgyl.message.resolver;

import javax.activation.DataSource;

/**
 * 附件解析器
 * <p>
 * 具体实现的组件：
 * </p>
 * <ul>
 * <li>LocalFile组件</li>
 * <li>FastDFS组件</li>
 * </ul>
 *
 * @author iss002
 *
 */
public interface DataSourceResolver {

	/**
	 * 根据文件位置信息获得Email附件源
	 * 
	 * @param dir
	 * @param path
	 * @return
	 */
	DataSource resolve(String dir, String path);
}
