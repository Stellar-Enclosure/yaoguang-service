package top.egon.cola.component.yaoguang.admin.permission;

import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.LocalizedText;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionDefinition;
import top.egon.cola.platform.tianquan.jianshen.contract.permission.annotation.PermissionMessage;

/** Stable knowledge permissions owned by the Yaoguang Java producer. */
@PermissionDefinition(code = YaoguangPermissionCodes.READ, meaning = "Read authorized knowledge bases, documents, jobs and Wiki pages",
        labelPage = "yaoguang.permissions", labelKey = "permission.read")
@PermissionMessage(pageCode = "yaoguang.permissions", key = "permission.read", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Read authorized knowledge bases, documents, jobs and Wiki pages"))
@PermissionDefinition(code = YaoguangPermissionCodes.WRITE, meaning = "Create knowledge bases, upload documents and edit or publish Wiki content",
        labelPage = "yaoguang.permissions", labelKey = "permission.write")
@PermissionMessage(pageCode = "yaoguang.permissions", key = "permission.write", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Create knowledge bases, upload documents and edit or publish Wiki content"))
@PermissionDefinition(code = YaoguangPermissionCodes.ADMIN, meaning = "Manage knowledge base configuration and membership",
        labelPage = "yaoguang.permissions", labelKey = "permission.admin")
@PermissionMessage(pageCode = "yaoguang.permissions", key = "permission.admin", defaultLocale = "en-US",
        defaults = @LocalizedText(locale = "en-US", text = "Manage knowledge base configuration and membership"))
public final class YaoguangPermissionCodes {

    public static final String READ = "yuheng:knowledge:read";
    public static final String WRITE = "yuheng:knowledge:write";
    public static final String ADMIN = "yuheng:knowledge:admin";

    private YaoguangPermissionCodes() { }
}
