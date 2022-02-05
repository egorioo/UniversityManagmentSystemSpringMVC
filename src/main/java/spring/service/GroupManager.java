package spring.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import spring.jsonUtils.ReadJson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupManager {
    private String url = "http://localhost:8080/groups";

    private final ReadJson reader;

    @Autowired
    public GroupManager(ReadJson reader) {
        this.reader = reader;
    }

    /*public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        JSONObject json = reader.readJsonFromUrl(urlAllGroups);
        JSONArray groupsJson = (JSONArray) json.get("data");
        for (int i = 0; i < groupsJson.length(); i++) {
            Group group = new Group();
            JSONObject groupJson = (JSONObject) groupsJson.get(i);
            group.setId((int) groupJson.get("group_id"));
            group.setFullName((String) groupJson.get("group_full_name"));
            group.setUrl((String) groupJson.get("group_url"));
            group.setGroupType((String) groupJson.get("group_type"));
            groups.add(group);
        }
        return groups;
    }*/

    public List<String> getGroupsName() {
        List<String> list = new ArrayList<>();
        JSONObject json = reader.readJsonFromUrl(url);
        JSONArray arr = (JSONArray) json.get("data");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);
            list.add((String) obj.get("fullName"));
        }
        return list;
    }

    public String getGroup(String groupName) throws UnsupportedEncodingException {
        JSONObject json = reader.readJsonFromUrl(url + "/" + URLEncoder.encode(groupName,"UTF-8"));
        JSONObject data = (JSONObject) json.get("data");
        return (String) data.get("url");
    }
}
