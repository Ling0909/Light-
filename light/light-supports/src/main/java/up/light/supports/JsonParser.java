package up.light.supports;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import up.light.Constants;
import up.light.pojo.ByBean;
import up.light.pojo.Locator;
import up.light.util.Assert;

public class JsonParser {
	private static final Log logger = LogFactory.getLog(JsonParser.class);
	private String mFolder;
	private Map<String, Locator> cacheForFile;
	private String currentFile;

	/**
	 * 构造方法
	 * 
	 * @param folder 存放Json文件的文件夹路径
	 */
	public JsonParser(String folder) {
		File file = new File(folder);
		Assert.isTrue(file.exists() && file.isDirectory(), "Invalid path of folder: " + folder);
		folder = folder.replace('\\', '/');
		if (!folder.endsWith("/")) {
			mFolder = folder + "/";
		} else {
			mFolder = folder;
		}
	}

	public Locator getLocator(Field field) {
		return getLocator(field.getDeclaringClass().getSimpleName(), field.getName());
	}

	public Locator getLocator(String file, String name) {
		return getCacheForFile(file).get(name);
	}

	private Map<String, Locator> getCacheForFile(String file) {
		if (!file.equals(currentFile)) {
			cacheForFile = parseJson(file + ".json");
			currentFile = file;
		}
		return cacheForFile;
	}

	/*
	 * 解析Json对象库文件
	 */
	private Map<String, Locator> parseJson(String file) {
		if (logger.isDebugEnabled())
			logger.debug(Constants.LOG_TAG + "parse file: " + mFolder + file);

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<List<ByBean>>() {
		}.getType(), new MapDeserializer());
		Gson gson = builder.create();

		try (FileReader reader = new FileReader(mFolder + file)) {
			return gson.fromJson(reader, new TypeToken<Map<String, Locator>>() {
			}.getType());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
