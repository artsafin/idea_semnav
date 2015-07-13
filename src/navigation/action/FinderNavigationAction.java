package navigation.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.psi.elements.*;
import navigation.finder.matcher.*;
import util.ElementUtil;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public abstract class FinderNavigationAction extends AnAction
{
    protected PsiElementMatcher getVarsMatcher() {
        return new OrMatcher(
                new AndMatcher(new ParentClassMatcher(FieldReference.class), new LeafTypeMatcher("identifier")),
                new AndMatcher(new ParentClassMatcher(Variable.class), new LeafTypeMatcher("variable")),
                new ClassMatcher(ParameterList.class)
        );
    }

    protected PsiElementMatcher getFileScopeMatcher() {
        return new ClassMatcher(PsiFile.class);
    }

    protected void jumpToElement(Editor editor, PsiElement el)
    {
        if (el != null) {
            int pos = ElementUtil.getBeginOffset(el);
            editor.getCaretModel().moveToOffset(pos);
            editor.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
        }
    }
}
