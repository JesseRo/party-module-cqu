package hg.party.command.common;

import com.alibaba.fastjson.JSON;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import hg.party.entity.common.FileVM;
import hg.util.result.ResultUtil;
import org.apache.commons.io.FileUtils;
import org.osgi.service.component.annotations.Component;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.*;

import java.io.File;
import java.io.PrintWriter;

/**
 * 通用文件上传接口
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.NewPlan,
                "mvc.command.name=/api/upload"
        },
        service = MVCResourceCommand.class
)
public class UploadFilesCommand implements MVCResourceCommand {
    private final static int ONE_GB = 1073741824;
    private final static String SAVE_PATH = "/uploadFiles";
    private final static String TEMP = "tmp";
    private final static String REPOSITORY = "repository";
    private final static String fileInputName = "file";
    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)  {
        UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
        try {
            PrintWriter printWriter=resourceResponse.getWriter();
            if (uploadRequest.getSize(fileInputName) == 0) {
                printWriter.write(JSON.toJSONString(ResultUtil.fail("文件为空!")));
                return false;
            }
            Boolean ableDelete = ParamUtil.getBoolean(uploadRequest, "ableDelete");
            String fileName = ParamUtil.getString(uploadRequest, "fileName");
            String bucket = ParamUtil.getString(uploadRequest, "bucket");
            // 从uploadRequest获得File对象
            File uploadedFile = uploadRequest.getFile(fileInputName);
            String tempPath = uploadedFile.getAbsolutePath();
            String rootPath = tempPath.substring(0,tempPath.indexOf("\\temp"))+ File.separator+"webapps";
            String path =SAVE_PATH;
            if(ableDelete){
                path =  path + File.separator +TEMP;
            }else{
                if(!StringUtils.isEmpty(bucket)){
                    path =  path + File.separator +REPOSITORY + File.separator +bucket;
                }else{
                    printWriter.write(JSON.toJSONString(ResultUtil.fail("不可删除文件，参数bucket不能为空。")));
                    return false;
                }
            }
            String sourceFileName = uploadRequest.getFileName(fileInputName);
            if(!StringUtils.isEmpty(fileName)){
                sourceFileName = fileName;
            }
            // 存储文件的目录
            File folder = new File(rootPath+path);
            if(!folder.exists()){
                folder.mkdirs();
            }
            // 对当前目录做可用空间检查
            if (folder.getUsableSpace() < ONE_GB) {
                printWriter.write(JSON.toJSONString(ResultUtil.fail("服务器空间不足!")));
                return false;
            }
            // 最终的文件路径
            String savePath = folder.getAbsolutePath() + File.separator + sourceFileName;
            File file = new File(savePath);
            // 保存文件到物理路径
            FileUtils.copyFile(uploadedFile, file);
            printWriter.write(JSON.toJSONString(ResultUtil.success(new FileVM(sourceFileName,uploadedFile.length(),path+  File.separator + sourceFileName))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
