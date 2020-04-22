package up.light.supports;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import up.light.pojo.ByBean;
import up.light.supports.pagefactory.ByBeanFactory;

class MapDeserializer implements JsonDeserializer<List<ByBean>> {

	@Override
	public List<ByBean> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		List<ByBean> vList = new ArrayList<>();

		JsonArray vArr = json.getAsJsonArray();
		String vByStr = null;

		for (JsonElement e : vArr) {
			if (e.isJsonNull())
				continue;

			vByStr = e.getAsString();
			ByBean vBean = ByBeanFactory.getByBean(vByStr);
			vList.add(vBean);
		}

		return vList;
	}

}
