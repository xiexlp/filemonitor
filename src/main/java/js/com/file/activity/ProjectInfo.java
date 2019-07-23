package js.com.file.activity;

import java.util.List;

public class ProjectInfo {

    String name;
    String path;
    List<String> filePathList;
    List<String> excludePathList;
    String prefixName;
    List<String> suffexTypeList;
    String averageFileNum;
    String outputFileType;
    String fileDeepLevel;
    int beginLineNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getFilePathList() {
        return filePathList;
    }

    public void setFilePathList(List<String> filePathList) {
        this.filePathList = filePathList;
    }

    public List<String> getExcludePathList() {
        return excludePathList;
    }

    public void setExcludePathList(List<String> excludePathList) {
        this.excludePathList = excludePathList;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

    public List<String> getSuffexTypeList() {
        return suffexTypeList;
    }

    public void setSuffexTypeList(List<String> suffexTypeList) {
        this.suffexTypeList = suffexTypeList;
    }

    public String getAverageFileNum() {
        return averageFileNum;
    }

    public void setAverageFileNum(String averageFileNum) {
        this.averageFileNum = averageFileNum;
    }

    public String getOutputFileType() {
        return outputFileType;
    }

    public void setOutputFileType(String outputFileType) {
        this.outputFileType = outputFileType;
    }

    public String getFileDeepLevel() {
        return fileDeepLevel;
    }

    public void setFileDeepLevel(String fileDeepLevel) {
        this.fileDeepLevel = fileDeepLevel;
    }

    public int getBeginLineNumber() {
        return beginLineNumber;
    }

    public void setBeginLineNumber(int beginLineNumber) {
        this.beginLineNumber = beginLineNumber;
    }
}
