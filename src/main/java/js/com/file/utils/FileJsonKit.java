package js.com.file.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import js.com.file.activity.ProjectInfo;
import js.com.file.concat.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileJsonKit {

    public  static Logger log = LoggerFactory.getLogger(FileJsonKit.class);
    static String filePath = "res/recentProject.json";


    public static void main(String[] args) {
        String filePath = "res/recentProject.json";
        File file = new File(filePath);
        System.out.println(file.exists());
    }

    public static void saveToProperties(ProjectInfo projectInfo){
        String contentJson =FileUtil.readFileByLines(filePath);
        List<ProjectInfo> projectInfoList=null;
        List<ProjectInfo> projectInfoListGson=null;

        Gson gson = new Gson();
        if(contentJson!=null&&contentJson.equalsIgnoreCase("")){
            projectInfoList = JSON.parseArray(contentJson,ProjectInfo.class);
            projectInfoListGson = gson.fromJson(contentJson,new TypeToken<List<ProjectInfo>>(){}.getType());
            //List<User> retList = gson.fromJson(jsonStr2,new TypeToken<List<User>>(){}.getType());

            System.out.println("读取出来的project:"+projectInfoList);
            System.out.println("读取出来的gson 解出来的，project:"+projectInfoListGson);
            projectInfoList.add(projectInfo);

            projectInfoListGson.add(projectInfo);
            String newContentJson = JSON.toJSONString(projectInfoList);

            String newContentGson = gson.toJson(projectInfoListGson);
            try {
                //FileUtil.writeFileContent(filePath,newContentJson);
                FileUtil.writeFileContent(filePath,newContentGson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            projectInfoList=new ArrayList<>();
            projectInfoList.add(projectInfo);
            String newContentJson = JSON.toJSONString(projectInfoList);
            try {
                FileUtil.writeFileContent(filePath,newContentJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static String loadFromProperties(){
        String contentJson =FileUtil.readFileByLines(filePath);
        System.out.println("contentJson:"+contentJson);
        List<ProjectInfo> projectInfoList = JSON.parseArray(contentJson,ProjectInfo.class);

        for(ProjectInfo projectInfo1:projectInfoList){
            System.out.println(projectInfo1.getPrefixName());
        }
        return contentJson;
    }

}
