package mobile.zxjt.test.base;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import org.testng.ITestContext;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import up.light.LightContext;
import up.light.folder.FolderTypes;
import up.light.testng.data.reader.IGroupResolver;

public class JsonGroupSolver implements IGroupResolver {
	private Map<String, String> groups;

	public JsonGroupSolver() {
		String file = LightContext.getFolderPath(FolderTypes.CONFIG) + "group.json";
		Gson gson = new Gson();
		try (FileReader reader = new FileReader(file)) {
			groups = gson.fromJson(reader, new TypeToken<Map<String, String>>() {
				private static final long serialVersionUID = -7318315516540777308L;
			}.getType());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getGroup(ITestContext context, Method method) {
		String vClassName = method.getDeclaringClass().getSimpleName();
		String vMethodName = method.getName();
		String vGroup = vClassName + "_" + vMethodName;
		return groups.getOrDefault(vGroup, vGroup);
	}

}
