package navigation.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import navigation.exception.ContextMissingException;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import util.ActionUtil;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class TraceUp extends AnAction {
    public static final Logger LOG = Logger.getInstance("#nav_trace");

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        try {
            PsiElement el = ActionUtil.requireCurrentPsi(anActionEvent);
            printUp(el, 0);

        } catch (ContextMissingException e) {
            e.printStackTrace();
        }
    }

    private void printUp(@NotNull PsiElement el, int lev) {
        String levelPadding = StringUtils.repeat(" ", lev * 2);

        if (el instanceof PsiFile) {
            LOG.info(String.format("%sFile: %s", levelPadding, ((PsiFile) el).getVirtualFile().getCanonicalPath()));
            return;
        }

        for (PsiElement siblingEl = el; siblingEl != null; siblingEl = siblingEl.getPrevSibling()) {
            LOG.info(String.format("%s%s: [%d] %s",
                    levelPadding,
                    siblingEl.getClass().getCanonicalName(),
                    siblingEl.getTextLength(),
                    siblingEl.getText().replace("\n", "\\n")));
        }

        PsiElement parent = el.getParent();
        if (parent != null) {
            printUp(parent, lev + 1);
        }
    }
}
