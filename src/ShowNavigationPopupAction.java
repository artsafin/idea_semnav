import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author ArturSafin <treilor@gmail.com>
 * @licence MIT
 */
public class ShowNavigationPopupAction extends AnAction {

    public static final String NAVIGATIONS_GROUP_ID = "semnav.navigations";

    public void actionPerformed(@NotNull AnActionEvent e) {
        ActionManager am = e.getActionManager();
        ActionGroup navActionGroup = (ActionGroup) am.getAction(NAVIGATIONS_GROUP_ID);

        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        if (editor != null && navActionGroup != null) {
            JBPopupFactory.getInstance()
                    .createActionGroupPopup(navActionGroup.getTemplatePresentation().getText(),
                                            navActionGroup,
                                            e.getDataContext(),
                                            JBPopupFactory.ActionSelectionAid.ALPHA_NUMBERING,
                                            false)
                    .showInBestPositionFor(editor);
        }
    }
}
